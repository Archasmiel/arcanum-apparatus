package net.archasmiel.arcanumapparatus.util.registering;

import java.util.function.Supplier;
import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.archasmiel.arcanumapparatus.util.ModCreativeTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistering {

  private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
      ForgeRegistries.BLOCKS, ArcanumApparatus.MOD_ID);

  private BlockRegistering() {

  }

  public static RegistryObject<Block> registerOre(String name) {
    return registerBlock(
        name + "_ore",
        () -> new OreBlock(BlockBehaviour.Properties
            .of(Material.STONE)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 3.0F)));
  }

  public static <T extends Block> RegistryObject<T> registerBlock(
      String name, Supplier<T> blockSupplier) {
    RegistryObject<T> result = BLOCKS.register(name, blockSupplier);
    registerBlockItem(name, result);
    return result;
  }

  public static <T extends Block> RegistryObject<T> registerBlock(
      String name, Supplier<T> blockSupplier, CreativeModeTab tab) {
    RegistryObject<T> result = BLOCKS.register(name, blockSupplier);
    registerBlockItem(name, result, tab);
    return result;
  }

  public static <T extends Block> RegistryObject<Item> registerBlockItem(
      String name, RegistryObject<T> block) {
    return ItemRegistering.registerItem(name, () -> new BlockItem(
        block.get(), new Item.Properties().tab(ModCreativeTab.MOD_TAB)));
  }

  public static <T extends Block> RegistryObject<Item> registerBlockItem(
      String name, RegistryObject<T> block, CreativeModeTab tab) {
    return ItemRegistering.registerItem(name, () -> new BlockItem(
        block.get(), new Item.Properties().tab(tab)));
  }

  public static void registerEventBus(IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }

  public static DeferredRegister<Block> getRegister() {
    return BLOCKS;
  }

}
