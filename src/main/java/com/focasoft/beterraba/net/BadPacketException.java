package com.focasoft.beterraba.net;

public class BadPacketException extends RuntimeException
{
  public BadPacketException(String msg)
  {
    super(msg);
  }
}
