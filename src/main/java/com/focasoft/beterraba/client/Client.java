package com.focasoft.beterraba.client;

import com.focasoft.beterraba.task.Worker;

public class Client implements Runnable
{
  private final Worker WORKER;
  
  public Client()
  {
    WORKER = new Worker(this);
  }
  
  @Override
  public void run()
  {
  
  }
}