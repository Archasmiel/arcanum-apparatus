package net.archasmiel.arcanumapparatus.datagen.providers;

import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.archasmiel.arcanumapparatus.blocks.ModBlocks;
import net.archasmiel.arcanumapparatus.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

  public ModItemModelProvider(DataGenerator generator,
      ExistingFileHelper existingFileHelper) {
    super(generator, ArcanumApparatus.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    simpleItem(ModItems.DULL_INGOT.get());

    simpleBlockItem(ModBlocks.CHROME_ORE.get());
    simpleBlockItem(ModBlocks.COBALT_ORE.get());
    simpleBlockItem(ModBlocks.TUNGSTEN_ORE.get());
    simpleBlockItem(ModBlocks.VANADIUM_ORE.get());
    simpleBlockItem(ModBlocks.ZINC_ORE.get());
  }


  private ItemModelBuilder simpleBlockItem(Block block) {
    ResourceLocation registryName = block.getRegistryName();
    if (registryName == null) {
      throw new NullPointerException();
    }
    return withExistingParent(registryName.getPath(),
        modid + ":block/" + registryName.getPath());
  }

  private ItemModelBuilder simpleItem(Item item) {
    ResourceLocation registryName = item.getRegistryName();
    if (registryName == null) {
      throw new NullPointerException();
    }
    return withExistingParent(registryName.getPath(),
        new ResourceLocation("item/generated"))
        .texture("layer0", modid + ":item/" + registryName.getPath());
  }

}
