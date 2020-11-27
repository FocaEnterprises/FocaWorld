package com.focasoft.beterraba.server;

import com.focasoft.beterraba.task.AsyncWorker;
import com.focasoft.beterraba.task.Worker;
import com.focasoft.beterraba.world.World;

public class Server implements Runnable
{
  private final SocketManager SOCKET_MANAGER;
  private final ServerPacketManager PACKET_MANAGER;
  private final AsyncWorker ASYNC;
  private final Worker WORKER;
  private final World WORLD;
  
  public Server()
  {
    PACKET_MANAGER = new ServerPacketManager(this);
    SOCKET_MANAGER = new SocketManager(this);
    ASYNC = new AsyncWorker();
    WORKER = new Worker(this);
    WORLD = new World("Spawn");
    
    WORKER.start();
    ASYNC.start();
    SOCKET_MANAGER.start();
  }
  
  @Override
  public void run()
  {
    PACKET_MANAGER.processPackets();
    WORLD.update();
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
  
  public ServerPacketManager getPacketManager()
  {
    return this.PACKET_MANAGER;
  }
}
