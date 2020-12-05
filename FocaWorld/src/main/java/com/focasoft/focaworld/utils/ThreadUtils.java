package com.focasoft.focaworld.utils;

public class ThreadUtils
{
  public static void sleep(long millis)
  {
    try {
      Thread.sleep(millis);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }
}
