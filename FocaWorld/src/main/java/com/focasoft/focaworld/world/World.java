package com.focasoft.focaworld.world;

import com.focasoft.focaworld.client.render.Camera;
import com.focasoft.focaworld.entity.Entity;
import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.packets.PacketWorld;
import com.focasoft.focaworld.world.gen.WorldGenerator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.focasoft.focaworld.client.Client.HEIGHT;
import static com.focasoft.focaworld.client.Client.TILE_SIZE;
import static com.focasoft.focaworld.client.Client.WIDTH;

public class World
{
  private final AtomicInteger IDS = new AtomicInteger();
  private final Object lock = new Object();
  
  private final LinkedList<Entity> ENTITIES = new LinkedList<>();
  
  private String name;
  private byte[] tiles;
  
  private int width;
  private int height;

  private long seed;

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

  public byte[] getTiles()
  {
    synchronized(this.lock) {
      return Arrays.copyOf(tiles, tiles.length);
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

  public void removeEntity(short id)
  {
    LinkedList<Entity> entities = getEntities();

    for(Entity ent : entities)
    {
      if(ent.getId() == id)
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

  public void addEntities(List<Entity> entities)
  {
    synchronized(this.lock)
    {
      this.ENTITIES.addAll(entities);
    }
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

  public Entity getEntity(short id)
  {
    for(Entity ent : getEntities())
    {
      if(ent.getId() == id)
        return ent;
    }

    return null;
  }

  public EntityPlayer getPlayer(short id)
  {
    for(Entity en : getEntities())
    {
      if((en instanceof EntityPlayer) && en.getId() == id)
        return (EntityPlayer) en;
    }

    return null;
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

  public void load(String name, int width, int height, long seed)
  {
    load(new WorldGenerator(seed).generate(name, width, height));
  }

  public void load(JSONObject json)
  {
    try {
      name = json.getString("name");
      width = json.getInt("width");
      height = json.getInt("height");
      seed = json.getLong("seed");

      this.tiles = new byte[width * height];

      if(!json.isNull("tiles"))
      {
        JSONObject tiles = json.getJSONObject("tiles");

        for(String key : tiles.keySet()) {
          this.tiles[Integer.parseInt(key)] = (byte) tiles.getInt(key);
        }
      }

      if(!json.isNull("entities"))
      {
        JSONArray entities = json.getJSONArray("entities");

        for(int i = 0; i < entities.length(); i++)
        {
          JSONObject entity = entities.getJSONObject(i);
          Entity entityInstance = createInstance(
                  entity.getString("class"),
                  entity.getString("name"),
                  (short) entity.getInt("id"),
                  entity.getInt("x"),
                  entity.getInt("y")
          );

          if(containsEntity(entityInstance.getName()))
            continue;

          addEntity(entityInstance);
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

  public PacketWorld toPacket()
  {
    JSONObject json = new JSONObject();
    JSONArray entities = new JSONArray();

    getEntities().forEach(entity -> {
      JSONObject entityJson = new JSONObject();

      entityJson.put("x", entity.getX());
      entityJson.put("y", entity.getY());
      entityJson.put("id", entity.getId());
      entityJson.put("name", entity.getName());
      entityJson.put("class", entity.getClass().getName());

      entities.put(entityJson);
    });

    json.put("name", name);
    json.put("width", width);
    json.put("height", height);
    json.put("seed", seed);
    json.put("entities", entities);

    return new PacketWorld(json);
  }

  private Entity createInstance(String clazzName, String name, short id, int x, int y) throws Exception
  {
    Class<?> clazz = Class.forName(clazzName);
    Constructor<?> cons = clazz.getDeclaredConstructor(World.class, String.class, short.class, int.class, int.class);
    return (Entity) cons.newInstance(this, name, id, x, y);
  }

  public synchronized int nextEntityID()
  {
    return IDS.getAndIncrement();
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

  public long getSeed()
  {
    return seed;
  }
}
