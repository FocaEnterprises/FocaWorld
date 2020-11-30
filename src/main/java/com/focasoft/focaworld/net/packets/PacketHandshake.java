package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;

public class PacketHandshake extends Packet
{
  private final String NAME;
  
  public PacketHandshake(String name)
  {
    super(PacketType.HANDSHAKE);
    this.NAME = name;
    this.DATA.put("name", name);
  }
  
  public String getName()
  {
    return NAME;
  }
}
