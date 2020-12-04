package com.focasoft.focaworld;

import com.focasoft.focaworld.client.Client;
import com.focasoft.focaworld.server.Server;

public class Main
{
  public static void main(String[] args)
  {
    if(args.length == 0)
    {
      System.out.println("No arguments, starting on default mode");
      new Server("ForcedStart");
      return;
    }

    if(args.length < 2)
    {
      System.out.println("Missing arguments!");
      System.exit(0);
      return;
    }

    String name = args[1];

    if(args[0].equals("--clientMode"))
    {
      boolean multiplayer = args.length > 2 && args[2].equals("--multiplayer");
      new Client(name, multiplayer);
      return;
    }
    else if(args[0].equals("--serverMode"))
    {
      new Server(name);
      return;
    }

    System.out.println("Argumentos fracos");
  }
}
