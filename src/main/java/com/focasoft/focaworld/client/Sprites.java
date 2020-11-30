package com.focasoft.focaworld.client;

import static com.focasoft.focaworld.client.Client.TILE_SIZE;

import com.focasoft.focaworld.Main;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Sprites
{
  public static BufferedImage player;
  
  public static BufferedImage rock;
  public static BufferedImage dirt;
  public static BufferedImage grass;
  public static BufferedImage sand;
  public static BufferedImage lava;
  public static BufferedImage water;
  public static BufferedImage tree;
  public static BufferedImage cactus;
  public static BufferedImage flower;
  
  private static BufferedImage spritesheet;
  private Sprites() {}
  
  public static boolean init()
  {
    try
    {
      spritesheet = load("sprites/Spritesheet");
      loadDefaults();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return false;
    }
    
    return true;
  }
  
  public static BufferedImage getSprite(int x, int y, int w, int h)
  {
    return spritesheet.getSubimage(x, y, w, h);
  }
  
  private static void loadDefaults()
  {
    player = getSprite(0, 0, TILE_SIZE, TILE_SIZE);
    
    rock = getSprite(0, TILE_SIZE, TILE_SIZE, TILE_SIZE);
    dirt = getSprite(TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
    grass = getSprite(TILE_SIZE * 2, TILE_SIZE, TILE_SIZE, TILE_SIZE);
    sand = getSprite(TILE_SIZE * 3, TILE_SIZE, TILE_SIZE, TILE_SIZE);
    
    cactus = getSprite(0, TILE_SIZE * 2, TILE_SIZE, TILE_SIZE);
    flower = getSprite(TILE_SIZE, TILE_SIZE * 2, TILE_SIZE, TILE_SIZE);
    tree = getSprite(TILE_SIZE * 2, TILE_SIZE * 2, TILE_SIZE, TILE_SIZE);
    
    water = getSprite(0, TILE_SIZE * 3, TILE_SIZE, TILE_SIZE);
    lava = getSprite(0, TILE_SIZE * 4, TILE_SIZE, TILE_SIZE);
  }
  
  private static BufferedImage load(String path) throws IOException
  {
    return ImageIO.read(Main.class.getResource("/" + path + ".png"));
  }
}
