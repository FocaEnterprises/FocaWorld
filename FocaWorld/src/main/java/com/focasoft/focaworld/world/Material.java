package com.focasoft.focaworld.world;

import static com.focasoft.focaworld.assets.Sprites.*;

import java.awt.image.BufferedImage;

public enum Material
{
  ROCK((byte) 0, "Rock", true, rock),
  DIRT((byte) 1, "Dirt", true, dirt),
  GRASS((byte) 2, "Grass", true, grass),
  SAND((byte) 3, "Sand", true, sand),
  LAVA((byte) 4, "Lava", false, lava),
  WATER((byte) 5, "Water", false, water),
  TREE((byte) 6, "Tree", true, tree),
  CACTUS((byte) 7, "Cactus", false, cactus),
  FLOWER((byte) 8, "Flower", false, flower),
  ;
  
  public final BufferedImage SPRITE;
  public final String NAME;
  public final boolean SOLID;
  public final byte ID;
  
  Material(byte id, String name, boolean solid, BufferedImage sprite)
  {
    this.ID = id;
    this.NAME = name;
    this.SOLID = solid;
    this.SPRITE = sprite;
  }
  
  public static Material getByID(byte id)
  {
    for(Material material : values())
    {
      if(material.ID == id)
      {
        return material;
      }
    }
    
    return ROCK;
  }
}
