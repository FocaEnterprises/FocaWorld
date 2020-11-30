package com.focasoft.focaworld.net;

import com.focasoft.focaworld.net.packets.PacketHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import static com.focasoft.focaworld.net.PacketType.*;

public class PacketParser
{
  public static Packet parsePacket(String line) throws BadPacketException
  {
    JSONObject json;
    String type;
    
    try {
      json = new JSONObject(line);
      type = json.getString("Type");
    } catch(JSONException e) {
      throw new BadPacketException(e.getMessage());
    }
    
    if(type.equals(HANDSHAKE.getDescName()))
      return parseHandshake(json);
    
    throw new BadPacketException("Tipo de packet é inválido!");
  }
  
  private static PacketHandshake parseHandshake(JSONObject json)
  {
    return null;
  }
}
