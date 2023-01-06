package net.archasmiel.arcanumapparatus.datagen.providers;

import static net.archasmiel.arcanumapparatus.ArcanumApparatus.MOD_ID;
import static net.archasmiel.arcanumapparatus.block.ModBlocks.*;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockModelProvider {

  public ModBlockModelProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
    super(dataGenerator, MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    simpleBlock(CHROME_ORE.get());
    simpleBlock(COBALT_ORE.get());
    simpleBlock(TUNGSTEN_ORE.get());
    simpleBlock(VANADIUM_ORE.get());
    simpleBlock(ZINC_ORE.get());
  }

  private BlockModelBuilder simpleBlock(Block block) {
    ResourceLocation registryName = block.getRegistryName();
    if (registryName == null) {
      throw new NullPointerException();
    }
    return withExistingParent(registryName.getPath(),
        new ResourceLocation("block/cube_all"))
          .texture("all", "block/" + registryName.getPath());
  }

}
