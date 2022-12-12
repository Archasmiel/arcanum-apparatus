package net.archasmiel.arcanumapparatus.items;

import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, ArcanumApparatus.MOD_ID);

  public static final RegistryObject<Item> DULL_INGOT =
      ITEMS.register("dull_ingot",
          () -> new Item(new Properties().tab(ModCreativeTab.MOD_TAB)));

  private ModItems() {

  }

  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }

}
