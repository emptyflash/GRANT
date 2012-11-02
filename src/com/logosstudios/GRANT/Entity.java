package com.logosstudios.GRANT;

public interface Entity {
	public void draw();
	public void logic();
	public int getX();
	public int getY();
	public float getPreviousX();
	public float getPreviousY();
	public int getWidth();
	public int getHeight();
	public void setX(float x);
	public void setY(float y);
}
