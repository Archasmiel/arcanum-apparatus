package net.archasmiel.arcanumapparatus.networking;

import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.archasmiel.arcanumapparatus.networking.c2s.SlotUpdatePacket;
import net.archasmiel.arcanumapparatus.networking.s2c.SmelteryFuelSyncS2C;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {

  public static final ResourceLocation RESOURCE =
      new ResourceLocation(ArcanumApparatus.MOD_ID, "messages");
  private static SimpleChannel instance;

  private static int packetId = 0;

  private ModMessages() {

  }

  private static int id() {
    return packetId++;
  }

  public static void register() {
    instance = NetworkRegistry.ChannelBuilder
        .named(RESOURCE)
        .networkProtocolVersion(() -> "1.0")
        .serverAcceptedVersions(s -> true)
        .clientAcceptedVersions(s -> true)
        .simpleChannel();

    instance.messageBuilder(SmelteryFuelSyncS2C.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        .decoder(SmelteryFuelSyncS2C::new)
        .encoder(SmelteryFuelSyncS2C::toBytes)
        .consumer(SmelteryFuelSyncS2C::handle)
        .add();
  }

  public static <M> void sendToServer(M message) {
    instance.sendToServer(message);
  }

  public static <M> void sendToPlayer(M message, ServerPlayer player) {
    instance.send(PacketDistributor.PLAYER.with(() -> player), message);
  }

  public static <M> void sendToClients(M message) {
    instance.send(PacketDistributor.ALL.noArg(), message);
  }

  public static SimpleChannel getInstance() {
    return instance;
  }

}
