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

  private void processPlayerMove(PacketPlayerMove packet)
  {
    EntityPlayer player = WORLD.getPlayer(packet.getName());

    player.setMovingRight(packet.isRight());
    player.setMovingLeft(packet.isLeft());
    player.setMovingUp(packet.isUp());
    player.setMovingDown(packet.isDown());

    MANAGER.broadcast(packet, packet.getName());
  }

  private void processPlayerQuit(PacketPlayerQuit quit)
  {
    MANAGER.handleLogout(quit);
  }
}
