package com.focasoft.focaworld;

import com.focasoft.focaworld.client.Client;
import com.focasoft.focaworld.server.Server;

public class Main
{
  public static void main(String[] args)
  {
    if(args.length == 0)
    {
      new Server();
      return;
    }
    
    if(args.length < 2)
    {
      System.out.println("Missing arguments!");
      System.exit(0);
      return;
    }
    
    if(args[0].equals("--clientMode"))
    {
      String nick = args[1];
      boolean multiplayer = args.length > 2 && args[2].equals("--multiplayer");
      new Client(nick, multiplayer);
    }

    System.out.println("Argumentos fracos");
  }
}
