package com.focasoft.focaworld.launcher;

import javax.swing.event.MouseInputListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UI implements KeyListener, MouseInputListener
{
  private final String VALID = "ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private final Launcher LAUNCHER;

  protected ArrayList<Button> buttons = new ArrayList<>();
  protected Button hover;
  protected Button multi;
  protected Button single;

  private char[] chars = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
  private int i = 0;

  private boolean click;
  private int x;
  private int y;

  public UI(Launcher launcher)
  {
    this.LAUNCHER = launcher;
    launcher.addKeyListener(this);
    launcher.addMouseMotionListener(this);
    launcher.addMouseListener(this);

    multi = new Button(this, "Multiplayer", 120, 120, 80, 40);
    single = new Button(this, "Singlesplayer", 120, 180, 80, 40);

    buttons.add(multi);
    buttons.add(single);
  }

  public void update()
  {
    for(Button button : buttons)
    {
      if(x >= button.x && x < button.x + button.w && y >= button.y && y < button.y + button.h)
      {
        hover = button;
        break;
      }

      hover = null;
    }

    if(click)
    {
      click = false;

      if(hover != null)
      {
        performLaunch();
      }
    }
  }

  private void performLaunch()
  {
    if(chars[0] == 0)
      return;

    String args = "--clientMode ";

    args += new String(chars);
    args += " ";

    if(hover == multi)
    {
      args += "--multiplayer";
    }
    else if(hover != single)
    {
      return;
    }

    LAUNCHER.launch(args);
  }

  public void render(Graphics g)
  {
    buttons.forEach(e -> e.draw(g));
    g.setColor(Color.WHITE);
    g.setFont(new Font("arial", Font.PLAIN, 16));
    g.drawString(new String(chars), 200, 200);
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
    boolean match = false;
    char ch = e.getKeyChar();

    for(int index = 0; index < VALID.length(); index++)
    {
      if(ch == VALID.charAt(index))
      {
        match = true;
        break;
      }
    }

    if(!match)
      return;

    if(i >= chars.length)
      return;

    chars[i] = ch;
    ++i;
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
    {
      if(i > chars.length -1)
        i = chars.length -1;

      chars[i] = ' ';
      --i;

      if(i < 0)
        i = 0;
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {

  }

  @Override
  public void mouseClicked(MouseEvent e)
  {

  }

  @Override
  public void mousePressed(MouseEvent e)
  {
    click = true;
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    click = false;
  }

  @Override
  public void mouseEntered(MouseEvent e)
  {

  }

  @Override
  public void mouseExited(MouseEvent e)
  {

  }

  @Override
  public void mouseDragged(MouseEvent e)
  {

  }

  @Override
  public void mouseMoved(MouseEvent e)
  {
    x = e.getX();
    y = e.getY();
  }
}
