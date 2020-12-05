package com.focasoft.focaworld.net;

public enum PacketType
{
  HANDSHAKE((byte) 0),
  PLAYER_JOIN((byte) 1),
  PLAYER_QUIT((byte) 2),
  PLAYER_MOVE((byte) 3),
  WORLD((byte) 4),
  ;
  
  private final byte ID;
  
  PacketType(byte id)
  {
    ID = id;
  }
  
  public byte getID()
  {
    return ID;
  }
}
