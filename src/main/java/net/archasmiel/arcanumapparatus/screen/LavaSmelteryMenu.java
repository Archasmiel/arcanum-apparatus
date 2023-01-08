package net.archasmiel.arcanumapparatus.screen;

import net.archasmiel.arcanumapparatus.blockentity.LavaSmelteryBE;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class LavaSmelteryMenu extends AbstractContainerMenu {

  public final LavaSmelteryBE blockEntity;
  private final Level level;
  private final ContainerData data;

  public LavaSmelteryMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
    this(id, inventory, inventory.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(16));
  }

  public LavaSmelteryMenu(int id, Inventory inventory, BlockEntity entity, ContainerData data) {
    super(ModMenuTypes.LAVA_SMELTERY_MENU.get(), id);
    checkContainerSize(inventory, 16);
    blockEntity = (LavaSmelteryBE) entity;
    this.level = inventory.player.level;
    this.data = data;

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

    this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
      for (int r = 0 ; r < 4 ; r++) {
        for (int c = 0 ; c < 4 ; c++) {
          this.addSlot(new SlotItemHandler(handler, c + r*4, 144 + c * 18, 7 + r * 18) {
            @Override
            public int getMaxStackSize() {
              return 1;
            }

            @Override
            public int getMaxStackSize(@NotNull ItemStack stack) {
              return 1;
            }
          });
        }
      }
    });

    addDataSlots(data);
  }

  public boolean isSmeltingSlot(int slot) {
    return data.get(slot) >= 0;
  }

  public float getScaledProgress(int slot) {
    return 1f * data.get(slot) / blockEntity.smeltingTime();
  }

  @Override
  public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.slots.get(pIndex);
    if (slot != null && slot.hasItem()) {
      ItemStack itemstack1 = slot.getItem();
      itemstack = itemstack1.copy();
      EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
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
    return true;
  }

}
