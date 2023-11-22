package com.focasoft.focaworld.server;

import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketParser;
import com.focasoft.focaworld.net.packets.PacketHandshake;
import com.focasoft.focaworld.utils.ThreadUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class UnknownClient implements Runnable {
  private final Socket SOCKET;
  private final ServerNetworkManager MANAGER;

  public UnknownClient(Socket socket, ServerNetworkManager manager) {
    System.out.println("Novo UnknownClient: " + socket.getInetAddress());

    this.SOCKET = socket;
    this.MANAGER = manager;
    new Thread(this).start();
  }

  @Override
  public void run() {
    DataInputStream in;
    boolean exit = false;
    long last = System.currentTimeMillis();

    try {
      in = new DataInputStream(SOCKET.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
      close("Input failure!");
      return;
    }

    while (!exit) {
      ThreadUtils.sleep(50);

      if (System.currentTimeMillis() - last >= 10000) {
        System.out.println("Nada ainda");
        close("No input!");
        return;
      }

      int len;

      try {
        len = in.readInt();
      } catch (IOException e) {
        continue;
      }

      if (len < 1) continue;

      byte[] data = new byte[len];
      Packet packet;
      exit = true;

      try {
        in.readFully(data, 0, len);
        System.out.println("Entrada UnknowClient: " + Arrays.toString(data));
        packet = PacketParser.parsePacket(data);
      } catch (Exception e) {
        close("Invalid input!");
        continue;
      }

      if (!(packet instanceof PacketHandshake)) {
        close("Invalid credentials!");
        continue;
      }

      MANAGER.handleLogin((PacketHandshake) packet, SOCKET);
    }
  }

  public void close(String reason) {
    try {
      SOCKET.getOutputStream().write(reason.getBytes());
      SOCKET.getOutputStream().flush();
      SOCKET.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
