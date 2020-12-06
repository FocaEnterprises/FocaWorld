package com.focasoft.focaworld.server;

import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketProcessor;
import com.focasoft.focaworld.net.packets.PacketPlayerMove;
import com.focasoft.focaworld.net.packets.PacketPlayerQuit;
import com.focasoft.focaworld.world.World;

import java.util.LinkedList;

public class ServerPacketProcessor implements PacketProcessor
{
  private final ServerNetworkManager MANAGER;
  private final Server SERVER;
  private final World WORLD;

  public ServerPacketProcessor(Server server, ServerNetworkManager manager, World world)
  {
    this.SERVER = server;
    this.WORLD = world;
    this.MANAGER = manager;
  }

  @Override
  public void processPackets(LinkedList<Packet> packets)
  {
    for(Packet packet : packets)
    {
      if(packet instanceof PacketPlayerMove)
        processPlayerMove((PacketPlayerMove) packet);

      if(packet instanceof PacketPlayerQuit)
        processPlayerQuit((PacketPlayerQuit) packet);
    }
  }

  private void processPlayerMove(PacketPlayerMove move)
  {
    EntityPlayer player = WORLD.getPlayer(move.getID());

    player.moveX(move.getX());
    player.moveY(move.getY());

    MANAGER.broadcast(move, move.getID());
  }

  private void processPlayerQuit(PacketPlayerQuit quit)
  {
    MANAGER.handleLogout(quit);
  }
}
