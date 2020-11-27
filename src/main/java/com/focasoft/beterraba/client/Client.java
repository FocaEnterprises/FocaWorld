package com.focasoft.beterraba.client;

import com.focasoft.beterraba.entity.entities.EntityPlayer;
import com.focasoft.beterraba.player.PlayerControllerClient;
import com.focasoft.beterraba.player.PlayerInput;
import com.focasoft.beterraba.task.Worker;
import com.focasoft.beterraba.world.World;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Client extends Canvas implements Runnable
{
  public static final int WIDTH = 320;
  public static final int HEIGHT = 240;
  public static final int SCALE = 2;
  
  private final Worker WORKER;
  private final World WORLD;
  private final PlayerInput INPUT;
  private final PlayerControllerClient CONTROLLER;
  
  private final JFrame FRAME;
  private final BufferedImage LAYER;
  private final Graphics GRAPHICS;
  
  public Client()
  {
    FRAME = new JFrame();
    FRAME.setPreferredSize(new Dimension(scaledWidth(), scaledHeight()));
    FRAME.setResizable(false);
    FRAME.add(this);
    FRAME.pack();
    FRAME.setLocationRelativeTo(null);
    FRAME.setVisible(true);
    
    createBufferStrategy(3);
    GRAPHICS = getBufferStrategy().getDrawGraphics();
    LAYER = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    
    WORLD = new World("World");
    
    EntityPlayer player = new EntityPlayer("Giver", 20, 30);
    
    WORKER = new Worker(this);
    INPUT = new PlayerInput(this);
    CONTROLLER = new PlayerControllerClient(player, INPUT);
    
    WORLD.addEntity(player);
    WORKER.start();
  }
  
  @Override
  public void run()
  {
    tick();
    render();
  }
  
  private void tick()
  {
    CONTROLLER.update();
    WORLD.update();
  }
  
  private void render()
  {
    Graphics g = LAYER.getGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    
    WORLD.render(g);
    
    GRAPHICS.drawImage(LAYER, 0, 0, scaledWidth(), scaledHeight(), null);
  }
  
  public void stop()
  {
    WORKER.kill();
  }
  
  public World getWorld()
  {
    return this.WORLD;
  }
  
  public PlayerInput getInput()
  {
    return this.INPUT;
  }
  
  public static int scaledWidth()
  {
    return WIDTH * SCALE;
  }
  
  public static int scaledHeight()
  {
    return HEIGHT * SCALE;
  }
}
