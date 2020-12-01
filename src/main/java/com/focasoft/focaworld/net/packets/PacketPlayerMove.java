package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import org.json.JSONException;
import org.json.JSONObject;

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
    DATA.put("y", yi);
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

  public static PacketPlayerMove parse(JSONObject json)
  {
    PacketPlayerMove packet;

    try {
      packet = new PacketPlayerMove(json.getString("name"), json.getInt("x"), json.getInt("y"));
    } catch(JSONException e){
      throw new BadPacketException(e.getMessage());
    }

    return packet;
  }
}
