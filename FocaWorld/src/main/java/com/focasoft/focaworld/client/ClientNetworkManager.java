package com.focasoft.focaworld.client;

import com.focasoft.focaworld.net.BadPacketException;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketParser;
import com.focasoft.focaworld.net.packets.PacketHandshake;
import com.focasoft.focaworld.task.AsyncWorker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;

public class ClientNetworkManager implements Runnable
{
  private final LinkedList<byte[]> OUT_MESSAGES = new LinkedList<>();
  private final LinkedList<Packet> IN_MESSAGES = new LinkedList<>();

  private final ClientPacketProcessor PROCESSOR;
  private final AsyncWorker WORKER;
  private final Client CLIENT;
  private final String HOST;

  private final int PORT;
  
  private DataInputStream input;
  private DataOutputStream output;

  private Socket socket;
  private Thread thread;
  
  private volatile boolean running;
  
  public ClientNetworkManager(Client client, String hostname, int port)
  {
    this.HOST = hostname;
    this.PORT = port;

    this.PROCESSOR = new ClientPacketProcessor(client);
    this.CLIENT = client;

    WORKER = new AsyncWorker();
    WORKER.start();
  }
  
  public void connect() throws IOException
  {
    socket = new Socket(HOST, PORT);
    input = new DataInputStream(socket.getInputStream());
    output = new DataOutputStream(socket.getOutputStream());
    thread = new Thread(this, "Network Manager");
    running = true;
    thread.start();

    sendPacket(new PacketHandshake(CLIENT.getName()));
  }
  
  public synchronized void disconnect() throws IOException
  {
    running = false;

    OUT_MESSAGES.clear();
    IN_MESSAGES.clear();

    try {
      thread.join();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }

    WORKER.flushAndKill();
    System.out.println("BELEZA");
    socket.close();
  }
  
  private void parseInput(byte[] data)
  {
    System.out.println("Recebi: " + Arrays.toString(data));
    Packet packet;

    try {
      packet = PacketParser.parsePacket(data);
    } catch(BadPacketException e) {
      e.printStackTrace();
      return;
    }
    
    synchronized(IN_MESSAGES)
    {
      IN_MESSAGES.add(packet);
    }
  }
  
  public void sendMessage(byte[] msg)
  {
    if(!running)
      return;

    synchronized(OUT_MESSAGES)
    {
      OUT_MESSAGES.add(msg);
    }
  }

  public void sendPacket(Packet packet)
  {
    if(!running)
      return;

    sendMessage(packet.serialize());
  }

  protected void sendPacketNow(Packet packet) throws IOException
  {
    sendMessageNow(packet.serialize());
  }

  protected void sendMessageNow(byte[] msg) throws IOException
  {
    if(!running)
      return;

    output.writeInt(msg.length);
    output.write(msg);
    output.flush();
  }

  public LinkedList<Packet> drainInput()
  {
    LinkedList<Packet> in;
    
    synchronized(IN_MESSAGES)
    {
      in = new LinkedList<>(IN_MESSAGES);
      IN_MESSAGES.clear();
    }
    
    return in;
  }
  
  private LinkedList<byte[]> drainOut()
  {
    LinkedList<byte[]> out;

    synchronized(OUT_MESSAGES)
    {
      out = new LinkedList<>(OUT_MESSAGES);
      OUT_MESSAGES.clear();
    }

    return out;
  }

  public void processIncomingPackets()
  {
    PROCESSOR.processPackets(drainInput());
  }

  public void processOutPackets()
  {
    if(OUT_MESSAGES.size() == 0)
      return;

    WORKER.addTask(() -> {
      LinkedList<byte[]> out = ClientNetworkManager.this.drainOut();

      out.forEach(e -> {
        try {
          ClientNetworkManager.this.sendMessageNow(e);
          System.out.println("Escrevi: " + Arrays.toString(e));
        }
        catch(IOException ex) {
          ex.printStackTrace();
        }
      });
    });
  }

  private void read() throws IOException
  {
    int len;

    try { // Exception if input hasn't four bytes
      len = input.readInt();
    } catch(IOException ex){
      return;
    }

    if(len < 1)
      return;

    byte[] data = new byte[len];

    input.readFully(data, 0, len);
    parseInput(data);
  }

  @Override
  public void run()
  {
    while(running)
    {
      try {
        read();
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
  }
}
