package net.archasmiel.arcanumapparatus.block.entity;

import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.archasmiel.arcanumapparatus.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
      DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ArcanumApparatus.MOD_ID);

  public static final RegistryObject<BlockEntityType<LavaSmelteryBE>> LAVA_SMELTERY_BE =
      BLOCK_ENTITIES.register("lava_smeltery", () ->
          BlockEntityType.Builder.of(LavaSmelteryBE::new, ModBlocks.LAVA_SMELTERY.get())
              .build(null));

  private ModBlockEntities() {

  }

  public static void register(IEventBus eventBus) {
    BLOCK_ENTITIES.register(eventBus);
  }

}
