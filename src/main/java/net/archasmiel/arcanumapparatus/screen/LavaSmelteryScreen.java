package net.archasmiel.arcanumapparatus.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.archasmiel.arcanumapparatus.util.render.PreciseDrawing;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LavaSmelteryScreen extends AbstractContainerScreen<LavaSmelteryMenu> {

  public static final ResourceLocation TEXTURE =
      new ResourceLocation(ArcanumApparatus.MOD_ID, "textures/gui/lava_smeltery.png");

  public LavaSmelteryScreen(LavaSmelteryMenu pMenu, Inventory pPlayerInventory, Component component) {
    super(pMenu, pPlayerInventory, new TextComponent(""));
    this.imageWidth = 230;
    this.imageHeight = 219;
  }

  @Override
  protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
    int x = (width - imageWidth) / 2;
    int y = (height - imageHeight) / 2;

    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, TEXTURE);
    RenderSystem.enableBlend();

    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    GuiComponent.blit(pPoseStack, x+10, y+10, 230, 0, 116, 119, 346, 219);
    GuiComponent.blit(pPoseStack, x, y, 0, 0, 230, 219, 346, 219);

    for (int r = 0 ; r < 4 ; r++) {
      for (int c = 0 ; c < 4 ; c++) {
        float progress = menu.getScaledProgress(c + r*4);

        if (progress > 0) {
          float progressInv = 1f - progress;
          float sizeInv = 16f * progressInv;
          float x0 = x + c*18 + 144f;
          float y0 = y + r*18 + 7f;

          PreciseDrawing.fillPrecise(pPoseStack, x0, y0+sizeInv, x0+16f, y0+16f,
              toRGBA(progress, progressInv, 0, 0.5f));
        }
      }
    }
  }

  private int toRGBA(float r, float g, float b, float a) {
    return toRGBA((int)(r*255+0.5), (int)(g*255+0.5), (int)(b*255+0.5), (int)(a*255+0.5));
  }

  private int toRGBA(int r, int g, int b, int a) {
    return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
  }

  @Override
  public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
    renderBackground(pPoseStack);
    super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    renderTooltip(pPoseStack, pMouseX, pMouseY);
  }

  @Override
  protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {

  }

}
