package com.focasoft.beterraba;

import com.focasoft.beterraba.client.Client;
import com.focasoft.beterraba.server.Server;

public class Main
{
  public static void main(String[] args)
  {
    if(args.length > 0)
    {
      if(args[0].equals("--clientMode"))
      {
        new Client();
        return;
      }
      
     new Server();
    }
  }
}
