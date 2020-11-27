package com.focasoft.beterraba.net;

public enum PacketType
{
  HANDSHAKE("HANDSHAKE"),
  ;
  
  private final String DESC;
  
  PacketType(String desc)
  {
    DESC = desc;
  }
  
  public String getDescName()
  {
    return DESC;
  }
}
