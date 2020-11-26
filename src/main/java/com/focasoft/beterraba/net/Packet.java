package com.focasoft.beterraba.net;

import java.util.HashMap;
import org.json.JSONObject;

public abstract class Packet
{
  protected final HashMap<String, Object> DATA = new HashMap<>();
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
    JSONObject data = new JSONObject();
    
    for(String key : DATA.keySet())
    {
      data.put(key, DATA.get(key));
    }
    
    json.put("type", TYPE.getDescName());
    json.put("data", data);
    
    return json;
  }
}
