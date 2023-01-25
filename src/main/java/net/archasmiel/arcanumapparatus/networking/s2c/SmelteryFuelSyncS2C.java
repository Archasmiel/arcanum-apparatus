package net.archasmiel.arcanumapparatus.networking.s2c;

import java.util.function.Supplier;
import net.archasmiel.arcanumapparatus.block.entity.SmelteryBE;
import net.archasmiel.arcanumapparatus.screen.lavasmeltery.SmelteryMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class SmelteryFuelSyncS2C {

  private final FluidStack fluidStack;
  private final BlockPos pos;

  public SmelteryFuelSyncS2C(FluidStack fluidStack, BlockPos pos) {
    this.fluidStack = fluidStack;
    this.pos = pos;
  }

  public SmelteryFuelSyncS2C(FriendlyByteBuf buf) {
    this.fluidStack = buf.readFluidStack();
    this.pos = buf.readBlockPos();
  }

  public void toBytes(FriendlyByteBuf buf) {
    buf.writeFluidStack(fluidStack);
    buf.writeBlockPos(pos);
  }

  public boolean handle(Supplier<Context> supplier) {
    Context context = supplier.get();
    context.enqueueWork(() -> {
      if (Minecraft.getInstance().level == null) {
        return;
      }

      if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof SmelteryBE blockEntity) {
        blockEntity.setFuelLevel(fluidStack);

        if (Minecraft.getInstance().player.containerMenu instanceof SmelteryMenu menu &&
            menu.getBlockEntity().getBlockPos().equals(pos)) {
          // menu operations
        }
      }
    });
    return true;
  }

}
