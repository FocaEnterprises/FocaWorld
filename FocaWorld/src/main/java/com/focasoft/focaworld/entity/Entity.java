package com.focasoft.focaworld.entity;

import com.focasoft.focaworld.client.render.Camera;
import com.focasoft.focaworld.world.World;
import java.awt.Graphics;

public abstract class Entity
{
  protected World world;
  protected String name;
  protected int x;
  protected int y;
  protected short id;

  public Entity(World world, String name, short id, int x, int y)
  {
    this.name = name;
    this.world = world;
    this.x = x;
    this.y = y;
    this.id = id;
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

  public short getId()
  {
    return id;
  }

  public void setId(short id)
  {
    this.id = id;
  }

  public World getWorld()
  {
    return world;
  }
}
