package com.focasoft.focaworld.player;

import com.focasoft.focaworld.world.World;

public interface PlayerController
{
  World getWorld();

  String getName();

  void setName(String name);

  void setSpeed(int speed);

  int getSpeed();

  int getLife();

  void setLife(int life);

  int getMaxLife();

  void setMaxLife(int maxLife);

  int getX();

  void setX(int x);

  int getY();

  void setY(int y);

  void moveX(int move);

  void moveY(int move);
}
