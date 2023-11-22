package com.focasoft.focaworld.launcher;

import javax.swing.event.MouseInputListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.focasoft.focaworld.launcher.Launcher.WIDTH;

public class UI implements KeyListener, MouseInputListener {
  private final String VALID = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private final Launcher LAUNCHER;

  protected ArrayList<Button> buttons = new ArrayList<>();
  protected Button hover;
  protected Button multi;
  protected Button single;

  private final char[] chars = {'D', 'I', 'G', 'I', 'T', 'E', 'A', 'Q'};
  private int i = 7;

  private boolean click;
  private int x;
  private int y;

  public UI(Launcher launcher) {
    this.LAUNCHER = launcher;
    launcher.addKeyListener(this);
    launcher.addMouseMotionListener(this);
    launcher.addMouseListener(this);

    single = new Button(this, "Singlesplayer", WIDTH / 2 - 120, 260, 100, 40);
    multi = new Button(this, "Multiplayer", WIDTH / 2 + 16, 260, 100, 40);

    buttons.add(multi);
    buttons.add(single);
    launcher.requestFocus();
  }

  public void update() {
    for (Button button : buttons) {
      if (x >= button.x && x < button.x + button.w && y >= button.y && y < button.y + button.h) {
        hover = button;
        break;
      }

      hover = null;
    }

    if (click) {
      click = false;

      if (hover != null) {
        performLaunch();
      }
    }
  }

  private void performLaunch() {
    if (chars[0] == ' ') return;

    String args = "--clientMode ";

    args += new String(chars);
    args += " ";

    if (hover == multi) {
      args += "--multiplayer";
    } else if (hover != single) {
      return;
    }

    LAUNCHER.launch(args);
  }

  public void render(Graphics g) {
    String txt = "Nickname";

    g.setColor(Color.WHITE);
    g.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
    g.drawString(txt, (WIDTH - g.getFontMetrics().stringWidth(txt)) / 2, 130);

    g.setColor(new Color(17, 17, 17));
    g.fillRect((WIDTH - 240) / 2, 152, 240, 58);

    g.setColor(new Color(30, 30, 30));
    g.fillRect((WIDTH - 232) / 2, 156, 232, 50);

    g.setColor(Color.WHITE);
    g.setFont(new Font("Comic Sans MS", Font.BOLD, 26));

    String name = new String(chars);
    g.drawString(name, (WIDTH - g.getFontMetrics().stringWidth(name)) / 2, 190);

    buttons.forEach(e -> e.draw(g));
  }

  @Override
  public void keyTyped(KeyEvent e) {
    boolean match = false;
    char ch = e.getKeyChar();

    for (int index = 0; index < VALID.length(); index++) {
      if (ch == VALID.charAt(index)) {
        match = true;
        break;
      }
    }

    if (!match) return;

    if (i > chars.length - 1) {
      i = chars.length - 1;
      return;
    }

    if (chars[i] != ' ') return;

    chars[i] = ch;
    ++i;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
      if (i >= chars.length) {
        i = chars.length - 1;
      }

      if (i == chars.length - 1 && chars[i] != ' ') {
        chars[i] = ' ';
        return;
      }

      --i;

      if (i < 0) {
        i = 0;
      }

      chars[i] = ' ';
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {
    click = true;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    click = false;
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public void mouseDragged(MouseEvent e) {

  }

  @Override
  public void mouseMoved(MouseEvent e) {
    x = e.getX();
    y = e.getY();
  }
}
