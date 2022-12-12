package net.archasmiel.arcanumapparatus.world.features;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {

  public static final Holder<PlacedFeature> COBALT_ORE_PLACED = PlacementUtils.register(
      "cobalt_ore_placed",
      ModConfiguredFeatures.COBALT_ORE,
      ModOrePlacement.commonOrePlacement(20,
          HeightRangePlacement.triangle(
              VerticalAnchor.aboveBottom(-80),
              VerticalAnchor.aboveBottom(80)
          )
  ));

  private ModPlacedFeatures() {

  }

}
