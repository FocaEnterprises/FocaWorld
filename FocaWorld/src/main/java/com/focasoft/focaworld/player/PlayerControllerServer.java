package com.focasoft.focaworld.player;

import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketParser;
import com.focasoft.focaworld.server.Server;
import com.focasoft.focaworld.server.ServerNetworkManager;
import com.focasoft.focaworld.task.AsyncWorker;
import com.focasoft.focaworld.utils.ThreadUtils;
import com.focasoft.focaworld.world.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerControllerServer implements PlayerController, Runnable
{
  private final ServerNetworkManager MANAGER;
  private final EntityPlayer PLAYER;
  private final AsyncWorker WORKER;
  private final Thread THREAD;
  private final Socket SOCKET;
  private final Server SERVER;

  private DataOutputStream output;
  private DataInputStream input;

  private boolean listening;

  public PlayerControllerServer(Server server, EntityPlayer player, Socket socket, AsyncWorker worker)
  {
    this.SERVER = server;
    this.PLAYER = player;
    this.SOCKET = socket;
    this.WORKER = worker;
    this.MANAGER = server.getNetworkManager();

    player.setController(this);
    listening = true;

    try {
      this.input = new DataInputStream(SOCKET.getInputStream());
      this.output = new DataOutputStream(SOCKET.getOutputStream());
    } catch(IOException e) {
      e.printStackTrace();
    }

    THREAD = new Thread(this, "PlayerController: " + player.getName());
    THREAD.start();
  }

  public void sendMessage(byte[] msg)
  {
    if(SOCKET.isClosed())
      return;

    WORKER.addTask(() -> {
      try {
        output.writeInt(msg.length);
        output.write(msg);
      }
      catch(IOException e){
        e.printStackTrace();
      }
    });
  }

  public void sendPacket(Packet packet)
  {
    sendMessage(packet.serialize());
  }

  private void processInput(byte[] data)
  {
    Packet packet;

    try {
      packet = PacketParser.parsePacket(data);
    } catch(BadPacketException e) {
      e.printStackTrace();
      return;
    }

    MANAGER.checkEntry(packet);
  }

  @Override
  public void run()
  {
    while(listening)
    {
      if(SOCKET.isClosed())
      {
        MANAGER.handleLogout(this);
        listening = false;
        continue;
      }

      int len;

      try {
        len = input.readInt();
      } catch(IOException e){
        continue;
      }

      if(len < 1)
        continue;

      byte[] data = new byte[len];

      try {
        input.readFully(data, 0, len);
      } catch(IOException e){
        e.printStackTrace();
        continue;
      }

      processInput(data);
      ThreadUtils.sleep(10);
    }
  }

  @Override
  public World getWorld()
  {
    return PLAYER.getWorld();
  }

  @Override
  public EntityPlayer getPlayer()
  {
    return PLAYER;
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
  public short getId()
  {
    return PLAYER.getId();
  }

  @Override
  public void setId(short id)
  {
    PLAYER.setId(id);
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
