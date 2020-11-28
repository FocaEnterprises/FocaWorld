package com.focasoft.beterraba.entity;

import com.focasoft.beterraba.client.Camera;
import com.focasoft.beterraba.world.World;
import java.awt.Graphics;

public abstract class Entity
{
  protected World world;
  protected String name;
  protected int x;
  protected int y;
  
  public Entity(World world, String name, int x, int y)
  {
    this.name = name;
    this.world = world;
    this.x = x;
    this.y = y;
  }
  
  public abstract void tick();
  
  public abstract void render(Graphics g, Camera camera);
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public int getX()
  {
    return x;
  }
  
  public void setX(int x)
  {
    this.x = x;
  }
  
  public int getY()
  {
    return y;
  }
  
  public void setY(int y)
  {
    this.y = y;
  }
  
  public void moveX(int move)
  {
    this.x += move;
  }
  
  public void moveY(int move)
  {
    this.y += move;
  }
  
  public World getWorld()
  {
    return world;
  }
}
