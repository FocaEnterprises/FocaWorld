package com.focasoft.beterraba.server;

import com.focasoft.beterraba.Worker;

public class Server implements Runnable
{
  private final Worker WORKER;
  
  public Server()
  {
    WORKER = new Worker(this);
  }
  
  @Override
  public void run()
  {
  
  }
  
  public void stop()
  {
    if(WORKER != null)
      WORKER.kill();
    
    System.exit(0);
  }
  
}
