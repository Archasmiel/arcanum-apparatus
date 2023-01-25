package net.archasmiel.arcanumapparatus.screen.lavasmeltery;

import lombok.Getter;
import net.archasmiel.arcanumapparatus.block.ModBlocks;
import net.archasmiel.arcanumapparatus.block.entity.SmelteryBE;
import net.archasmiel.arcanumapparatus.screen.ModMenuTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;

public class SmelteryMenu extends AbstractContainerMenu {

  @Getter
  public final SmelteryBE blockEntity;
  private final ContainerData progressData;
  private final Level level;

  public SmelteryMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
    this(id, inventory, inventory.player.level.getBlockEntity(extraData.readBlockPos()),
        new SimpleContainerData(17));
  }

  public SmelteryMenu(int id, Inventory inventory, BlockEntity entity,
      ContainerData progressData) {
    super(ModMenuTypes.LAVA_SMELTERY_MENU.get(), id);
    checkContainerSize(inventory, 16);
    blockEntity = (SmelteryBE) entity;
    this.level = inventory.player.level;
    this.progressData = progressData;

    // inventory
    for (int r = 0 ; r < 3 ; r++) {
      for (int c = 0 ; c < 9 ; c++) {
        this.addSlot(new Slot(inventory, c + r * 9 + 9, 36 + c * 18, 137 + r * 18));
      }
    }

    // hotbar
    for (int c = 0 ; c < 9 ; c++) {
      this.addSlot(new Slot(inventory, c, 36 + c * 18, 195));
    }

    // block slots
    this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
      for (int r = 0 ; r < 4 ; r++) {
        for (int c = 0 ; c < 4 ; c++) {
          this.addSlot(new SmelteryItemSlot(handler, c + r*4, 144 + c * 18, 7 + r * 18));
        }
      }
      this.addSlot(new SmelteryFuelSlot(handler, 16, 198, 97));
    });

    addDataSlots(progressData);
  }

  public float getFuelScaled() {
    return 1f * blockEntity.getFuel().getFluidAmount() / SmelteryBE.FUEL_CAPACITY;
  }

  public float getProgressScaled(int slot) {
    int progress = progressData.get(slot);
    return progress == -1 ? -1 : 1f * progress / SmelteryBE.SMELTING_TIME;
  }

  @Override
  @MethodsReturnNonnullByDefault
  public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.slots.get(pIndex);
    if (slot != null && slot.hasItem()) {
      ItemStack itemstack1 = slot.getItem();
      itemstack = itemstack1.copy();
      EquipmentSlot equipmentslot = LivingEntity.getEquipmentSlotForItem(itemstack);
      if (pIndex == 0) {
        if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
          return ItemStack.EMPTY;
        }

        slot.onQuickCraft(itemstack1, itemstack);
      } else if (pIndex >= 1 && pIndex < 5) {
        if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
          return ItemStack.EMPTY;
        }
      } else if (pIndex >= 5 && pIndex < 9) {
        if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
          return ItemStack.EMPTY;
        }
      } else if (equipmentslot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(8 - equipmentslot.getIndex()).hasItem()) {
        int i = 8 - equipmentslot.getIndex();
        if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
          return ItemStack.EMPTY;
        }
      } else if (equipmentslot == EquipmentSlot.OFFHAND && !this.slots.get(45).hasItem()) {
        if (!this.moveItemStackTo(itemstack1, 45, 46, false)) {
          return ItemStack.EMPTY;
        }
      } else if (pIndex >= 9 && pIndex < 36) {
        if (!this.moveItemStackTo(itemstack1, 36, 45, false)) {
          return ItemStack.EMPTY;
        }
      } else if (pIndex >= 36 && pIndex < 45) {
        if (!this.moveItemStackTo(itemstack1, 9, 36, false)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
        return ItemStack.EMPTY;
      }

      if (itemstack1.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }

      if (itemstack1.getCount() == itemstack.getCount()) {
        return ItemStack.EMPTY;
      }

      slot.onTake(pPlayer, itemstack1);
      if (pIndex == 0) {
        pPlayer.drop(itemstack1, false);
      }
    }

    return itemstack;
  }

  @Override
  public boolean stillValid(Player pPlayer) {
    return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
        pPlayer, ModBlocks.SMELTERY.get());
  }

}
