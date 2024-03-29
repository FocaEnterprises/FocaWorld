package com.focasoft.focaworld.world.gen;

import org.json.JSONObject;

public class WorldGenerator {
  private final long SEED;

  public WorldGenerator(long seed) {
    this.SEED = seed;
  }

  public WorldGenerator(String seed) {
    this(seed.hashCode());
  }

  public JSONObject generate(String name, int w, int h) {
    JSONObject json = new JSONObject();
    JSONObject tiles = generateTiles(w, h);

    json.put("name", name);
    json.put("seed", SEED);
    json.put("width", w);
    json.put("height", h);
    json.put("tiles", tiles);

    return json;
  }

  public JSONObject generateTiles(int w, int h) {
    LevelGen.resetAndSetSeed(SEED);
    JSONObject tiles = new JSONObject();

    byte[] gen = LevelGen.createAndValidateMap(w, h)[0];

    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        int i = x + y * w;
        int tile = gen[i];

        tiles.put(String.valueOf(i), tile);
      }
    }

    return tiles;
  }
}
