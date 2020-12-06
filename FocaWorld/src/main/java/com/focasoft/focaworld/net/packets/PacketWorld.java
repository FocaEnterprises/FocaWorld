package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import com.focasoft.focaworld.utils.ByteUtils;

public class PacketWorld extends Packet
{
  private final String NAME;

  private final long SEED;

  private final int WIDTH;
  private final int HEIGHT;

  public PacketWorld(String name, long seed, int width, int height)
  {
    super(PacketType.WORLD);

    this.NAME = name;
    this.SEED = seed;
    this.WIDTH = width;
    this.HEIGHT = height;
  }

  @Override
  public byte[] serialize()
  {
    byte[] data = new byte[17 + NAME.length()];
    data[0] = TYPE.getID();

    ByteUtils.writeLong(data, SEED, 1);
    ByteUtils.writeInt(data, WIDTH, 9);
    ByteUtils.writeInt(data, HEIGHT, 13);
    ByteUtils.writeString(data, NAME, 17);

    return data;
  }

  public String getName()
  {
    return NAME;
  }

  public long getSeed()
  {
    return SEED;
  }

  public int getWidth()
  {
    return WIDTH;
  }

  public int getHeight()
  {
    return HEIGHT;
  }

  public static PacketWorld parse(byte[] data)
  {
    return new PacketWorld(
            ByteUtils.readString(data, 17, data.length - 17),
            ByteUtils.readLong(data, 1),
            ByteUtils.readInt(data, 9),
            ByteUtils.readInt(data, 13)
    );
  }
}
