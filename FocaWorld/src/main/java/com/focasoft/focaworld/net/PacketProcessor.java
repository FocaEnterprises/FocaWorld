package com.focasoft.focaworld.net;

import java.util.LinkedList;

public interface PacketProcessor {
  void processPackets(LinkedList<Packet> packets);
}
