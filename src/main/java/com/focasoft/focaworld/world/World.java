package com.focasoft.focaworld.world;

import com.focasoft.focaworld.client.Camera;
import com.focasoft.focaworld.entity.Entity;
import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.packets.PacketWorld;
import org.json.JSONObject;

import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.LinkedList;

import static com.focasoft.focaworld.client.Client.HEIGHT;
import static com.focasoft.focaworld.client.Client.TILE_SIZE;
import static com.focasoft.focaworld.client.Client.WIDTH;

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
    int xs = camera.getX() >> 4;
    int ys = camera.getY() >> 4;
    int xf = (camera.getX() + WIDTH) >> 4;
    int yf = (camera.getY() + HEIGHT) >> 4;
    
    for(int x = xs; x <= xf; x++)
    {
      for(int y = ys; y <= yf; y++)
      {
        getTile(x, y).render(g, x * TILE_SIZE - camera.getX(), y * TILE_SIZE - camera.getY());
      }
    }
    
    getEntities().forEach(e -> e.render(g, camera));
  }
  
  public void load(JSONObject json)
  {
    try {
      name = json.getString("name");
      width = json.getInt("width");
      height = json.getInt("height");

      this.tiles = new byte[width * height];

      JSONObject tiles = json.getJSONObject("tiles");

      for(String key : tiles.keySet()) {
        this.tiles[Integer.parseInt(key)] = (byte) tiles.getInt(key);
      }

      if(!json.isNull("entities")) {
        JSONObject entities = json.getJSONObject("entities");

        for(String key : entities.keySet()) {
          JSONObject ent = entities.getJSONObject(key);
          Entity entity = createIntance(ent.getString("class"), key, ent.getInt("x"), ent.getInt("y"));

          if(containsEntity(entity.getName()))
            continue;

          addEntity(entity);
        }
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

  private Entity createIntance(String clazzName, String name, int x, int y) throws Exception
  {
    Class<?> clazz = Class.forName("com.focasoft.focaworld.entity.entities." + clazzName);
    Constructor<?> cons = clazz.getDeclaredConstructor(World.class, String.class, int.class, int.class);
    return (Entity) cons.newInstance(this, name, x, y);
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

  public void removeEntity(String name)
  {
    LinkedList<Entity> entities = getEntities();

    for(Entity ent : entities)
    {
      if(ent.getName().equals(name))
      {
        removeEntity(ent);
        return;
      }
    }
  }

  public void addEntity(Entity entity)
  {
    synchronized(this.lock)
    {
      ENTITIES.add(entity);
    }
  }

  public EntityPlayer getPlayer(String name)
  {
    for(Entity en : getEntities())
    {
      if((en instanceof EntityPlayer) && en.getName().equals(name))
        return (EntityPlayer) en;
    }

    return null;
  }

  public LinkedList<Entity> getEntities()
  {
    synchronized(this.lock)
    {
      return new LinkedList<>(this.ENTITIES);
    }
  }

  public boolean containsEntity(String name)
  {
    for(Entity e : getEntities())
    {
      if(e.getName().equals(name))
        return true;
    }

    return false;
  }

  public PacketWorld toPacket()
  {
    JSONObject json = new JSONObject();
    json.put("name", name);
    json.put("width", width);
    json.put("height", height);

    this.tiles = new byte[width * height];

    JSONObject tiles = new JSONObject();

    byte[] tileArray = getTiles();

    for(int x = 0; x < width; x++)
    {
      for(int y = 0; y < height; y++)
      {
        int i = x + y * width;
        int tile = tileArray[i];

        tiles.put(String.valueOf(i), tile);
      }
    }

    JSONObject entities = new JSONObject();

    getEntities().forEach(e -> {
      JSONObject ent = new JSONObject();
      ent.put("x", e.getX());
      ent.put("y", e.getY());
      ent.put("class", e.getClass().getName());
      entities.put(e.getName(), ent);
    });

    json.put("tiles", tiles);
    json.put("entities", entities);

    return new PacketWorld(json);
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

  public byte[] getTiles()
  {
    synchronized(this.lock) {
      return Arrays.copyOf(tiles, tiles.length);
    }
  }
}
