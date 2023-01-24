package net.archasmiel.arcanumapparatus;

import com.mojang.logging.LogUtils;
import net.archasmiel.arcanumapparatus.block.ModBlocks;
import net.archasmiel.arcanumapparatus.block.entity.ModBlockEntities;
import net.archasmiel.arcanumapparatus.item.ModItems;
import net.archasmiel.arcanumapparatus.networking.ModMessages;
import net.archasmiel.arcanumapparatus.screen.lavasmeltery.LavaSmelteryScreen;
import net.archasmiel.arcanumapparatus.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ArcanumApparatus.MOD_ID)
public class ArcanumApparatus {

    public static final String MOD_ID = "arcanumapparatus";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ArcanumApparatus() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LAVA_SMELTERY.get(), RenderType.translucent());

        MenuScreens.register(ModMenuTypes.LAVA_SMELTERY_MENU.get(), LavaSmelteryScreen::new);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        private ClientModEvents() {

        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("onClientSetup successful");
        }

    }
}
