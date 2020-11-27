package com.focasoft.beterraba.player;

import com.focasoft.beterraba.client.Client;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerInput implements KeyListener
{
  private final boolean[] KEYS = new boolean[256];
  
  public PlayerInput(Client client)
  {
    client.addKeyListener(this);
  }
  
  public boolean isPressed(int code)
  {
    if(code >= KEYS.length || code < 0)
      return false;
    
    return KEYS[code];
  }
  
  @Override
  public void keyTyped(KeyEvent e) { }
  
  @Override
  public void keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() >= KEYS.length)
      return;
    
    KEYS[e.getKeyCode()] = true;
  }
  
  @Override
  public void keyReleased(KeyEvent e)
  {
    if(e.getKeyCode() >= KEYS.length)
      return;
    
    KEYS[e.getKeyCode()] = false;
  }
}
