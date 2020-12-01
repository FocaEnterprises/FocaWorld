package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import org.json.JSONException;
import org.json.JSONObject;

public class PacketWorld extends Packet
{
  private final JSONObject WORLD;

  public PacketWorld(JSONObject json)
  {
    super(PacketType.WORLD);

    WORLD = json;
    DATA.put("world", json);
  }

  public JSONObject getWorld()
  {
    return WORLD;
  }

  public static PacketWorld parse(JSONObject json)
  {
    PacketWorld packet;

    try{
      packet = new PacketWorld(json.getJSONObject("world"));
    } catch(JSONException e){
      throw new BadPacketException(e.getMessage());
    }

    return packet;
  }
}
