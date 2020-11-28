package com.focasoft.beterraba.client;

import com.focasoft.beterraba.Main;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Sprites
{
  private static BufferedImage spritesheet;
  private Sprites() {}
  
  public static boolean init()
  {
    try
    {
      spritesheet = load("sprites/Spritesheet");
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
  
  private static BufferedImage load(String path) throws IOException
  {
    return ImageIO.read(Main.class.getResource("/" + path + ".png"));
  }
}
