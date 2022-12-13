package net.archasmiel.arcanumapparatus.world.features;

import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.jetbrains.annotations.NotNull;

public class ModOrePlacement {

  public static List<PlacementModifier> orePlacement(PlacementModifier modifier1,
      PlacementModifier modifier2, List<ResourceKey<Biome>> biomes) {
    return List.of(modifier1, InSquarePlacement.spread(), modifier2, new PlacementFilter() {
      @Override
      protected boolean shouldPlace(@NotNull PlacementContext pContext,
          @NotNull Random pRandom, @NotNull BlockPos pPos) {
        Holder<Biome> biomeHolder = pContext.getLevel().getBiome(pPos);
        return biomes.stream().anyMatch(biomeHolder::is);
      }

      @Override
      public @NotNull PlacementModifierType<?> type() {
        return PlacementModifierType.BIOME_FILTER;
      }
    });
  }

  public static List<PlacementModifier> commonOrePlacement(int veinsPerChunk,
      PlacementModifier modifier, List<ResourceKey<Biome>> biomes) {
    return orePlacement(CountPlacement.of(veinsPerChunk), modifier, biomes);
  }

  public static List<PlacementModifier> rareOrePlacement(int veinsPerChunk,
      PlacementModifier modifier, List<ResourceKey<Biome>> biomes) {
    return orePlacement(RarityFilter.onAverageOnceEvery(veinsPerChunk), modifier, biomes);
  }

  public static List<PlacementModifier> orePlacement(PlacementModifier modifier1,
      PlacementModifier modifier2) {
    return List.of(modifier1, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
  }

  public static List<PlacementModifier> commonOrePlacement(int veinsPerChunk,
      PlacementModifier modifier) {
    return orePlacement(CountPlacement.of(veinsPerChunk), modifier);
  }

  public static List<PlacementModifier> rareOrePlacement(int veinsPerChunk,
      PlacementModifier modifier) {
    return orePlacement(RarityFilter.onAverageOnceEvery(veinsPerChunk), modifier);
  }

  private ModOrePlacement() {

  }

}
