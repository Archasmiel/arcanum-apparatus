package net.archasmiel.arcanumapparatus.item;

import net.archasmiel.arcanumapparatus.util.ModCreativeTab;
import net.archasmiel.arcanumapparatus.util.registering.ItemRegistering;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

  public static final RegistryObject<Item> DULL_INGOT = ItemRegistering.registerItem(
      "dull_ingot", () -> new Item(new Properties().tab(ModCreativeTab.MOD_TAB)));

  private ModItems() {

  }

  public static void register(IEventBus eventBus) {
    ItemRegistering.registerEventBus(eventBus);
  }

}
