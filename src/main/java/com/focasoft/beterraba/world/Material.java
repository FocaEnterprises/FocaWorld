package com.focasoft.beterraba.world;

import java.awt.image.BufferedImage;

public enum Material
{
  ;
  
  private final BufferedImage SPRITE;
  private final String NAME;
  private final boolean SOLID;
  private final int ID;
  
  Material(int id, String name, boolean solid, BufferedImage sprite)
  {
    this.ID = id;
    this.NAME = name;
    this.SOLID = solid;
    this.SPRITE = sprite;
  }
  
  public String getName()
  {
    return NAME;
  }
  
  public int getID()
  {
    return ID;
  }
  
  public boolean isSolid()
  {
    return SOLID;
  }
  
  public BufferedImage getSprite()
  {
    return SPRITE;
  }
}
