package com.focasoft.focaworld.server;

import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketParser;
import com.focasoft.focaworld.net.packets.PacketHandshake;
import com.focasoft.focaworld.utils.ThreadUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

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
    System.out.println("Novo UnknownClient: " + SOCKET.getInetAddress());
    DataInputStream in;
    boolean exit = false;
    long last = System.currentTimeMillis();

    try
    {
      in = new DataInputStream(SOCKET.getInputStream());
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

      if(in.hasNextLine())
      {
        exit = true;
        String line = in.nextLine();
        System.out.println("UnknowClient: " + line);

        try
        {
          Packet packet = PacketParser.parsePacket(line);
          
          if(!(packet instanceof PacketHandshake))
          {
            SOCKET.close();
            continue;
          }
          
          MANAGER.handleLogin((PacketHandshake) packet, SOCKET);
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

      ThreadUtils.sleep(50);
    }
  }

  public void close(String reason)
  {
    try
    {
      SOCKET.getOutputStream().write(reason.getBytes());
      SOCKET.getOutputStream().flush();
      SOCKET.close();
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }
}
