package com.focasoft.beterraba.world;

public class World
{
  public final int WIDTH;
  public final int HEIGHT;
  
  private String name;
  
  public World(String name, int width, int height)
  {
    this.name = name;
    this.WIDTH = width;
    this.HEIGHT = height;
  }
  
  public void update()
  {
  
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public int getWidth()
  {
    return WIDTH;
  }
  
  public int getHeight()
  {
    return HEIGHT;
  }
}
