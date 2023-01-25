package net.archasmiel.arcanumapparatus.block.entity;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.archasmiel.arcanumapparatus.networking.ModMessages;
import net.archasmiel.arcanumapparatus.networking.s2c.SmelteryFuelSyncS2C;
import net.archasmiel.arcanumapparatus.screen.lavasmeltery.SmelteryMenu;
import net.archasmiel.arcanumapparatus.util.smeltery.FuelFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmelteryBE extends BlockEntity implements MenuProvider {

  /* ONLY TESTING SUBJECTS
  * TODO delete later
  * TODO replace with JSON recipes */
  public static final Set<Item> SMELTABLE_ITEMS = Sets.newHashSet(
      Items.COPPER_INGOT,
      Items.IRON_INGOT,
      Items.GOLD_INGOT
  );
  public static final Set<FuelFluid> FUELS = Sets.newHashSet(
      new FuelFluid(Items.LAVA_BUCKET, Fluids.LAVA)
  );
  public static final int SMELTING_TIME = 80;
  public static final int LAVA_PER_OPERATION = 32;

  public static final String INVENTORY_TAG = "inventory";
  public static final String LAVA_TAG = "fuel";
  public static final int FUEL_CAPACITY = 8000;

  private final FluidTank fuelTank = new FluidTank(FUEL_CAPACITY) {

    @Override
    protected void onContentsChanged() {
      setChanged();
      if(level != null && !level.isClientSide()) {
        ModMessages.sendToClients(new SmelteryFuelSyncS2C(this.fluid, worldPosition));
      }
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
      return FUELS.stream().anyMatch(e -> e.getFluid() == stack.getFluid());
    }

  };

  private final ItemStackHandler itemHandler = new ItemStackHandler(17) {

    @Override
    protected void onContentsChanged(int slot) {
      setChanged();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
      if (slot >= 0 && slot < 16 && SMELTABLE_ITEMS.contains(stack.getItem())) {
        return true;
      }
      return slot == 16 && FUELS.stream().anyMatch(e -> e.getBucket() == stack.getItem());
    }

  };

  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
  private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
  protected final ContainerData progressData;
  private final List<Integer> smeltProgress = new ArrayList<>(Collections.nCopies(16, -1));


  public SmelteryBE(BlockPos pPos, BlockState pBlockState) {
    super(ModBlockEntities.LAVA_SMELTERY_BE.get(), pPos, pBlockState);
    this.progressData = new ContainerData() {
      @Override
      public int get(int pIndex) {
        return SmelteryBE.this.smeltProgress.get(pIndex);
      }

      @Override
      public void set(int pIndex, int pValue) {
        SmelteryBE.this.smeltProgress.set(pIndex, pValue);
      }

      @Override
      public int getCount() {
        return 16;
      }
    };
  }

  @Override
  public Component getDisplayName() {
    return new TextComponent("");
  }

  @Override
  public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
    ModMessages.sendToClients(new SmelteryFuelSyncS2C(this.fuelTank.getFluid(), worldPosition));
    return new SmelteryMenu(pContainerId, pPlayerInventory, this, progressData);
  }

  @NotNull
  @Override
  public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return lazyItemHandler.cast();
    }
    if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
      return lazyItemHandler.cast();
    }
    return LazyOptional.empty();
  }

  @Override
  public void onLoad() {
    super.onLoad();
    lazyItemHandler = LazyOptional.of(() -> itemHandler);
    lazyFluidHandler = LazyOptional.of(() -> fuelTank);
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    lazyItemHandler.invalidate();
    lazyFluidHandler.invalidate();
  }

  @Override
  protected void saveAdditional(CompoundTag nbt) {
    nbt.put(INVENTORY_TAG, itemHandler.serializeNBT());
    nbt.put(LAVA_TAG, fuelTank.writeToNBT(new CompoundTag()));
    super.saveAdditional(nbt);
  }

  @Override
  public void load(CompoundTag nbt) {
    super.load(nbt);
    itemHandler.deserializeNBT(nbt.getCompound(INVENTORY_TAG));
    fuelTank.readFromNBT(nbt.getCompound(LAVA_TAG));
  }

  public void drops() {
    SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
    for (int i = 0 ; i < itemHandler.getSlots() ; i++) {
      inv.setItem(i, itemHandler.getStackInSlot(i));
    }
    if (this.level != null) {
      Containers.dropContents(this.level, this.worldPosition, inv);
    }
  }

  public FluidTank getFuel() {
    return fuelTank;
  }

  public void setFuelLevel(FluidStack fluidStack) {
    fuelTank.setFluid(fluidStack);
  }

  /**
   * Method for all slots updating.
   */
  private void updateSlots() {
    for (int i = 0 ; i < 16 ; i++) {
      int slotProgress = smeltProgress.get(i);
      ItemStack stack = itemHandler.getStackInSlot(i).copy();

      if (slotProgress == -1) {
        if (SMELTABLE_ITEMS.contains(stack.getItem()) &&
            fuelTank.getFluidAmount() >= LAVA_PER_OPERATION) {
          fuelTank.drain(LAVA_PER_OPERATION, FluidAction.EXECUTE);
          smeltProgress.set(i, 0);
        }
      } else if (slotProgress >= 0) {
        if (stack.isEmpty()) {
          smeltProgress.set(i, -1);
        } else {
          if (slotProgress == SMELTING_TIME) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
            smeltProgress.set(i, -1);
          } else {
            smeltProgress.set(i, slotProgress + 1);
          }
        }
      }
    }
  }

  private void updateLavaSlot() {
    if (itemHandler.getStackInSlot(16).isEmpty()) {
      return;
    }

    Optional<FuelFluid> fuelFluid = FUELS.stream()
        .filter(e -> e.getBucket() == itemHandler.getStackInSlot(16).getItem())
        .findFirst();

    fuelFluid.ifPresent(e -> {
      if (FUEL_CAPACITY - fuelTank.getFluidAmount() >= 1000) {
        itemHandler.setStackInSlot(16, new ItemStack(Items.BUCKET));
        fuelTank.fill(new FluidStack(e.getFluid(), 1000), FluidAction.EXECUTE);
      }
    });
  }

  public static void tick(Level level, BlockPos blockPos, BlockState blockState,
      SmelteryBE entity) {
    if (level.isClientSide()) {
      return;
    }

    entity.updateLavaSlot();
    entity.updateSlots();
    entity.setChanged();
  }

}
