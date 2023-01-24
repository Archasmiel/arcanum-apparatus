package net.archasmiel.arcanumapparatus.screen.lavasmeltery;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.archasmiel.arcanumapparatus.ArcanumApparatus;
import net.archasmiel.arcanumapparatus.util.render.ColorUtils;
import net.archasmiel.arcanumapparatus.util.render.PreciseDrawing;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LavaSmelteryScreen extends AbstractContainerScreen<LavaSmelteryMenu> {

  public static final int INTERFACE_WIDTH = 230;
  public static final int INTERFACE_HEIGHT = 219;
  public static final int TEXTURE_WIDTH = 346;
  public static final int TEXTURE_HEIGHT = 219;
  public static final ResourceLocation TEXTURE =
      new ResourceLocation(ArcanumApparatus.MOD_ID, "textures/gui/lava_smeltery.png");

  public LavaSmelteryScreen(LavaSmelteryMenu pMenu, Inventory pPlayerInventory, Component component) {
    super(pMenu, pPlayerInventory, new TextComponent(""));
    this.imageWidth = INTERFACE_WIDTH;
    this.imageHeight = INTERFACE_HEIGHT;
  }

  @Override
  protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
    int x = (width - imageWidth) / 2;
    int y = (height - imageHeight) / 2;

    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, TEXTURE);
    RenderSystem.enableBlend();

    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    GuiComponent.blit(pPoseStack, x+10, y+10, 230, 0, 116, 119, TEXTURE_WIDTH, TEXTURE_HEIGHT);
    GuiComponent.blit(pPoseStack, x, y, 0, 0, 230, 219, TEXTURE_WIDTH, TEXTURE_HEIGHT);

    for (int r = 0 ; r < 4 ; r++) {
      for (int c = 0 ; c < 4 ; c++) {
        float progress = menu.getScaledProgress(c + r*4);

        if (progress > 0) {
          float progressInv = 1f - progress;
          float sizeInv = 16f * progressInv;
          float x0 = x + c*18 + 144f;
          float y0 = y + r*18 + 7f;

          PreciseDrawing.fillPrecise(pPoseStack, x0, y0+sizeInv, x0+16f, y0+16f,
              ColorUtils.toRGBA(progress, progressInv, 0, 0.5f));
        }
      }
    }

    float x0 = x + 198f;
    float y0 = y + 115f;
    PreciseDrawing.fillPrecise(pPoseStack, x0, y0+(1f-menu.getLavaLevel())*16,
        x0+16f, y0+16f, ColorUtils.toRGBA(1f, 0, 0, 0.5f));
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
