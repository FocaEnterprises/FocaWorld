package com.focasoft.beterraba.world;

public class Tile
{
  private final int X;
  private final int Y;
  
  private Material type;
  
  public Tile(Material type, int x, int y)
  {
    this.type = type;
    this.X = x;
    this.Y = y;
  }
  
  public int getX()
  {
    return X;
  }
  
  public int getY()
  {
    return Y;
  }
  
  public Material getType()
  {
    return type;
  }
  
  public void setType(Material type)
  {
    this.type = type;
  }
}
