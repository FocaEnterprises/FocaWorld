package com.focasoft.focaworld.task;

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
    long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    long now;
    
    double unprocessed = 0.0D;
    double nsTick = 1_000_000_000 / 60D;
    
    int fps = 0;
    
    while(working)
    {
      now = System.nanoTime();
      unprocessed += (now - lastTime) / nsTick;
      lastTime = now;
      
      while(unprocessed >= 1)
      {
        RUNNABLE.run();
        ++fps;
        --unprocessed;
      }
      
      if(System.currentTimeMillis() - timer >= 1000)
      {
        //System.out.println(fps);
        fps = 0;
        timer += 1000;
      }
      
      try
      {
        Thread.sleep(5);
      }
      catch(InterruptedException e)
      {
        e.printStackTrace();
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
      THREAD.join(500);
    }
    catch(InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
