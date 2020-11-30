package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import org.json.JSONException;
import org.json.JSONObject;

public class PacketPlayerJoin extends Packet
{
  private final String NAME;

  private final int X;
  private final int Y;

  public PacketPlayerJoin(String name, int x, int y)
  {
    super(PacketType.PLAYER_JOIN);

    this.NAME = name;
    this.X = x;
    this.Y = y;

    DATA.put("name", name);
    DATA.put("x", x);
    DATA.put("y", y);
  }
  
  public PacketPlayerJoin(EntityPlayer player)
  {
    this(player.getName(), player.getX(), player.getY());
  }

  public String getName()
  {
    return NAME;
  }

  public int getY()
  {
    return Y;
  }

  public int getX()
  {
    return X;
  }

  public static PacketPlayerJoin parse(JSONObject json)
  {
    PacketPlayerJoin packet;

    try{
      packet = new PacketPlayerJoin(json.getString("name"), json.getInt("x"), json.getInt("y"));
    } catch(JSONException e){
      throw new BadPacketException(e.getMessage());
    }

    return packet;
  }
}
