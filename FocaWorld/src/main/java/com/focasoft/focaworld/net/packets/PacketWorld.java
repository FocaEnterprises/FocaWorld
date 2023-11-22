package com.focasoft.focaworld.net.packets;

import com.focasoft.focaworld.net.Packet;
import com.focasoft.focaworld.net.PacketType;
import com.focasoft.focaworld.utils.ByteUtils;
import org.json.JSONObject;

public class PacketWorld extends Packet {
  private final JSONObject JSON;

  public PacketWorld(JSONObject json) {
    super(PacketType.WORLD);

    this.JSON = json;
  }

  @Override
  public byte[] serialize() {
    String jsonString = JSON.toString();
    byte[] data = new byte[jsonString.length() + 1];
    data[0] = TYPE.getID();

    ByteUtils.writeString(data, jsonString, 1);

    return data;
  }

  public JSONObject getJsonWorld() {
    return JSON;
  }

  public static PacketWorld parse(byte[] data) {
    String str = ByteUtils.readString(data, 1, data.length - 1);
    JSONObject json = new JSONObject(str);

    // Validate JSON. Throws JSONException if key is not defined
    json.getString("name");
    json.getLong("seed");
    json.getInt("width");
    json.getInt("height");

    return new PacketWorld(json);
  }
}
