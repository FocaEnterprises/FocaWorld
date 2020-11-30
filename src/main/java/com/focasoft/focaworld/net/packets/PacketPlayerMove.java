package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;

public class PacketPlayerMove extends Packet
{
  private final String NAME;
  private final int X;
  private final int Y;

  public PacketPlayerMove(String name, int xi, int yi)
  {
    super(PacketType.PLAYER_MOVE);

    this.NAME = name;
    this.X = xi;
    this.Y = yi;

    DATA.put("name", name);
    DATA.put("x", xi);
    DATA.put("Y", yi);
  }

  public String getName()
  {
    return NAME;
  }

  public int getX()
  {
    return X;
  }

  public int getY()
  {
    return Y;
  }
}
