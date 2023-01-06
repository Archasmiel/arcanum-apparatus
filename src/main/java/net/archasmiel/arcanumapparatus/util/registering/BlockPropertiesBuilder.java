package net.archasmiel.arcanumapparatus.util.registering;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

public class BlockPropertiesBuilder {

  private Properties properties;

  public BlockPropertiesBuilder stone() {
    properties = Properties.of(Material.STONE);
    return this;
  }

  public BlockPropertiesBuilder metal() {
    properties = Properties.of(Material.METAL);
    return this;
  }

  public BlockPropertiesBuilder wood() {
    properties = Properties.of(Material.WOOD);
    return this;
  }

  public BlockPropertiesBuilder needTools() {
    properties = properties.requiresCorrectToolForDrops();
    return this;
  }

  public BlockPropertiesBuilder resists(float destroyTime, float explosionResist) {
    properties = properties.strength(destroyTime, explosionResist);
    return this;
  }


  public BlockPropertiesBuilder resists(int destroyTime, int explosionResist) {
    properties = properties.strength(destroyTime, explosionResist);
    return this;
  }

  public Properties build() {
    return properties;
  }

}
