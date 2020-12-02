package com.focasoft.focaworld.launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Button
{
  protected UI ui;
  protected String text;

  protected int x;
  protected int y;
  protected int w;
  protected int h;

  public Button(UI ui, String text, int x, int y, int w, int h)
  {
    this.ui = ui;
    this.text = text;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  public void draw(Graphics g)
  {
    g.setColor(new Color(0x5CAAD4));
    g.fillRect(x, y, w, h);

    g.setFont(new Font("Arial", Font.PLAIN, 14));
    g.setColor(Color.WHITE);
    g.drawString(text, x + (w - g.getFontMetrics().stringWidth(text)) / 2, y + h / 2);

    if(ui.hover == this)
    {
      g.setColor(new Color(0, 0, 0, 212));
      g.fillRect(x, y, w, h);
    }
  }

  public String getText()
  {
    return text;
  }

  public void setText(String text)
  {
    this.text = text;
  }

  public int getX()
  {
    return x;
  }

  public void setX(int x)
  {
    this.x = x;
  }

  public int getY()
  {
    return y;
  }

  public void setY(int y)
  {
    this.y = y;
  }

  public int getHeight()
  {
    return h;
  }

  public int getWidth()
  {
    return w;
  }
}
