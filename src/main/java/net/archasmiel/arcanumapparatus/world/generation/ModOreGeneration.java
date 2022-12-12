package net.archasmiel.arcanumapparatus.world.generation;

import java.util.List;
import net.archasmiel.arcanumapparatus.world.features.ModPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ModOreGeneration {

  private ModOreGeneration() {

  }

  public static void generateOres(final BiomeLoadingEvent event) {
    List<Holder<PlacedFeature>> undergroundOres =
        event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES);

    undergroundOres.add(ModPlacedFeatures.COBALT_ORE_PLACED);
  }

}
