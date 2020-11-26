package com.focasoft.beterraba.server;

import com.focasoft.beterraba.net.PacketManager;
import com.focasoft.beterraba.task.AsyncWorker;
import com.focasoft.beterraba.task.Worker;

public class Server implements Runnable
{
  private final SocketManager SOCKET_MANAGER;
  private final PacketManager PACKET_MANAGER;
  private final AsyncWorker ASYNC;
  private final Worker WORKER;
  
  public Server()
  {
    PACKET_MANAGER = new PacketManager(this);
    SOCKET_MANAGER = new SocketManager(this);
    ASYNC = new AsyncWorker();
    WORKER = new Worker(this);
    
    SOCKET_MANAGER.start();
    WORKER.start();
    ASYNC.start();
  }
  
  @Override
  public void run()
  {
    SOCKET_MANAGER.processPackets();
  }
  
  public void stop()
  {
    if(WORKER != null)
      WORKER.kill();
    
    if(ASYNC != null)
      ASYNC.kill();
    
    System.exit(0);
  }
  
  public SocketManager getSocketManager()
  {
    return this.SOCKET_MANAGER;
  }
  
  public PacketManager getPacketManager()
  {
    return this.PACKET_MANAGER;
  }
}