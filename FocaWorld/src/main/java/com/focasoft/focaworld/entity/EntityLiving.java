package com.focasoft.focaworld.entity;

import com.focasoft.focaworld.world.World;

public abstract class EntityLiving extends Entity
{
  protected int life;
  protected int maxLife;
  
  public EntityLiving(World world, String name, short id, int x, int y, int life, int maxLife)
  {
    super(world, name, id, x, y);
    
    this.life = life;
    this.maxLife = maxLife;
  }
  
  public EntityLiving(World world, String name, short id, int x, int y, int maxLife)
  {
    this(world, name, id, x, y, maxLife, maxLife);
  }
  
  public int getLife()
  {
    return life;
  }
  
  public void setLife(int life)
  {
    this.life = life;
    
    if(life > maxLife)
      life = maxLife;
    else if(life < 0)
      life = 0;
  }
  
  public int getMaxLife()
  {
    return maxLife;
  }
  
  public void setMaxLife(int maxLife)
  {
    if(maxLife > 0)
      this.maxLife = maxLife;
  }
}
