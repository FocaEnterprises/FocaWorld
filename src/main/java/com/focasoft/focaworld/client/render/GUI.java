package com.focasoft.focaworld.client.render;

import com.focasoft.focaworld.client.Client;
import com.focasoft.focaworld.player.PlayerControllerClient;

import java.awt.Graphics;
import java.util.ArrayList;

public class GUI
{
  private static GUI instance;

  private final ArrayList<Text> TEXTS = new ArrayList<>();
  private final Client CLIENT;

  private PlayerControllerClient player;

  public GUI(Client client)
  {
    instance = this;
    this.CLIENT = client;
  }

  public void create()
  {
    player = CLIENT.getPlayerController();
  }

  public void addText(Text text)
  {
    TEXTS.add(text);
  }

  public void removeText(Text text)
  {
    TEXTS.remove(text);
  }

  public void render(Graphics g)
  {
    while(TEXTS.size() > 0)
    {
      Text text = TEXTS.get(0);
      text.draw(g);
      TEXTS.remove(0);
    }
  }

  // As vezes é preciso quebrar as regras
  public static GUI getInstance()
  {
    return instance;
  }
}
