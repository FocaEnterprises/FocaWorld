package com.focasoft.focaworld.client;

import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketProcessor;
import com.focasoft.focaworld.world.World;

import java.util.LinkedList;

public class ClientPacketProcessor implements PacketProcessor
{
  private final Client CLIENT;
  private final World WORLD;
  private final ClientNetworkManager MANAGER;

  public ClientPacketProcessor(Client client)
  {
    this.CLIENT = client;
    this.WORLD = client.getWorld();
    this.MANAGER = client.getNetworkManager();
  }

  @Override
  public void processPackets(LinkedList<Packet> packets)
  {

  }
}
