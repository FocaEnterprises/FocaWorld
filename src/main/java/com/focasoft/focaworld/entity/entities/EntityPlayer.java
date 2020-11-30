package com.focasoft.focaworld.entity.entities;

import static com.focasoft.focaworld.client.Client.TILE_SIZE;

import com.focasoft.focaworld.client.Camera;
import com.focasoft.focaworld.client.Sprites;
import com.focasoft.focaworld.entity.EntityLiving;
import com.focasoft.focaworld.world.World;
import java.awt.Graphics;

public class EntityPlayer extends EntityLiving
{
  protected boolean right;
  protected boolean left;
  protected boolean up;
  protected boolean down;
  
  protected int speed = 1;
  
  public EntityPlayer(World world, String name, int x, int y)
  {
    super(world, name, x, y, 10);
  }
  
  @Override
  public void tick()
  {
    if(right)
      moveX(speed);
    
    if(left)
      moveX(-speed);
    
    if(down)
      moveY(speed);
    
    if(up)
      moveY(-speed);
  }
  
  @Override
  public void render(Graphics g, Camera camera)
  {
    g.drawImage(Sprites.player, x - camera.getX(), y - camera.getY(), TILE_SIZE, TILE_SIZE, null);
  }

  public int getSpeed()
  {
    return speed;
  }

  public void setSpeed(int speed)
  {
    this.speed = speed;
  }

  public boolean isMovingRight()
  {
    return right;
  }
  
  public boolean isMovingLeft()
  {
    return left;
  }
  
  public boolean isMovingDown()
  {
    return down;
  }
  
  public boolean isMovingUp()
  {
    return up;
  }
  
  public void setMovingLeft(boolean left)
  {
    this.left = left;
  }
  
  public void setMovingRight(boolean right)
  {
    this.right = right;
  }
  
  public void setMovingDown(boolean down)
  {
    this.down = down;
  }
  
  public void setMovingUp(boolean up)
  {
    this.up = up;
  }
}
