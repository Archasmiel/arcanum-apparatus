package net.archasmiel.arcanumapparatus.util.smeltery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

@AllArgsConstructor
@Getter
public class FuelFluid {

  private Item bucket;
  private Fluid fluid;
  
}
