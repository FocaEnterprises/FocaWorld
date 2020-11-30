package com.focasoft.focaworld.server;

import com.focasoft.focaworld.task.AsyncWorker;
import com.focasoft.focaworld.task.Worker;
import com.focasoft.focaworld.world.World;
import com.focasoft.focaworld.world.gen.WorldGenerator;

public class Server implements Runnable
{
  private final SocketManager SOCKET_MANAGER;
  private final ServerPacketManager PACKET_MANAGER;
  private final AsyncWorker ASYNC;
  private final Worker WORKER;
  private final World WORLD;
  
  public Server()
  {
    PACKET_MANAGER = new ServerPacketManager(this);
    SOCKET_MANAGER = new SocketManager(this);
    ASYNC = new AsyncWorker();
    WORKER = new Worker(this);
    WORLD = new World();
  
    WorldGenerator gen = new WorldGenerator(223124453L);
    WORLD.load(gen.generate("Spawn", 128, 128));
    
    WORKER.start();
    ASYNC.start();
    SOCKET_MANAGER.start();
  }
  
  @Override
  public void run()
  {
    PACKET_MANAGER.processPackets();
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
  
  public SocketManager getSocketManager()
  {
    return this.SOCKET_MANAGER;
  }
  
  public ServerPacketManager getPacketManager()
  {
    return this.PACKET_MANAGER;
  }
}
