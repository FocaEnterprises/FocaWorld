package com.focasoft.focaworld.player;

import com.focasoft.focaworld.client.Client;
import com.focasoft.focaworld.client.render.Camera;
import com.focasoft.focaworld.entity.entities.EntityPlayer;
import com.focasoft.focaworld.net.packets.PacketPlayerMove;
import com.focasoft.focaworld.world.World;

import static com.focasoft.focaworld.client.Client.HEIGHT;
import static com.focasoft.focaworld.client.Client.TILE_SIZE;
import static com.focasoft.focaworld.client.Client.WIDTH;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_W;

public class PlayerControllerClient implements PlayerController
{
  private final EntityPlayer PLAYER;
  private final PlayerInput INPUT;
  private final Camera CAMERA;
  private final Client CLIENT;

  private boolean isEnabled = false;
  private int lastX;
  private int lastY;


  public PlayerControllerClient(Client client, EntityPlayer player, PlayerInput input, Camera camera)
  {
    this.CLIENT = client;
    this.PLAYER = player;
    this.INPUT = input;
    this.CAMERA = camera;

    this.lastX = player.getX();
    this.lastY = player.getY();

    player.setController(this);
  }
  
  public void update()
  {
    if(!isEnabled) return;

    setMovingRight(INPUT.isPressed(VK_D));
    setMovingLeft(INPUT.isPressed(VK_A));
    setMovingUp(INPUT.isPressed(VK_W));
    setMovingDown(INPUT.isPressed(VK_S));

    if(CLIENT.isMultiplayer())
    {
      if(lastX != getX() || lastY != getY())
      {
        CLIENT.getNetworkManager().sendPacket(new PacketPlayerMove(getId(), (byte) (getX() - lastX), (byte) (getY() - lastY)));
      }
    }

    lastX = getX();
    lastY = getY();
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
  public short getId()
  {
    return PLAYER.getId();
  }

  @Override
  public void setId(short id)
  {
    PLAYER.setId(id);
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

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }

  public void setLastX(int lastX) {
    this.lastX = lastX;
  }

  public void setLastY(int lastY) {
    this.lastY = lastY;
  }
}
