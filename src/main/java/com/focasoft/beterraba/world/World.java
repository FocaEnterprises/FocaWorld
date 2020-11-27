package com.focasoft.beterraba.world;

import com.focasoft.beterraba.entity.Entity;
import java.awt.Graphics;
import java.util.LinkedList;
import org.json.JSONObject;

public class World
{
  private String name;
  private final LinkedList<Entity> entities = new LinkedList<>();
  
  private int width;
  private int height;
  
  public World(String name)
  {
    this.name = name;
    load(null); // TODO
  }
  
  public void update()
  {
    getEntities().forEach(Entity::tick);
  }
  
  public void render(Graphics g)
  {
    getEntities().forEach(e -> e.render(g));
  }
  
  public void load(JSONObject json)
  {
    // TODO
    width = 240;
    height = 240;
  }
  
  public void removeEntity(Entity entity)
  {
    synchronized(entities)
    {
      entities.remove(entity);
    }
  }
  
  public void addEntity(Entity entity)
  {
    synchronized(this.entities)
    {
      entities.add(entity);
    }
  }
  
  public LinkedList<Entity> getEntities()
  {
    synchronized(this.entities)
    {
      return new LinkedList<>(this.entities);
    }
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
