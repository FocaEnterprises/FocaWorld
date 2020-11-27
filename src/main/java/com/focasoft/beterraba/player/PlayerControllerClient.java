package com.focasoft.beterraba.player;

import static java.awt.event.KeyEvent.*;

import com.focasoft.beterraba.entity.entities.EntityPlayer;

public class PlayerControllerClient
{
  private final EntityPlayer PLAYER;
  private final PlayerInput INPUT;
  
  public PlayerControllerClient(EntityPlayer player, PlayerInput input)
  {
    this.PLAYER = player;
    this.INPUT = input;
  }
  
  public void update()
  {
    setMovingRight(INPUT.isPressed(VK_D));
    setMovingLeft(INPUT.isPressed(VK_A));
    setMovingUp(INPUT.isPressed(VK_W));
    setMovingDown(INPUT.isPressed(VK_S));
  }
  
  public boolean isMovingRight()
  {
    return PLAYER.isMovingRight();
  }
  
  public boolean isMovingLeft()
  {
    return PLAYER.isMovingLeft();
  }
  
  public boolean isMovingDown()
  {
    return PLAYER.isMovingDown();
  }
  
  public boolean isMovingUp()
  {
    return PLAYER.isMovingUp();
  }
  
  public void setMovingLeft(boolean left)
  {
    PLAYER.setMovingLeft(left);
  }
  
  public void setMovingRight(boolean right)
  {
    PLAYER.setMovingRight(right);
  }
  
  public void setMovingDown(boolean down)
  {
    PLAYER.setMovingDown(down);
  }
  
  public void setMovingUp(boolean up)
  {
    PLAYER.setMovingUp(up);
  }
  
  public int getLife()
  {
    return PLAYER.getLife();
  }
  
  public void setLife(int life)
  {
    PLAYER.setLife(life);
  }
  
  public int getMaxLife()
  {
    return PLAYER.getMaxLife();
  }
  
  public void setMaxLife(int maxLife)
  {
    PLAYER.setMaxLife(maxLife);
  }
  
  public String getName()
  {
    return PLAYER.getName();
  }
  
  public void setName(String name)
  {
    PLAYER.setName(name);
  }
  
  public int getX()
  {
    return PLAYER.getX();
  }
  
  public void setX(int x)
  {
    PLAYER.setX(x);
  }
  
  public int getY()
  {
    return PLAYER.getY();
  }
  
  public void setY(int y)
  {
    PLAYER.setY(y);
  }
  
  public void moveX(int move)
  {
    PLAYER.moveX(move);
  }
  
  public void moveY(int move)
  {
    PLAYER.moveY(move);
  }
}
