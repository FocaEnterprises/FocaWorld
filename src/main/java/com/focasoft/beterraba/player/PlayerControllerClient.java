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
    PLAYER.setMovingRight(INPUT.isPressed(VK_D));
    PLAYER.setMovingLeft(INPUT.isPressed(VK_A));
    PLAYER.setMovingUp(INPUT.isPressed(VK_W));
    PLAYER.setMovingDown(INPUT.isPressed(VK_S));
  }
}
