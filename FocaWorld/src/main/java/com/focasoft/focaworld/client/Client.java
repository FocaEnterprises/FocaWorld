package com.focasoft.focaworld.client;

import com.focasoft.focaworld.assets.Resources;
import com.focasoft.focaworld.client.render.Camera;
import com.focasoft.focaworld.client.render.GUI;
import com.focasoft.focaworld.assets.Sprites;
import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.packets.PacketPlayerQuit;
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

  private final GUI UI;
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

  private boolean stoping = false;

  public Client(String nick, boolean multiplayer)
  {
    if(!Resources.setup() || !Sprites.init())
    {
      System.out.println("Falha ao carregar resources! Saindo.");
      System.exit(0);
    }

    Runtime.getRuntime().addShutdownHook(new Thread(this::stop, "Shutdown"));

    this.MULTIPLAYER = multiplayer;
    this.setPreferredSize(new Dimension(scaledWidth(), scaledHeight()));

    FRAME = new JFrame("Foca World - " + (multiplayer ? "Multiplayer" : "Singleplayer"));
    FRAME.setResizable(false);
    FRAME.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    FRAME.add(this);
    FRAME.pack();
    FRAME.setLocationRelativeTo(null);
    FRAME.setVisible(true);

    createBufferStrategy(3);
    GRAPHICS = getBufferStrategy().getDrawGraphics();
    LAYER = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    UI = new GUI(this);
    WORLD = new World();

    if(nick.length() > 8)
      nick = nick.substring(0, 7);

    EntityPlayer player = new EntityPlayer(WORLD, nick, 0, 0);

    WORKER = new Worker(this);
    INPUT = new PlayerInput(this);
    CAMERA = new Camera();
    CONTROLLER = new PlayerControllerClient(this, player, INPUT, CAMERA);
    WORLD.addEntity(player);

    if(multiplayer)
    {
      NETWORK_MANAGER = new ClientNetworkManager(this, "us-1.purplehost.com.br", 10039);

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

    UI.create();
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
    if(!FRAME.isVisible())
    {
      if(!stoping)
      {
        stoping = true;
        stop();
      }

      return;
    }

    if(MULTIPLAYER) {
      NETWORK_MANAGER.processIncomingPackets();
      NETWORK_MANAGER.processOutPackets();
    }

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
    UI.render(GRAPHICS);

    getBufferStrategy().show();
  }
  
  public void stop()
  {
    if(MULTIPLAYER)
      NETWORK_MANAGER.sendPacketNow(new PacketPlayerQuit(getName()));

    WORKER.kill();
  }

  public String getName()
  {
    return CONTROLLER != null ? CONTROLLER.getName() : "FocaClient";
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
