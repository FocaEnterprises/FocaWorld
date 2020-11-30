package com.focasoft.focaworld.net;

public enum PacketType
{
  HANDSHAKE("HANDSHAKE"),
  PLAYER_MOVE("PLAYER_MOVE")
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
