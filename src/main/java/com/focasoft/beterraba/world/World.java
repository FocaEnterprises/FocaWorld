package com.focasoft.beterraba.world;

import java.awt.Graphics;
import org.json.JSONObject;

public class World
{
  public int width;
  public int height;
  
  private String name;
  
  public World(String name)
  {
    this.name = name;
  }
  
  public void update()
  {
  
  }
  
  public void render(Graphics g)
  {
  
  }
  
  public void load(JSONObject json)
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
    return width;
  }
  
  public int getHeight()
  {
    return height;
  }
}
