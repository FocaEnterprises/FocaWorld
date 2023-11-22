package com.focasoft.focaworld.client;

import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketProcessor;
import com.focasoft.focaworld.net.packets.PacketPlayerJoin;
import com.focasoft.focaworld.net.packets.PacketPlayerMove;
import com.focasoft.focaworld.net.packets.PacketPlayerQuit;
import com.focasoft.focaworld.net.packets.PacketWorld;
import com.focasoft.focaworld.player.PlayerControllerClient;
import com.focasoft.focaworld.world.World;

import java.util.LinkedList;

public class ClientPacketProcessor implements PacketProcessor {
  private final ClientNetworkManager MANAGER;
  private final PlayerControllerClient PLAYER;
  private final Client CLIENT;
  private final World WORLD;

  public ClientPacketProcessor(Client client) {
    this.CLIENT = client;
    this.WORLD = client.getWorld();
    this.MANAGER = client.getNetworkManager();
    this.PLAYER = client.getPlayerController();
  }

  @Override
  public void processPackets(LinkedList<Packet> packets) {
    for (Packet packet : packets) {
      if (packet instanceof PacketPlayerJoin) {
        processPlayerJoin((PacketPlayerJoin) packet);
      }

      if (packet instanceof PacketPlayerQuit) {
        processPlayerQuit((PacketPlayerQuit) packet);
      }

      if (packet instanceof PacketPlayerMove) {
        processPlayerMove((PacketPlayerMove) packet);
      }

      if (packet instanceof PacketWorld) {
        processWorld((PacketWorld) packet);
      }
    }
  }

  private void processPlayerJoin(PacketPlayerJoin join) {
    if (join.getName().equals(CLIENT.getName())) {
      PlayerControllerClient controller = CLIENT.getPlayerController();
      controller.setLastX(join.getX());
      controller.setLastY(join.getY());
      controller.setEnabled(true);
      return;
    }

    WORLD.addEntity(new EntityPlayer(WORLD, join.getName(), join.getID(), join.getX(), join.getY()));
  }

  private void processPlayerQuit(PacketPlayerQuit quit) {
    if (quit.getID() == PLAYER.getId()) {
      CLIENT.stop();
      return;
    }

    WORLD.removeEntity(quit.getID());
  }

  private void processPlayerMove(PacketPlayerMove move) {
    EntityPlayer player = WORLD.getPlayer(move.getID());

    if (player == null) return;

    player.moveX(move.getX());
    player.moveY(move.getY());
  }

  private void processWorld(PacketWorld world) {
    WORLD.load(world);
  }
}
