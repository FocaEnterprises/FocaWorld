package com.focasoft.focaworld.world;

import static com.focasoft.focaworld.client.Client.TILE_SIZE;

import java.awt.Graphics;

// https://github.com/skeeto/Minicraft/blob/master/src/com/mojang/ld22/level/tile/Tile.java
public class Tile {
  public static final Tile[] TILES = new Tile[128];

  public static final Tile ROCK = new Tile(Material.ROCK.ID);
  public static final Tile DIRT = new Tile(Material.DIRT.ID);
  public static final Tile GRASS = new Tile(Material.GRASS.ID);
  public static final Tile SAND = new Tile(Material.SAND.ID);
  public static final Tile CACTUS = new Tile(Material.CACTUS.ID);
  public static final Tile WATER = new Tile(Material.WATER.ID);
  public static final Tile LAVA = new Tile(Material.LAVA.ID);
  public static final Tile FLOWER = new Tile(Material.FLOWER.ID);
  public static final Tile TREE = new Tile(Material.TREE.ID);

  private final byte ID;

  public Tile(byte id) {
    this.ID = id;

    if (TILES[id] != null) {
      throw new IllegalArgumentException("Duplicated ID: " + id);
    }

    TILES[id] = this;
  }

  public void render(Graphics g, int x, int y) {
    g.drawImage(getType().SPRITE, x, y, TILE_SIZE, TILE_SIZE, null);
  }

  public Material getType() {
    return Material.getByID(ID);
  }

  public byte getID() {
    return ID;
  }
}
