package com.focasoft.beterraba.server;

import static com.focasoft.beterraba.net.PacketType.*;

import com.focasoft.beterraba.net.BadPacketException;
import com.focasoft.beterraba.net.Packet;
import com.focasoft.beterraba.net.packets.PacketHandshake;
import java.net.Socket;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerPacketManager
{
  private final Server SERVER;
  
  public ServerPacketManager(Server server)
  {
    this.SERVER = server;
  }
  
  public void processPackets()
  {
  
  }
  
  // -------------------------------------------------------------
  
  public Packet parsePacket(String line)
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
  
  public void checkEntry(Packet packet)
  {
  
  }
  
  public void checkLogin(Packet packet, Socket socket)
  {
  
  }
  
  // --------------------------------------------------------
  
  private PacketHandshake parseHandshake(JSONObject json)
  {
    return null;
  }
}
