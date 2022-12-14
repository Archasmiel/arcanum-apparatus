package net.archasmiel.arcanumapparatus.world.features;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.archasmiel.arcanumapparatus.blocks.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class ModConfiguredFeatures {

  private static final List<TargetBlockState> OVERWORLD_COBALT_ORES =
      oreTargetBlockStates(List.of(
        new Pair<>(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.COBALT_ORE.get())
  ));
  private static final List<TargetBlockState> OVERWORLD_CHROME_ORES =
      oreTargetBlockStates(List.of(
        new Pair<>(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.CHROME_ORE.get())
  ));
  private static final List<TargetBlockState> OVERWORLD_TUNGSTEN_ORES =
      oreTargetBlockStates(List.of(
        new Pair<>(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.TUNGSTEN_ORE.get())
  ));
  private static final List<TargetBlockState> OVERWORLD_VANADIUM_ORES =
      oreTargetBlockStates(List.of(
        new Pair<>(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.VANADIUM_ORE.get())
  ));
  private static final List<TargetBlockState> OVERWORLD_ZINC_ORES =
      oreTargetBlockStates(List.of(
        new Pair<>(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.ZINC_ORE.get())
  ));

  public static final Holder<ConfiguredFeature<OreConfiguration, ?>> COBALT_ORE = oreFeature(
      "cobalt_ore", OVERWORLD_COBALT_ORES, 5);
  public static final Holder<ConfiguredFeature<OreConfiguration, ?>> CHROME_ORE = oreFeature(
      "chrome_ore", OVERWORLD_CHROME_ORES, 4);
  public static final Holder<ConfiguredFeature<OreConfiguration, ?>> TUNGSTEN_ORE = oreFeature(
      "tungsten_ore", OVERWORLD_TUNGSTEN_ORES, 5);
  public static final Holder<ConfiguredFeature<OreConfiguration, ?>> VANADIUM_ORE = oreFeature(
      "vanadium_ore", OVERWORLD_VANADIUM_ORES, 5);
  public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ZINC_ORE = oreFeature(
      "zinc_ore", OVERWORLD_ZINC_ORES, 8);

  private ModConfiguredFeatures() {

  }

  private static List<TargetBlockState> oreTargetBlockStates(List<Pair<RuleTest, Block>> replaces) {
    return replaces.stream()
        .map(pair -> OreConfiguration.target(pair.getFirst(), pair.getSecond().defaultBlockState()))
        .toList();
  }

  private static Holder<ConfiguredFeature<OreConfiguration, ?>> oreFeature(
      String name, List<TargetBlockState> targetBlockStates, int veinSize) {
    return FeatureUtils.register(name, Feature.ORE,
        new OreConfiguration(targetBlockStates, veinSize));
  }

}
