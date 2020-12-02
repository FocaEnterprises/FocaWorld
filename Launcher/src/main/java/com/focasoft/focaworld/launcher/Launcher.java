package com.focasoft.focaworld.launcher;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;

public class Launcher extends Canvas implements Runnable
{
  private static final int WIDTH = 640;
  private static final int HEIGHT = 480;

  private final JFrame FRAME;
  private final Graphics GRAPHICS;
  private final UI GUI;

  private Thread thread;
  private volatile boolean running;

  private Launcher()
  {
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    FRAME = new JFrame("Foca World - Launcher");
    FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    FRAME.setResizable(false);
    FRAME.add(this);
    FRAME.pack();
    FRAME.setLocationRelativeTo(null);

    createBufferStrategy(3);
    GRAPHICS = getBufferStrategy().getDrawGraphics();

    GUI = new UI(this);

    restore();
  }

  @Override
  public void run()
  {
    while(running)
    {
      update();
      draw();

      try {
        Thread.sleep(50L);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  protected void launch(String args)
  {
    running = false;

    try {
      thread.join();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }

    String cmd = "java -jar " + args;

    Process process;

    try {
      process = Runtime.getRuntime().exec(cmd.trim());
    } catch(IOException e) {
      e.printStackTrace();
      return;
    }

    new GameRuntime(this, process);
    FRAME.setVisible(false);
  }

  public void restore()
  {
    FRAME.setVisible(true);
    running = true;
    thread = new Thread(this, "Launcher");
    thread.start();
  }

  private void update()
  {
    GUI.update();
  }

  private void draw()
  {
    GRAPHICS.setColor(Color.BLACK);
    GRAPHICS.fillRect(0, 0, WIDTH, HEIGHT);

    GUI.render(GRAPHICS);

    getBufferStrategy().show();
  }

  public static void main(String[] args)
  {
    new Launcher();
  }
}
