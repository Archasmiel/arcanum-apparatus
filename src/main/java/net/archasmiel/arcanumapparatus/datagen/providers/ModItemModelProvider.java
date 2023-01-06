package net.archasmiel.arcanumapparatus.datagen.providers;

import static net.archasmiel.arcanumapparatus.block.ModBlocks.CHROME_ORE;
import static net.archasmiel.arcanumapparatus.block.ModBlocks.COBALT_ORE;
import static net.archasmiel.arcanumapparatus.block.ModBlocks.LAVA_SMELTERY;
import static net.archasmiel.arcanumapparatus.block.ModBlocks.TUNGSTEN_ORE;
import static net.archasmiel.arcanumapparatus.block.ModBlocks.VANADIUM_ORE;
import static net.archasmiel.arcanumapparatus.block.ModBlocks.ZINC_ORE;
import static net.archasmiel.arcanumapparatus.item.ModItems.DULL_INGOT;

import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

  public ModItemModelProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
    super(dataGenerator, ArcanumApparatus.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    simpleItem(DULL_INGOT.get());

    simpleBlockItem(CHROME_ORE.get());
    simpleBlockItem(COBALT_ORE.get());
    simpleBlockItem(TUNGSTEN_ORE.get());
    simpleBlockItem(VANADIUM_ORE.get());
    simpleBlockItem(ZINC_ORE.get());

    simpleBlockItem(LAVA_SMELTERY.get());
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
