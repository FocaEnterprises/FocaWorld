package com.focasoft.beterraba.world;

import java.awt.image.BufferedImage;

public enum Material
{
  ROCK((byte) 0, "Rock", true, null),
  DIRT((byte) 1, "Dirt", true, null),
  GRASS((byte) 2, "Grass", true, null),
  SAND((byte) 3, "Sand", true, null),
  LAVA((byte) 4, "Lava", false, null),
  WATER((byte) 5, "Water", false, null),
  TREE((byte) 6, "Tree", true, null),
  CACTUS((byte) 7, "Cactus", false, null),
  FLOWER((byte) 8, "Flower", false, null),
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
}
