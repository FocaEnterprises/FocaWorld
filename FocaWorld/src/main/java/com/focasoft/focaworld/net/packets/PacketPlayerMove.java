package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import org.json.JSONException;
import org.json.JSONObject;

public class PacketPlayerMove extends Packet
{
  private final String NAME;
  private final boolean RIGHT;
  private final boolean LEFT;
  private final boolean UP;
  private final boolean DOWN;

  public PacketPlayerMove(String name, boolean right, boolean left, boolean up, boolean down)
  {
    super(PacketType.PLAYER_MOVE);

    this.NAME = name;
    this.RIGHT = right;
    this.LEFT = left;
    this.UP = up;
    this.DOWN = down;

    DATA.put("name", name);
    DATA.put("right", right);
    DATA.put("left", left);
    DATA.put("up", up);
    DATA.put("down", down);
  }

  public String getName()
  {
    return NAME;
  }

  public boolean isRight()
  {
    return RIGHT;
  }

  public boolean isLeft()
  {
    return LEFT;
  }

  public boolean isDown()
  {
    return DOWN;
  }

  public boolean isUp()
  {
    return UP;
  }

  public static PacketPlayerMove parse(JSONObject json)
  {
    PacketPlayerMove packet;

    try {
      packet = new PacketPlayerMove(
              json.getString("name"),
              json.getBoolean("right"),
              json.getBoolean("left"),
              json.getBoolean("up"),
              json.getBoolean("down"));
    } catch(JSONException e){
      throw new BadPacketException(e.getMessage());
    }

    return packet;
  }
}
