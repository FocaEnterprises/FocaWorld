package com.focasoft.focaworld.net;

import com.focasoft.focaworld.net.packets.PacketHandshake;
import com.focasoft.focaworld.net.packets.PacketPlayerMove;
import org.json.JSONException;
import org.json.JSONObject;

import static com.focasoft.focaworld.net.PacketType.*;

public class PacketParser
{
  public static Packet parsePacket(String line) throws BadPacketException
  {
    JSONObject json;
    JSONObject data;
    String type;

    try {
      json = new JSONObject(line);
      data = json.getJSONObject("Data");
      type = json.getString("Type");
    } catch(JSONException e) {
      throw new BadPacketException(e.getMessage());
    }

    if(type.equals(HANDSHAKE.getDescName()))
      return PacketHandshake.parse(data);

    if(type.equals(PLAYER_MOVE.getDescName()))
      return PacketPlayerMove.parse(data);

    throw new BadPacketException("Tipo de packet é inválido!");
  }
}
