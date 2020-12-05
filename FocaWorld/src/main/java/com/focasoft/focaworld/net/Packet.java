package com.focasoft.focaworld.net;

public abstract class Packet
{
  protected final PacketType TYPE;
  
  public Packet(PacketType type)
  {
    TYPE = type;
  }

  public abstract byte[] serialize();
}
