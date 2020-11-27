package com.focasoft.beterraba.net;

import java.util.HashMap;
import org.json.JSONObject;

public abstract class Packet
{
  protected final JSONObject DATA = new JSONObject();
  protected final PacketType TYPE;
  
  public Packet(PacketType type)
  {
    TYPE = type;
  }
  
  public void appendData(String index, String value)
  {
    DATA.put(index, value);
  }
  
  public void removeData(String index)
  {
    DATA.remove(index);
  }
  
  public JSONObject serialize()
  {
    JSONObject json = new JSONObject();
    
    json.put("Type", TYPE.getDescName());
    json.put("Data", DATA);
    
    return json;
  }
}
