package com.focasoft.beterraba.net;

public enum PacketType
{
  // Server bound
  IN_HANDSHAKE("HANDSHAKE"),
  IN_LOGIN("LOGIN")
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
