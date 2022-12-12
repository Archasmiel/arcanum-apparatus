package net.archasmiel.arcanumapparatus.world.features;

import java.util.List;
import net.archasmiel.arcanumapparatus.blocks.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;

public class ModConfiguredFeatures {

  public static final List<TargetBlockState> OVERWORLD_COBALT_ORES = List.of(
    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
        ModBlocks.COBALT_ORE.get().defaultBlockState()),
    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
        ModBlocks.COBALT_ORE.get().defaultBlockState())
  );

  public static final Holder<ConfiguredFeature<OreConfiguration, ?>> COBALT_ORE =
      FeatureUtils.register("cobalt_ore", Feature.ORE,
          new OreConfiguration(OVERWORLD_COBALT_ORES, 5));

  private ModConfiguredFeatures() {

  }

}
