package com.focasoft.focaworld.server;

import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketParser;
import com.focasoft.focaworld.net.packets.PacketHandshake;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class UnknowClient implements Runnable
{
  private final Socket SOCKET;
  private final ServerNetworkManager MANAGER;
  
  public UnknowClient(Socket socket, ServerNetworkManager manager)
  {
    this.SOCKET = socket;
    this.MANAGER = manager;
    new Thread(this).start();
  }

  @Override
  public void run()
  {
    System.out.println("Iniciando UnknownClient: " + SOCKET.getInetAddress());
    Scanner scanner;
    boolean exit = false;
    long last = System.currentTimeMillis();

    try
    {
      scanner = new Scanner(SOCKET.getInputStream());
    }
    catch(IOException e)
    {
      e.printStackTrace();
      return;
    }

    while(!exit)
    {
      if(System.currentTimeMillis() - last >= 10000) {
        System.out.println("Nada ainda");
        last = System.currentTimeMillis();
      }

      if(scanner.hasNextLine())
      {
        exit = true;
        String line = scanner.nextLine();
        System.out.println("UnknowClient: " + line);

        try
        {
          Packet packet = PacketParser.parsePacket(line);
          
          if(!(packet instanceof PacketHandshake))
          {
            SOCKET.close();
            continue;
          }
          
          MANAGER.checkLogin((PacketHandshake) packet, SOCKET);
        }
        catch(BadPacketException | IOException e)
        {
          try
          {
            SOCKET.close();
          }
          catch(IOException ioException)
          {
            ioException.printStackTrace();
          }
        }
        
        return;
      }
  
      try
      {
        Thread.sleep(50);
      }
      catch(InterruptedException e)
      {
        e.printStackTrace();
      }
    }

    System.out.println("Fechou.");
  }
}
