package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import com.focasoft.focaworld.utils.ByteUtils;

public class PacketPlayerMove extends Packet
{
  private final short ID;
  private final byte X;
  private final byte Y;

  public PacketPlayerMove(short id, byte x, byte y)
  {
    super(PacketType.PLAYER_MOVE);

    this.ID = id;
    this.X = x;
    this.Y = y;
  }

  public byte getX()
  {
    return X;
  }

  public byte getY()
  {
    return Y;
  }

  public short getID()
  {
    return ID;
  }

  @Override
  public byte[] serialize()
  {
    byte[] data = new byte[5];

    data[0] = TYPE.getID();
    data[3] = X;
    data[4] = Y;

    ByteUtils.writeShort(data, ID, 1);

    return data;
  }

  public static PacketPlayerMove parse(byte[] data)
  {
    return new PacketPlayerMove(ByteUtils.readShort(data, 1), data[3], data[4]);
  }
}
