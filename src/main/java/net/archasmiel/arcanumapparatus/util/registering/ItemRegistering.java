package net.archasmiel.arcanumapparatus.util.registering;

import java.util.function.Supplier;
import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistering {

  private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
      ForgeRegistries.ITEMS, ArcanumApparatus.MOD_ID);

  private ItemRegistering() {

  }

  public static <T extends Item> RegistryObject<Item> registerItem(
      String name, Supplier<T> itemSupplier) {
    return ITEMS.register(name, itemSupplier);
  }

  public static void registerEventBus(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }

  public static DeferredRegister<Item> getRegister() {
    return ITEMS;
  }

}
