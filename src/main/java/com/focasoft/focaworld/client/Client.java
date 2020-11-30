package com.focasoft.focaworld.client;

import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.player.PlayerControllerClient;
import com.focasoft.focaworld.player.PlayerInput;
import com.focasoft.focaworld.task.Worker;
import com.focasoft.focaworld.world.World;
import com.focasoft.focaworld.world.gen.WorldGenerator;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;

public class Client extends Canvas implements Runnable
{
  public static final int WIDTH = 480;
  public static final int HEIGHT = 320;
  public static final int SCALE = 2;
  public static final int TILE_SIZE = 16;
  
  private final Worker WORKER;
  private final World WORLD;
  private final Camera CAMERA;
  private final PlayerInput INPUT;
  private final PlayerControllerClient CONTROLLER;
  private final ClientNetworkManager NETWORK_MANAGER;
  
  private final JFrame FRAME;
  private final BufferedImage LAYER;
  private final Graphics GRAPHICS;
  
  private final boolean MULTIPLAYER;
  
  public Client(boolean multiplayer)
  {
    if(!Sprites.init())
    {
      System.out.println("Falha ao carregar sprites! Saindo.");
      System.exit(0);
    }
    
    this.MULTIPLAYER = multiplayer;
    this.setPreferredSize(new Dimension(scaledWidth(), scaledHeight()));
    
    FRAME = new JFrame("Foca World - " + (multiplayer ? "Multiplayer" : "Singleplayer"));
    FRAME.setResizable(false);
    FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    FRAME.add(this);
    FRAME.pack();
    FRAME.setLocationRelativeTo(null);
    FRAME.setVisible(true);
    
    createBufferStrategy(3);
    GRAPHICS = getBufferStrategy().getDrawGraphics();
    LAYER = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    
    WORLD = new World();
    
    EntityPlayer player = new EntityPlayer(WORLD, "Giver", 20, 30);
    
    WORKER = new Worker(this);
    INPUT = new PlayerInput(this);
    CAMERA = new Camera();
    CONTROLLER = new PlayerControllerClient(this, player, INPUT, CAMERA);
    WORLD.addEntity(player);

    if(multiplayer) {
      NETWORK_MANAGER = new ClientNetworkManager("br-3.enxadahost.com", 10215);
    
      try {
        NETWORK_MANAGER.connect();
      } catch(IOException e) {
        System.out.println("Falha ao abrir conexão com o servidor");
        e.printStackTrace();
        System.exit(0);
        return;
      }
    
    } else {
      WorldGenerator gen = new WorldGenerator(223124453L);
      WORLD.load(gen.generate("World", 128, 128));
      NETWORK_MANAGER = null;
    }
    
    WORKER.start();
  }
  
  @Override
  public synchronized void run()
  {
    tick();
    render();
  }
  
  private void tick()
  {
    if(!WORLD.isLoaded())
      return;
    
    CONTROLLER.update();
    WORLD.update();
  }
  
  private void render()
  {
    Graphics g = LAYER.getGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    
    if(WORLD.isLoaded())
    {
      CONTROLLER.updateCamera();
      WORLD.render(g, CAMERA);
    }
    
    GRAPHICS.drawImage(LAYER, 0, 0, scaledWidth(), scaledHeight(), null);
    
    getBufferStrategy().show();
  }
  
  public void stop()
  {
    FRAME.setVisible(false);
    WORKER.kill();
  }
  
  public PlayerControllerClient getPlayerController()
  {
    return this.CONTROLLER;
  }
  
  public ClientNetworkManager getNetworkManager()
  {
    return this.NETWORK_MANAGER;
  }
  
  public boolean isMultiplayer()
  {
    return this.MULTIPLAYER;
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
