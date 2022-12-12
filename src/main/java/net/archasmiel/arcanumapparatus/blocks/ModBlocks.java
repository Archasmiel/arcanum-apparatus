package net.archasmiel.arcanumapparatus.blocks;

import java.util.function.Supplier;
import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.archasmiel.arcanumapparatus.items.ModCreativeTab;
import net.archasmiel.arcanumapparatus.items.ModItems;
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

public class ModBlocks {

  public static final DeferredRegister<Block> BLOCKS =
      DeferredRegister.create(ForgeRegistries.BLOCKS, ArcanumApparatus.MOD_ID);

  public static final RegistryObject<Block> COBALT_ORE =
      registerBlock("cobalt_ore",
          () -> new OreBlock(BlockBehaviour.Properties
              .of(Material.STONE)
              .requiresCorrectToolForDrops()
              .strength(3.0F, 3.0F)),
          ModCreativeTab.MOD_TAB);

  private ModBlocks() {

  }

  public static <T extends Block> RegistryObject<T> registerBlock(String name,
      Supplier<T> blockSupplier, CreativeModeTab tab) {
    RegistryObject<T> result = BLOCKS.register(name, blockSupplier);
    registerBlockItem(name, result, tab);
    return result;
  }

  public static <T extends Block> RegistryObject<Item> registerBlockItem(String name,
      RegistryObject<T> block, CreativeModeTab tab) {
    return ModItems.ITEMS.register(name,
        () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
  }

  public static void register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }

}
