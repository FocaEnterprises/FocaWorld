package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;

public class PacketPlayerQuit extends Packet
{
  private final String NAME;

  public PacketPlayerQuit(String name)
  {
    super(PacketType.PLAYER_QUIT);

    this.NAME = name;
    DATA.put("name", name);
  }

  public String getName()
  {
    return NAME;
  }
}
