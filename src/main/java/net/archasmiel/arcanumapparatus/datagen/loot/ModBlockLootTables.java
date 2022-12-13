package net.archasmiel.arcanumapparatus.datagen.loot;

import net.archasmiel.arcanumapparatus.blocks.ModBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ModBlockLootTables extends BlockLoot {

  @Override
  protected void addTables() {
    this.dropSelf(ModBlocks.CHROME_ORE.get());
    this.dropSelf(ModBlocks.COBALT_ORE.get());
    this.dropSelf(ModBlocks.TUNGSTEN_ORE.get());
    this.dropSelf(ModBlocks.VANADIUM_ORE.get());
    this.dropSelf(ModBlocks.ZINC_ORE.get());
  }

  @Override
  protected @NotNull Iterable<Block> getKnownBlocks() {
    return ModBlocks.BLOCKS.getEntries().stream()
        .map(RegistryObject::get)::iterator;
  }

}