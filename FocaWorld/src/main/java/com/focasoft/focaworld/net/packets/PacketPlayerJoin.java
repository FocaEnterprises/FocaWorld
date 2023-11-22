package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import com.focasoft.focaworld.utils.ByteUtils;

public class PacketPlayerJoin extends Packet {
  private final String NAME;

  private final int X;
  private final int Y;
  private final short ID;

  public PacketPlayerJoin(String name, short id, int x, int y) {
    super(PacketType.PLAYER_JOIN);

    this.NAME = name;
    this.X = x;
    this.Y = y;
    this.ID = id;
  }

  public PacketPlayerJoin(EntityPlayer player) {
    this(player.getName(), player.getId(), player.getX(), player.getY());
  }

  public String getName() {
    return NAME;
  }

  public int getY() {
    return Y;
  }

  public int getX() {
    return X;
  }

  public short getID() {
    return ID;
  }

  @Override
  public byte[] serialize() {
    byte[] data = new byte[11 + NAME.length()];

    data[0] = TYPE.getID();

    ByteUtils.writeShort(data, ID, 1);
    ByteUtils.writeInt(data, X, 3);
    ByteUtils.writeInt(data, Y, 7);
    ByteUtils.writeString(data, NAME, 11);

    return data;
  }

  public static PacketPlayerJoin parse(byte[] data) {
    return new PacketPlayerJoin(
      ByteUtils.readString(data, 11, data.length - 11),
      ByteUtils.readShort(data, 1),
      ByteUtils.readInt(data, 3),
      ByteUtils.readInt(data, 7)
    );
  }
}
