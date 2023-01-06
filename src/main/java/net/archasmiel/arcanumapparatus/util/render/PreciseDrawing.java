package net.archasmiel.arcanumapparatus.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;

public class PreciseDrawing {

  public static void fillPrecise(PoseStack pPoseStack, float pMinX, float pMinY, float pMaxX, float pMaxY, int pColor) {
    innerFillPrecise(pPoseStack.last().pose(), pMinX, pMinY, pMaxX, pMaxY, pColor);
  }


  public static void innerFillPrecise(Matrix4f pMatrix, float pMinX, float pMinY, float pMaxX, float pMaxY, int pColor) {
    if (pMinX < pMaxX) {
      float i = pMinX;
      pMinX = pMaxX;
      pMaxX = i;
    }

    if (pMinY < pMaxY) {
      float j = pMinY;
      pMinY = pMaxY;
      pMaxY = j;
    }

    float f3 = (pColor >> 24 & 255) / 255.0F;
    float f = (pColor >> 16 & 255) / 255.0F;
    float f1 = (pColor >> 8 & 255) / 255.0F;
    float f2 = (pColor & 255) / 255.0F;
    BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
    RenderSystem.enableBlend();
    RenderSystem.disableTexture();
    RenderSystem.defaultBlendFunc();
    RenderSystem.setShader(GameRenderer::getPositionColorShader);
    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
    bufferbuilder.vertex(pMatrix, pMinX, pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
    bufferbuilder.vertex(pMatrix, pMaxX, pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
    bufferbuilder.vertex(pMatrix, pMaxX, pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
    bufferbuilder.vertex(pMatrix, pMinX, pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
    bufferbuilder.end();
    BufferUploader.end(bufferbuilder);
    RenderSystem.enableTexture();
    RenderSystem.disableBlend();
  }

}
