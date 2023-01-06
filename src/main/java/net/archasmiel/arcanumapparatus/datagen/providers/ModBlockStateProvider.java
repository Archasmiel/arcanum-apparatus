package net.archasmiel.arcanumapparatus.datagen.providers;

import static net.archasmiel.arcanumapparatus.ArcanumApparatus.MOD_ID;
import static net.archasmiel.arcanumapparatus.block.ModBlocks.*;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

  public ModBlockStateProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
    super(dataGenerator, MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    simpleBlock(CHROME_ORE.get());
    simpleBlock(COBALT_ORE.get());
    simpleBlock(TUNGSTEN_ORE.get());
    simpleBlock(VANADIUM_ORE.get());
    simpleBlock(ZINC_ORE.get());
  }

}
