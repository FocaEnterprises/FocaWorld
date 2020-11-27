package com.focasoft.beterraba.entity.entities;

import com.focasoft.beterraba.entity.EntityLiving;
import java.awt.Color;
import java.awt.Graphics;

public class EntityPlayer extends EntityLiving
{
  protected boolean right;
  protected boolean left;
  protected boolean up;
  protected boolean down;
  
  public EntityPlayer(String name, int x, int y)
  {
    super(name, x, y, 10);
  }
  
  @Override
  public void tick()
  {
    if(right)
      moveX(1);
    
    if(left)
      moveX(-1);
    
    if(down)
      moveY(1);
    
    if(up)
      moveY(-1);
  }
  
  @Override
  public void render(Graphics g)
  {
    g.setColor(Color.WHITE);
    g.fillRect(x, y, 10, 10);
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
