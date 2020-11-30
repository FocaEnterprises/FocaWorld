package com.focasoft.focaworld;

import com.focasoft.focaworld.client.Client;
import com.focasoft.focaworld.server.Server;

public class Main
{
  public static void main(String[] args)
  {
    if(args.length > 0)
    {
      if(args[0].equals("--clientMode"))
      {
        new Client(args.length > 1 && args[1].equals("--multiplayer"));
        return;
      }
      
     new Server();
    }
  }
}
