package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import com.focasoft.focaworld.utils.ByteUtils;

public class PacketPlayerQuit extends Packet
{
  private final short ID;

  public PacketPlayerQuit(short id)
  {
    super(PacketType.PLAYER_QUIT);

    this.ID = id;
  }

  public short getID()
  {
    return ID;
  }

  @Override
  public byte[] serialize()
  {
    byte[] data = new byte[3];

    data[0] = TYPE.getID();
    ByteUtils.writeShort(data, ID, 1);

    return data;
  }

  public static PacketPlayerQuit parse(byte[] data)
  {
    return new PacketPlayerQuit(ByteUtils.readShort(data, 1));
  }
}
