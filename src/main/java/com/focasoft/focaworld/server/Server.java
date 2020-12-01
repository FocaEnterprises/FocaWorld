package com.focasoft.focaworld.server;

import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.task.AsyncWorker;
import com.focasoft.focaworld.task.Worker;
import com.focasoft.focaworld.world.World;
import com.focasoft.focaworld.world.gen.WorldGenerator;

public class Server implements Runnable
{
  private final SocketHandler SOCKET_HANDLER;
  private final ServerNetworkManager NETWORK_MANAGER;
  private final AsyncWorker ASYNC;
  private final Worker WORKER;
  private final World WORLD;
  
  public Server()
  {
    ASYNC = new AsyncWorker();
    WORKER = new Worker(this);
    WORLD = new World();

    NETWORK_MANAGER = new ServerNetworkManager(this, ASYNC);
    SOCKET_HANDLER = new SocketHandler(this);

    WorldGenerator gen = new WorldGenerator(223124453L);
    WORLD.load(gen.generate("Spawn", 128, 128));
    
    WORKER.start();
    ASYNC.start();
    SOCKET_HANDLER.start();

    System.out.println("Servidor iniciado!");
    System.out.println("Pessoal da host, isso é seguro. Código fonte: ");
    System.out.println("https://github.com/foca-enterprises/beterraba");
  }
  
  @Override
  public void run()
  {
    NETWORK_MANAGER.processPackets();
    WORLD.update();
  }
  
  public void stop()
  {
    if(WORKER != null)
      WORKER.kill();
    
    if(ASYNC != null)
      ASYNC.kill();
    
    System.exit(0);
  }

  // TODO: Carregar dados do jogador de uma config
  public EntityPlayer registerPlayer(String name)
  {
    EntityPlayer player = new EntityPlayer(WORLD, name, 16, 16);
    WORLD.addEntity(player);

    return player;
  }

  public boolean isPlayerRegistered(String name)
  {
    return WORLD.containsEntity(name);
  }

  public World getWorld()
  {
    return WORLD;
  }

  public SocketHandler getSocketHandler()
  {
    return this.SOCKET_HANDLER;
  }
  
  public ServerNetworkManager getNetworkManager()
  {
    return this.NETWORK_MANAGER;
  }
}
