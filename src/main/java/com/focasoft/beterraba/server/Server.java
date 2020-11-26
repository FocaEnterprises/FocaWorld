package com.focasoft.beterraba.server;

import com.focasoft.beterraba.task.AsyncWorker;
import com.focasoft.beterraba.task.Worker;

public class Server implements Runnable
{
  private final SocketManager SOCKET_MANAGER;
  private final AsyncWorker ASYNC;
  private final Worker WORKER;
  
  public Server()
  {
    SOCKET_MANAGER = new SocketManager(this);
    ASYNC = new AsyncWorker();
    WORKER = new Worker(this);
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
}
