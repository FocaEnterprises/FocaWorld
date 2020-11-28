package com.focasoft.beterraba.world;

import static com.focasoft.beterraba.client.Client.TILE_SIZE;

import com.focasoft.beterraba.client.Camera;
import com.focasoft.beterraba.entity.Entity;
import java.awt.Graphics;
import java.util.LinkedList;
import org.json.JSONObject;

public class World
{
  private final Object lock = new Object();
  
  private final LinkedList<Entity> ENTITIES = new LinkedList<>();
  
  private String name;
  private byte[] tiles;
  
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
  
  public void render(Graphics g, Camera camera)
  {
    for(int x = 0; x < width; x++)
    {
      for(int y = 0; y < height; y++)
      {
        getTile(x, y).render(g, x * TILE_SIZE - camera.getX(), y * TILE_SIZE - camera.getY());
      }
    }
    
    getEntities().forEach(e -> e.render(g, camera));
  }
  
  public void load(JSONObject json)
  {
    try
    {
      name = json.getString("name");
      width = json.getInt("width");
      height = json.getInt("height");
      
      this.tiles = new byte[width * height];
      
      JSONObject tiles = json.getJSONObject("tiles");
      
      for(String key : tiles.keySet())
      {
        this.tiles[Integer.parseInt(key)] = (byte) tiles.getInt(key);
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
  
  public Tile getTile(int x, int y)
  {
    synchronized(this.lock)
    {
      if(x < 0 || y < 0 || x >= width || y >= height)
        return Tile.ROCK;
  
      return Tile.TILES[tiles[x + y * width]];
    }
  }
  
  public void setTile(int x, int y, Tile tile)
  {
    synchronized(this.lock)
    {
      if(x < 0 || y < 0 || x >= width || y >= height)
        return;
  
      tiles[x + y * width] = tile.getID();
    }
  }
  
  public void removeEntity(Entity entity)
  {
    synchronized(lock)
    {
      ENTITIES.remove(entity);
    }
  }
  
  public void addEntity(Entity entity)
  {
    synchronized(this.lock)
    {
      ENTITIES.add(entity);
    }
  }
  
  public LinkedList<Entity> getEntities()
  {
    synchronized(this.lock)
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
