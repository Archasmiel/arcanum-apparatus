package net.archasmiel.arcanumapparatus.blockentity;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import net.archasmiel.arcanumapparatus.screen.LavaSmelteryMenu;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LavaSmelteryBE extends BlockEntity implements MenuProvider {

  private static final String INVENTORY_TAG = "inventory";
  private static final int SMELT_TIME = 80;
  private static final HashSet<Item> SMELTABLE_ITEMS = Sets.newHashSet(
      Items.COPPER_INGOT,
      Items.IRON_INGOT,
      Items.GOLD_INGOT,
      Items.NETHERITE_INGOT
  );

  private final ItemStackHandler itemHandler = new ItemStackHandler(16) {
    @Override
    protected void onContentsChanged(int slot) {
      setChanged();
    }
  };

  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
  protected final ContainerData data;
  private final List<Integer> progress = new ArrayList<>(Collections.nCopies(16, -1));


  public LavaSmelteryBE(BlockPos pPos, BlockState pBlockState) {
    super(ModBlockEntities.LAVA_SMELTERY_BE.get(), pPos, pBlockState);
    this.data = new ContainerData() {
      @Override
      public int get(int pIndex) {
        return LavaSmelteryBE.this.progress.get(pIndex);
      }

      @Override
      public void set(int pIndex, int pValue) {
        if (pIndex >= 0 && pIndex < 16) {
          LavaSmelteryBE.this.progress.set(pIndex, pValue);
        }
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

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory,
      Player pPlayer) {
    return new LavaSmelteryMenu(pContainerId, pPlayerInventory, this, data);
  }

  @NotNull
  @Override
  public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return lazyItemHandler.cast();
    }

    return super.getCapability(cap, side);
  }

  @Override
  public void onLoad() {
    super.onLoad();
    lazyItemHandler = LazyOptional.of(() -> itemHandler);
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    lazyItemHandler.invalidate();
  }

  @Override
  protected void saveAdditional(CompoundTag nbt) {
    nbt.put(INVENTORY_TAG, itemHandler.serializeNBT());
    super.saveAdditional(nbt);
  }

  @Override
  public void load(CompoundTag nbt) {
    super.load(nbt);
    itemHandler.deserializeNBT(nbt.getCompound(INVENTORY_TAG));
  }

  public SimpleContainer getInventory() {
    SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
    for (int i = 0 ; i < itemHandler.getSlots() ; i++) {
      inv.setItem(i, itemHandler.getStackInSlot(i));
    }
    return inv;
  }

  public void drops() {
    if (this.level != null) {
      Containers.dropContents(this.level, this.worldPosition, getInventory());
    }
  }

  public int smeltingTime() {
    return SMELT_TIME;
  }

  public List<Integer> getProgress() {
    return progress;
  }

  /**
   * Method for all slots updating.
   */
  private void updateSlots(Level level, BlockPos blockPos, BlockState blockState) {
    for (int i = 0 ; i < 16 ; i++) {
      int slotProgress = progress.get(i);
      ItemStack stack = itemHandler.getStackInSlot(i).copy();

      // if stack in slot is empty (and slot progress is -1)
      if (stack.isEmpty() && slotProgress != -1) {
        progress.set(i, -1);
        setChanged(level, blockPos, blockState);
        continue;
      }

      // if slot progress is not -1
      if (slotProgress >= 0) {
        if (slotProgress == SMELT_TIME) {
          slotSmelted(i, stack);
        } else {
          progress.set(i, slotProgress + 1);
        }
        setChanged(level, blockPos, blockState);
        continue;
      }

      // if item is smeltable and its progress is -1
      if (SMELTABLE_ITEMS.contains(stack.getItem())) {
        progress.set(i, 0);
        setChanged(level, blockPos, blockState);
      }
    }
  }

  /**
   * Called if slot is smelted.
   */
  private void slotSmelted(int i, ItemStack stack) {
    if (stack.getCount() == 1) {
      stack = ItemStack.EMPTY;
      progress.set(i, -1);
    } else {
      stack.setCount(stack.getCount() - 1);
      progress.set(i, 0);
    }
    itemHandler.setStackInSlot(i, stack);
  }

  public static void tick(Level level, BlockPos blockPos, BlockState blockState,
      LavaSmelteryBE entity) {
    if (level.isClientSide()) {
      return;
    }

    entity.updateSlots(level, blockPos, blockState);
  }

}
