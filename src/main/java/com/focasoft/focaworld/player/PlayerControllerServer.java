package com.focasoft.focaworld.player;

import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketParser;
import com.focasoft.focaworld.server.Server;
import com.focasoft.focaworld.server.ServerNetworkManager;
import com.focasoft.focaworld.task.AsyncWorker;
import com.focasoft.focaworld.world.World;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class PlayerControllerServer implements PlayerController, Runnable
{
  private final ServerNetworkManager MANAGER;
  private final EntityPlayer PLAYER;
  private final AsyncWorker WORKER;
  private final Thread THREAD;
  private final Socket SOCKET;
  private final Server SERVER;

  private PrintStream output;
  private Scanner input;

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

    THREAD = new Thread(this, "PlayerController: " + player.getName());
    THREAD.start();

    try {
      this.input = new Scanner(SOCKET.getInputStream());
      this.output = new PrintStream(SOCKET.getOutputStream());
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public void sendMessage(String message)
  {
    WORKER.addTask(() -> {
      output.println(message);
      output.flush();
    });
  }

  public void sendMessage(JSONObject json)
  {
    sendMessage(json.toString());
  }

  public void sendPacket(Packet packet)
  {
    sendMessage(packet.serialize());
  }

  public void destroy()
  {
    listening = false;

    try {
      THREAD.join();
    } catch(InterruptedException e){
      e.printStackTrace();
    }
  }

  private void processInput(String line)
  {
    System.out.println("Input do Client: " + line);
    Packet packet;

    try {
      packet = PacketParser.parsePacket(line);
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
      if(input.hasNextLine())
      {
        processInput(input.nextLine());
      }

      try {
        Thread.sleep(10);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
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
