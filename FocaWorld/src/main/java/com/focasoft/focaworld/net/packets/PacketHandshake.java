package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import com.focasoft.focaworld.utils.ByteUtils;

public class PacketHandshake extends Packet
{
  private final String NAME;
  
  public PacketHandshake(String name)
  {
    super(PacketType.HANDSHAKE);
    this.NAME = name;
  }
  
  public String getName()
  {
    return NAME;
  }

  @Override
  public byte[] serialize()
  {
    byte[] data = new byte[NAME.length() + 1];
    data[0] = TYPE.getID();

    ByteUtils.writeString(data, NAME, 1);

    return data;
  }

  public static PacketHandshake parse(byte[] data)
  {
    char[] chars = new char[data.length -1];

    for(int i = 1; i < data.length; i++)
    {
      chars[i -1] = (char) data[i];
    }

    return new PacketHandshake(new String(chars));
  }
}
