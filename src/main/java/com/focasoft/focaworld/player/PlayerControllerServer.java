package com.focasoft.focaworld.player;

import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.server.Server;
import com.focasoft.focaworld.world.World;
import org.json.JSONObject;

import java.net.Socket;

public class PlayerControllerServer implements PlayerController, Runnable
{
  private final EntityPlayer PLAYER;
  private final Socket SOCKET;
  private final Server SERVER;

  public PlayerControllerServer(Server server, EntityPlayer player, Socket socket)
  {
    this.SERVER = server;
    this.PLAYER = player;
    this.SOCKET = socket;

    player.setController(this);
  }

  public void sendMessage(String message)
  {

  }

  public void sendMessage(JSONObject json)
  {
    sendMessage(json.toString());
  }

  public void sendPacket(Packet packet)
  {
    sendMessage(packet.serialize());
  }

  @Override
  public void run()
  {

  }

  @Override
  public World getWorld()
  {
    return PLAYER.getWorld();
  }

  @Override
  public String getName()
  {
    return PLAYER.getName();
  }

  @Override
  public void setName(String name)
  {
    PLAYER.setName(name);
  }

  @Override
  public int getSpeed()
  {
    return PLAYER.getSpeed();
  }

  @Override
  public void setSpeed(int speed)
  {
    PLAYER.setSpeed(speed);
  }

  @Override
  public int getLife()
  {
    return PLAYER.getLife();
  }

  @Override
  public void setLife(int life)
  {
    PLAYER.setLife(life);
  }

  @Override
  public int getMaxLife()
  {
    return PLAYER.getMaxLife();
  }

  @Override
  public void setMaxLife(int maxLife)
  {
    PLAYER.setMaxLife(maxLife);
  }

  @Override
  public int getX()
  {
    return PLAYER.getX();
  }

  @Override
  public void setX(int x)
  {
    PLAYER.setX(x);
  }

  @Override
  public int getY()
  {
    return PLAYER.getY();
  }

  @Override
  public void setY(int y)
  {
    PLAYER.setY(y);
  }

  @Override
  public void moveX(int move)
  {
    PLAYER.moveX(move);
  }

  @Override
  public void moveY(int move)
  {
    PLAYER.moveY(move);
  }
}
