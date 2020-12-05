package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import org.json.JSONException;
import org.json.JSONObject;

public class PacketHandshake extends Packet
{
  private final String NAME;
  
  public PacketHandshake(String name)
  {
    super(PacketType.HANDSHAKE);
    this.NAME = name;
    this.DATA.put("name", name);
  }
  
  public String getName()
  {
    return NAME;
  }

  public static PacketHandshake parse(byte[] data)
  {
    PacketHandshake packet;

    try {
      packet = new PacketHandshake(json.getString("name"));
    } catch(JSONException e){
      throw new BadPacketException(e.getMessage());
    }

    return packet;
  }
}
