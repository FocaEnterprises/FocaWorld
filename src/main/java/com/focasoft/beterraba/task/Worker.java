package com.focasoft.beterraba.task;

public class Worker implements Runnable
{
  private final Runnable RUNNABLE;
  private final Thread THREAD;
  
  private volatile boolean working = true;
  
  public Worker(Runnable runnable)
  {
    this.RUNNABLE = runnable;
    this.THREAD = new Thread(this);
    this.THREAD.start();
  }
  
  @Override
  public void run()
  {
    while(working)
    {
      try
      {
        RUNNABLE.run();
        Thread.sleep(1000 / 60);
      }
      catch(InterruptedException e)
      {
        e.printStackTrace();
      }
      catch(Throwable t)
      {
        System.out.println("Erro: " + t.getMessage());
      }
    }
  }
  
  public synchronized void kill()
  {
    working = false;
    
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
