package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import org.json.JSONException;
import org.json.JSONObject;

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

  public static PacketPlayerQuit parse(byte[] data)
  {
    PacketPlayerQuit packet;

    try{
      packet = new PacketPlayerQuit(json.getString("name"));
    } catch(JSONException e){
      throw new BadPacketException(e.getMessage());
    }

    return packet;
  }
}
