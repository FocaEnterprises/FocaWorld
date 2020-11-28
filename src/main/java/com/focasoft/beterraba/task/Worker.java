package com.focasoft.beterraba.task;

public class Worker implements Runnable
{
  private final Runnable RUNNABLE;
  private final Thread THREAD;
  
  private volatile boolean working = true;
  
  public Worker(Runnable runnable)
  {
    this.RUNNABLE = runnable;
    this.THREAD = new Thread(this, "Worker");
  }
  
  @Override
  public void run()
  {
    long timer = System.currentTimeMillis();
    int fps = 0;
    
    while(working)
    {
      try
      {
        RUNNABLE.run();
        Thread.sleep(1000 / 60);
        ++fps;
        
        if(System.currentTimeMillis() - timer >= 1000)
        {
          System.out.println(fps);
          timer = System.currentTimeMillis();
          fps = 0;
        }
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
  
  public synchronized void start()
  {
    THREAD.start();
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
