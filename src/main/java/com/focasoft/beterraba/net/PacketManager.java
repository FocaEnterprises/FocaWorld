package com.focasoft.beterraba.net;

import com.focasoft.beterraba.server.Server;
import java.net.Socket;

public class PacketManager
{
  private final Server SERVER;
  
  public PacketManager(Server server)
  {
    this.SERVER = server;
  }
  
  public void checkEntry(Socket socket)
  {
  
  }
}
