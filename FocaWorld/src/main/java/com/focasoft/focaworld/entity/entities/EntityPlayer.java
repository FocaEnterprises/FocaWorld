package com.focasoft.focaworld.entity.entities;

import com.focasoft.focaworld.assets.Sprites;
import com.focasoft.focaworld.client.Client;
import com.focasoft.focaworld.client.render.Camera;
import com.focasoft.focaworld.client.render.GUI;
import com.focasoft.focaworld.client.render.Text;
import com.focasoft.focaworld.entity.EntityLiving;
import com.focasoft.focaworld.player.PlayerController;
import com.focasoft.focaworld.world.World;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import static com.focasoft.focaworld.client.Client.TILE_SIZE;

public class EntityPlayer extends EntityLiving {
  private PlayerController controller;

  protected boolean right;
  protected boolean left;
  protected boolean up;
  protected boolean down;

  protected int speed = 1;

  public EntityPlayer(World world, String name, short id, int x, int y) {
    super(world, name, id, x, y, 10);
  }

  @Override
  public void tick() {
    if (right) {
      moveX(speed);
    }

    if (left) {
      moveX(-speed);
    }

    if (down) {
      moveY(speed);
    }

    if (up) {
      moveY(-speed);
    }
  }

  @Override
  public void render(Graphics g, Camera camera) {
    g.drawImage(Sprites.player, x - camera.getX(), y - camera.getY(), TILE_SIZE, TILE_SIZE, null);
    GUI.getInstance().addText(new Text(name, new Font("arial", Font.PLAIN, 12), Color.BLACK, (x - camera.getX()) * Client.SCALE, (y - camera.getY()) * Client.SCALE));
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public PlayerController getController() {
    return controller;
  }

  public void setController(PlayerController controller) {
    this.controller = controller;
  }

  public boolean isMovingRight() {
    return right;
  }

  public boolean isMovingLeft() {
    return left;
  }

  public boolean isMovingDown() {
    return down;
  }

  public boolean isMovingUp() {
    return up;
  }

  public void setMovingLeft(boolean left) {
    this.left = left;
  }

  public void setMovingRight(boolean right) {
    this.right = right;
  }

  public void setMovingDown(boolean down) {
    this.down = down;
  }

  public void setMovingUp(boolean up) {
    this.up = up;
  }
}
