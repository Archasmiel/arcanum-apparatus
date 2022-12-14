package net.archasmiel.arcanumapparatus.world.features;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {

  public static final Holder<PlacedFeature> COBALT_ORE_PLACED = PlacementUtils.register(
      "cobalt_ore_placed",
      ModConfiguredFeatures.COBALT_ORE,
      ModOrePlacement.rareOrePlacement(
          20,
          HeightRangePlacement.triangle(
              VerticalAnchor.aboveBottom(-80),
              VerticalAnchor.aboveBottom(80)),
          List.of(
              Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE,
              Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA)
  ));

  public static final Holder<PlacedFeature> CHROME_ORE_PLACED = PlacementUtils.register(
      "chrome_ore_placed",
      ModConfiguredFeatures.CHROME_ORE,
      ModOrePlacement.commonOrePlacement(
          20,
          HeightRangePlacement.triangle(
              VerticalAnchor.absolute(0),
              VerticalAnchor.absolute(80))
  ));

  public static final Holder<PlacedFeature> ZINC_ORE_PLACED = PlacementUtils.register(
      "zinc_ore_placed",
      ModConfiguredFeatures.ZINC_ORE,
      ModOrePlacement.commonOrePlacement(
          25,
          HeightRangePlacement.triangle(
              VerticalAnchor.absolute(0),
              VerticalAnchor.absolute(80))
  ));

  public static final Holder<PlacedFeature> TUNGSTEN_ORE_PLACED = PlacementUtils.register(
      "tungsten_ore_placed",
      ModConfiguredFeatures.TUNGSTEN_ORE,
      ModOrePlacement.commonOrePlacement(
          20,
          HeightRangePlacement.triangle(
              VerticalAnchor.absolute(0),
              VerticalAnchor.absolute(80)),
          List.of(
              Biomes.FOREST, Biomes.BIRCH_FOREST,
              Biomes.FLOWER_FOREST, Biomes.DARK_FOREST,
              Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.WINDSWEPT_FOREST)
      ));

  public static final Holder<PlacedFeature> VANADIUM_ORE_PLACED = PlacementUtils.register(
      "vanadium_ore_placed",
      ModConfiguredFeatures.VANADIUM_ORE,
      ModOrePlacement.commonOrePlacement(
          20,
          HeightRangePlacement.triangle(
              VerticalAnchor.absolute(0),
              VerticalAnchor.absolute(80)),
          List.of(
              Biomes.FOREST, Biomes.BIRCH_FOREST,
              Biomes.FLOWER_FOREST, Biomes.DARK_FOREST,
              Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.WINDSWEPT_FOREST)
      ));

  private ModPlacedFeatures() {

  }

}
