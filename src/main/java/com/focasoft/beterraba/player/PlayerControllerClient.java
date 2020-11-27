package com.focasoft.beterraba.player;

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
  
  }
}
