package com.focasoft.focaworld.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler implements Runnable
{
  private final Server SERVER;
  private final Thread THREAD;
  
  private ServerSocket SOCKET;
  
  private volatile boolean running = true;
  
  public SocketHandler(Server server)
  {
    this.SERVER = server;
    this.THREAD = new Thread(this, "Socket Manager");
    
    try
    {
      SOCKET = new ServerSocket(10215);
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
      
      handleConnection(sock);
    }
  }
  
  private void handleConnection(Socket socket)
  {
    new UnknowClient(socket, SERVER.getNetworkManager());
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
