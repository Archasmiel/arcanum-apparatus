package net.archasmiel.arcanumapparatus.block;

import net.archasmiel.arcanumapparatus.util.registering.BlockPropertiesBuilder;
import net.archasmiel.arcanumapparatus.util.registering.BlockRegistering;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

  public static final RegistryObject<Block> COBALT_ORE = BlockRegistering.registerOre("cobalt");
  public static final RegistryObject<Block> CHROME_ORE = BlockRegistering.registerOre("chrome");
  public static final RegistryObject<Block> TUNGSTEN_ORE = BlockRegistering.registerOre("tungsten");
  public static final RegistryObject<Block> VANADIUM_ORE = BlockRegistering.registerOre("vanadium");
  public static final RegistryObject<Block> ZINC_ORE = BlockRegistering.registerOre("zinc");

  public static final RegistryObject<Block> LAVA_SMELTERY = BlockRegistering.registerBlock(
      "lava_smeltery", () -> new LavaSmeltery(new BlockPropertiesBuilder()
          .metal().needTools().resists(3, 3).build()));

  private ModBlocks() {

  }

  public static void register(IEventBus eventBus) {
    BlockRegistering.registerEventBus(eventBus);
  }

}
