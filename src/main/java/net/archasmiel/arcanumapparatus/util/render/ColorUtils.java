package net.archasmiel.arcanumapparatus.util.render;

public class ColorUtils {

  public static int toRGBA(float r, float g, float b, float a) {
    return toRGBA((int)(r*255+0.5), (int)(g*255+0.5), (int)(b*255+0.5), (int)(a*255+0.5));
  }

  public static int toRGBA(int r, int g, int b, int a) {
    return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
  }

}
