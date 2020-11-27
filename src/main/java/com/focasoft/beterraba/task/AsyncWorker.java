package com.focasoft.beterraba.task;

import java.util.LinkedList;

public class AsyncWorker implements Runnable
{
  private final LinkedList<Runnable> QUEUE = new LinkedList<>();
  private final Thread THREAD;
  private volatile boolean working = true;
  
  public AsyncWorker()
  {
    THREAD = new Thread(this, "Async Worker");
  }
  
  @Override
  public void run()
  {
    while(working)
    {
      if(QUEUE.size() == 0)
      {
        try
        {
          Thread.sleep(5);
        }
        catch(InterruptedException e)
        {
          e.printStackTrace();
        }
      }
      
      try
      {
        Runnable run = QUEUE.poll();
        
        if(run != null)
          run.run();
      }
      catch(Throwable t)
      {
        System.out.println("Erro: " + t.getMessage());
      }
    }
  }
  
  public void addTask(Runnable run)
  {
    QUEUE.addLast(run);
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
