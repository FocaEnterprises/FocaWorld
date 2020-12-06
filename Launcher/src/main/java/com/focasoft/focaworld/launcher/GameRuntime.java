package com.focasoft.focaworld.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameRuntime implements Runnable
{
  private final Launcher LAUNCHER;
  private final Process PROCESS;

  public GameRuntime(Launcher launcher, Process process)
  {
    this.LAUNCHER = launcher;
    this.PROCESS = process;

    new Thread(this, "Game Runtime").start();
  }

  @Override
  public void run() //TODO: Loggar saida
  {
    BufferedReader input = new BufferedReader(new InputStreamReader(PROCESS.getInputStream()));
    BufferedReader errors = new BufferedReader(new InputStreamReader(PROCESS.getErrorStream()));

    while(PROCESS.isAlive())
    {
      String line;

      try {
        while((line = input.readLine()) != null)
        {
          System.out.println(line);
        }

        while((line = errors.readLine()) != null)
        {
          System.out.println(line);
        }
      }
      catch(IOException e)
      {
        e.printStackTrace();
      }

      try {
        Thread.sleep(1000);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }

    LAUNCHER.restore();
  }
}
