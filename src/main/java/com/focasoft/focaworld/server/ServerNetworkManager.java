package com.focasoft.focaworld.server;

import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.packets.PacketHandshake;
import com.focasoft.focaworld.net.packets.PacketPlayerJoin;
import com.focasoft.focaworld.net.packets.PacketPlayerMove;
import com.focasoft.focaworld.player.PlayerControllerServer;
import com.focasoft.focaworld.task.AsyncWorker;
import com.focasoft.focaworld.world.World;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

public class ServerNetworkManager
{
  private final LinkedList<PlayerControllerServer> HANDLERS = new LinkedList<>();
  private final LinkedList<Packet> PACKETS = new LinkedList<>();
  private final AsyncWorker WORKER;
  private final Server SERVER;
  private final World WORLD;

  public ServerNetworkManager(Server server, AsyncWorker worker)
  {
    this.SERVER = server;
    this.WORKER = worker;
    this.WORLD = server.getWorld();
  }
  
  public void processPackets()
  {
    for(Packet packet : drainInput())
    {
      if(packet instanceof PacketPlayerMove)
        handlePlayerMove((PacketPlayerMove) packet);
    }
  }
  
  public void checkEntry(Packet packet)
  {
    synchronized(PACKETS)
    {
      PACKETS.add(packet);
    }
  }

  public void broadcast(Packet packet, String... ignores)
  {
    for(PlayerControllerServer handler : getHandlers())
    {
      if(ignore(handler.getName(), ignores))
        continue;

      handler.sendPacket(packet);
    }
  }

  private boolean ignore(String name, String[] ignore)
  {
    for(String s : ignore)
      if(name.equals(s))
        return true;

    return false;
  }

  private LinkedList<Packet> drainInput()
  {
    synchronized(PACKETS)
    {
      return new LinkedList<>(PACKETS);
    }
  }

  public void removeHandler(PlayerControllerServer handler)
  {
    synchronized(HANDLERS)
    {
      HANDLERS.remove(handler);
    }
  }

  public void addHandler(PlayerControllerServer handler)
  {
    synchronized(HANDLERS)
    {
      HANDLERS.add(handler);
    }
  }

  public LinkedList<PlayerControllerServer> getHandlers()
  {
    synchronized(HANDLERS)
    {
      return new LinkedList<>(HANDLERS);
    }
  }

  public void checkLogin(PacketHandshake packet, Socket socket)
  {
    if(SERVER.isPlayerRegistered(packet.getName()))
    {
      try {
        socket.getOutputStream().write(("{\"code\":\"DP\"}").getBytes());
        socket.getOutputStream().flush();
        socket.close();
      } catch(IOException e) {
        e.printStackTrace();
      }

      return;
    }

    EntityPlayer player = SERVER.registerPlayer(packet.getName());
    addHandler(new PlayerControllerServer(SERVER, player, socket, WORKER));
    broadcast(new PacketPlayerJoin(player));
  }

  // --------------------------------------------------------------------------

  private void handlePlayerMove(PacketPlayerMove packet)
  {
    EntityPlayer player = WORLD.getPlayer(packet.getName());
    player.moveX(packet.getX());
    player.moveY(packet.getY());
    broadcast(packet, packet.getName());
  }

}