package com.focasoft.beterraba.server;

import com.focasoft.beterraba.net.BadPacketException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketManager implements Runnable
{
  private final Server SERVER;
  private final Thread THREAD;
  
  private ServerSocket SOCKET;
  
  private volatile boolean running = true;
  
  public SocketManager(Server server)
  {
    this.SERVER = server;
    this.THREAD = new Thread(this);
    
    try
    {
      SOCKET = new ServerSocket(1);
    }
    catch(IOException e)
    {
      e.printStackTrace();
      System.exit(0);
    }
  }
  
  @Override
  public void run()
  {
    while(running)
    {
      Socket sock;
      
      try
      {
        sock = SOCKET.accept();
      }
      catch(IOException e)
      {
        e.printStackTrace();
        continue;
      }
      
      try
      {
        SERVER.getPacketManager().checkEntry(sock);
      }
      catch(BadPacketException e)
      {
        try
        {
          sock.close();
        }
        catch(IOException ioException)
        {
          ioException.printStackTrace();
        }
      }
    }
  }
  
  public void processPackets()
  {
  
  }
  
  public synchronized void start()
  {
    THREAD.start();
  }
  
  public synchronized void stop()
  {
    running = false;
    
    try
    {
      THREAD.join();
    }
    catch(InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
