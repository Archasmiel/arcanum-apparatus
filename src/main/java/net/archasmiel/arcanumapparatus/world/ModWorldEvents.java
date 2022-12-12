package net.archasmiel.arcanumapparatus.world;

import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.archasmiel.arcanumapparatus.world.generation.ModOreGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArcanumApparatus.MOD_ID)
public class ModWorldEvents {

  @SubscribeEvent
  public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
    ModOreGeneration.generateOres(event);
  }

  private ModWorldEvents() {

  }

}
