package com.focasoft.focaworld.net;

import com.focasoft.focaworld.net.packets.PacketHandshake;
import com.focasoft.focaworld.net.packets.PacketPlayerJoin;
import com.focasoft.focaworld.net.packets.PacketPlayerMove;
import com.focasoft.focaworld.net.packets.PacketPlayerQuit;
import com.focasoft.focaworld.net.packets.PacketWorld;

import static com.focasoft.focaworld.net.PacketType.HANDSHAKE;
import static com.focasoft.focaworld.net.PacketType.PLAYER_JOIN;
import static com.focasoft.focaworld.net.PacketType.PLAYER_MOVE;
import static com.focasoft.focaworld.net.PacketType.PLAYER_QUIT;
import static com.focasoft.focaworld.net.PacketType.WORLD;

public class PacketParser {
  public static Packet parsePacket(byte[] data) throws BadPacketException {
    try {
      byte type = data[0];

      if (type == HANDSHAKE.getID()) {
        return PacketHandshake.parse(data);
      }

      if (type == PLAYER_MOVE.getID()) {
        return PacketPlayerMove.parse(data);
      }

      if (type == PLAYER_JOIN.getID()) {
        return PacketPlayerJoin.parse(data);
      }

      if (type == PLAYER_QUIT.getID()) {
        return PacketPlayerQuit.parse(data);
      }

      if (type == WORLD.getID()) {
        return PacketWorld.parse(data);
      }
    } catch (BadPacketException e) {
      throw e;
    } catch (Exception e) {
      throw new BadPacketException(e.getMessage());
    }

    throw new BadPacketException("Tipo de packet é inválido!");
  }
}
