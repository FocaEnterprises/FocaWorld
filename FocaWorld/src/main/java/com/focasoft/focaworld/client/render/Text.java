package com.focasoft.focaworld.client.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Text {
  private String text;
  private Font font;
  private Color color;

  private int x;
  private int y;

  public Text(String text, Font font, Color color, int x, int y) {
    this.text = text;
    this.font = font;
    this.color = color;
    this.x = x;
    this.y = y;
  }

  public void draw(Graphics g) {
    g.setColor(color);
    g.setFont(font);
    g.drawString(text, x, y);
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Font getFont() {
    return font;
  }

  public void setFont(Font font) {
    this.font = font;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
}
