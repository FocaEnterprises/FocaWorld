package com.focasoft.focaworld.utils;

public class ThreadUtils
{
  public static void sleep(long millis)
  {
    sleep(millis, false);
  }

  public static void sleep(long millis, boolean print)
  {
    try {
      Thread.sleep(millis);
    } catch(InterruptedException e) {
      if(print)
        e.printStackTrace();
    }
  }
}
