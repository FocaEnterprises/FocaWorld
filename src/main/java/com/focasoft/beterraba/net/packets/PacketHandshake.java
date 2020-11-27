package com.focasoft.beterraba.net.packets;

import com.focasoft.beterraba.net.Packet;
import com.focasoft.beterraba.net.PacketType;

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
