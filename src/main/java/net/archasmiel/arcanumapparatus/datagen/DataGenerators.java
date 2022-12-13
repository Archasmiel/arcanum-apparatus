package net.archasmiel.arcanumapparatus.datagen;

import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.archasmiel.arcanumapparatus.datagen.providers.ModBlockModelProvider;
import net.archasmiel.arcanumapparatus.datagen.providers.ModBlockStateProvider;
import net.archasmiel.arcanumapparatus.datagen.providers.ModItemModelProvider;
import net.archasmiel.arcanumapparatus.datagen.providers.ModLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ArcanumApparatus.MOD_ID, bus = Bus.MOD)
public class DataGenerators {

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

    generator.addProvider(new ModLootTableProvider(generator));
    generator.addProvider(new ModBlockModelProvider(generator, existingFileHelper));
    generator.addProvider(new ModItemModelProvider(generator, existingFileHelper));
    generator.addProvider(new ModBlockStateProvider(generator, existingFileHelper));

  }

  private DataGenerators() {

  }

}
