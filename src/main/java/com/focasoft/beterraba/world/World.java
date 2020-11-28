package com.focasoft.beterraba.world;

import com.focasoft.beterraba.entity.Entity;
import java.awt.Graphics;
import java.util.LinkedList;
import org.json.JSONObject;

public class World
{
  private final LinkedList<Entity> ENTITIES = new LinkedList<>();
  
  private String name;
  private Tile[] tiles;
  
  private int width;
  private int height;
  
  private boolean loaded;
  
  public World()
  {
    // TODO: Pensar numa logica mais interessante
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
    try
    {
      name = json.getString("name");
      width = json.getInt("width");
      height = json.getInt("height");
      width = json.getInt("width");
      
      this.tiles = new Tile[width * height];
      
      JSONObject tiles = json.getJSONObject("tiles");
      
      for(String key : tiles.keySet())
      {
        // TODO
        //this.tiles[Integer.parseInt(key)] = new Tile(Material.getByID((byte) tiles.getInt(key)));
      }
    }
    catch(Exception e)
    {
      System.out.println("Falha ao carregar o mundo.");
      e.printStackTrace();
      return;
    }
    
    loaded = true;
  }
  
  public void removeEntity(Entity entity)
  {
    synchronized(ENTITIES)
    {
      ENTITIES.remove(entity);
    }
  }
  
  public void addEntity(Entity entity)
  {
    synchronized(this.ENTITIES)
    {
      ENTITIES.add(entity);
    }
  }
  
  public LinkedList<Entity> getEntities()
  {
    synchronized(this.ENTITIES)
    {
      return new LinkedList<>(this.ENTITIES);
    }
  }
  
  public boolean isLoaded()
  {
    return loaded;
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
