package net.archasmiel.arcanumapparatus.screen.lavasmeltery;

import net.archasmiel.arcanumapparatus.block.entity.LavaSmelteryBE;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SmelteryItemSlot extends SlotItemHandler {

  public SmelteryItemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
    super(itemHandler, index, xPosition, yPosition);
  }

  @Override
  public int getMaxStackSize() {
    return 1;
  }

  @Override
  public int getMaxStackSize(@NotNull ItemStack stack) {
    return 1;
  }

  @Override
  public boolean mayPlace(@NotNull ItemStack stack) {
    return LavaSmelteryBE.SMELTABLE_ITEMS.contains(stack.getItem());
  }

}
