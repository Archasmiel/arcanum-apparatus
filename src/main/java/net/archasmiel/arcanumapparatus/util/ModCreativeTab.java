package net.archasmiel.arcanumapparatus.util;

import net.archasmiel.arcanumapparatus.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeTab {

  public static final CreativeModeTab MOD_TAB =
      new CreativeModeTab("main_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
          return new ItemStack(ModItems.DULL_INGOT.get());
        }
      };

  private ModCreativeTab() {

  }

}
