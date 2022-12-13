package net.archasmiel.arcanumapparatus.datagen.providers;

import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.archasmiel.arcanumapparatus.blocks.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

  public ModBlockStateProvider(DataGenerator gen,
      ExistingFileHelper exFileHelper) {
    super(gen, ArcanumApparatus.MOD_ID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    simpleBlock(ModBlocks.CHROME_ORE.get());
    simpleBlock(ModBlocks.COBALT_ORE.get());
    simpleBlock(ModBlocks.TUNGSTEN_ORE.get());
    simpleBlock(ModBlocks.VANADIUM_ORE.get());
    simpleBlock(ModBlocks.ZINC_ORE.get());
  }

}
