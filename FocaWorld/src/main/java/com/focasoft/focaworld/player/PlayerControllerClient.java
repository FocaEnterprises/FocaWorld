package com.focasoft.focaworld.player;

import static com.focasoft.focaworld.client.Client.TILE_SIZE;
import static com.focasoft.focaworld.client.Client.WIDTH;
import static com.focasoft.focaworld.client.Client.HEIGHT;
import static java.awt.event.KeyEvent.*;

import com.focasoft.focaworld.client.render.Camera;
import com.focasoft.focaworld.client.Client;
import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.packets.PacketPlayerMove;
import com.focasoft.focaworld.world.World;

public class PlayerControllerClient implements PlayerController
{
  private final EntityPlayer PLAYER;
  private final PlayerInput INPUT;
  private final Camera CAMERA;
  private final Client CLIENT;

  private boolean lastRight;
  private boolean lastLeft;
  private boolean lastUp;
  private boolean lastDown;

  public PlayerControllerClient(Client client, EntityPlayer player, PlayerInput input, Camera camera)
  {
    this.CLIENT = client;
    this.PLAYER = player;
    this.INPUT = input;
    this.CAMERA = camera;

    player.setController(this);
  }
  
  public void update()
  {
    boolean right = INPUT.isPressed(VK_D);
    boolean left = INPUT.isPressed(VK_A);
    boolean up = INPUT.isPressed(VK_W);
    boolean down = INPUT.isPressed(VK_S);

    setMovingRight(right);
    setMovingLeft(left);
    setMovingUp(up);
    setMovingDown(down);

    if(CLIENT.isMultiplayer())
    {
      if(right != lastRight || left != lastLeft || up != lastUp || down != lastDown)
      {
        CLIENT.getNetworkManager().sendPacket(new PacketPlayerMove(getName(), right, left, up, down));
      }
    }

    lastRight = right;
    lastLeft = left;
    lastUp = up;
    lastDown = down;
  }
  
  public void updateCamera()
  {
    CAMERA.setX(Camera.clamp(getX() - WIDTH / 2, 0, getWorld().getWidth() * TILE_SIZE - WIDTH));
    CAMERA.setY(Camera.clamp(getY() - HEIGHT / 2, 0, getWorld().getHeight() * TILE_SIZE - HEIGHT));
  }

  @Override
  public World getWorld()
  {
    return PLAYER.getWorld();
  }

  @Override
  public EntityPlayer getPlayer()
  {
    return PLAYER;
  }

  @Override
  public String getName()
  {
    return PLAYER.getName();
  }

  @Override
  public void setName(String name)
  {
    PLAYER.setName(name);
  }

  @Override
  public int getSpeed()
  {
    return PLAYER.getSpeed();
  }

  @Override
  public void setSpeed(int speed)
  {
    PLAYER.setSpeed(speed);
  }

  @Override
  public int getLife()
  {
    return PLAYER.getLife();
  }

  @Override
  public void setLife(int life)
  {
    PLAYER.setLife(life);
  }

  @Override
  public int getMaxLife()
  {
    return PLAYER.getMaxLife();
  }

  @Override
  public void setMaxLife(int maxLife)
  {
    PLAYER.setMaxLife(maxLife);
  }

  @Override
  public int getX()
  {
    return PLAYER.getX();
  }

  @Override
  public void setX(int x)
  {
    PLAYER.setX(x);
  }

  @Override
  public int getY()
  {
    return PLAYER.getY();
  }

  @Override
  public void setY(int y)
  {
    PLAYER.setY(y);
  }

  @Override
  public void moveX(int move)
  {
    PLAYER.moveX(move);
  }

  @Override
  public void moveY(int move)
  {
    PLAYER.moveY(move);
  }

  public boolean isMovingRight()
  {
    return PLAYER.isMovingRight();
  }
  
  public boolean isMovingLeft()
  {
    return PLAYER.isMovingLeft();
  }
  
  public boolean isMovingDown()
  {
    return PLAYER.isMovingDown();
  }
  
  public boolean isMovingUp()
  {
    return PLAYER.isMovingUp();
  }
  
  public void setMovingLeft(boolean left)
  {
    PLAYER.setMovingLeft(left);
  }
  
  public void setMovingRight(boolean right)
  {
    PLAYER.setMovingRight(right);
  }
  
  public void setMovingDown(boolean down)
  {
    PLAYER.setMovingDown(down);
  }
  
  public void setMovingUp(boolean up)
  {
    PLAYER.setMovingUp(up);
  }
}
