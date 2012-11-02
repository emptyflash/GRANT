package com.logosstudios.GRANT;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Box extends Component implements Entity {
	private static final long serialVersionUID = 1L;
	private float x, y, speed, previousX, previousY, width, height;
	private Graphics2D graphics;
	private Image box;
	private boolean move;
	private boolean push;
	private float angle;
	
	public Box(float x, float y, float s, Graphics2D g)
	{
		this.x = x;
		this.y = y;
		this.speed = s;
		graphics = g;
		width = 50;
		height = 50;
		try
		{
			box = ImageIO.read(new File("res/images/box.png"));
		}
		catch (Exception e){e.printStackTrace();}
	}
	public void draw() {
		graphics.drawImage(box, (int)x, (int)y, this);
	}

	public void logic() {
		if(move)
		{
			previousX = x;
			previousY = y;
			if(push)
			{
				x += speed * Math.cos(angle);
				y += speed * Math.sin(angle);
			}
			else
			{
				x -= speed/3 * Math.cos(angle);
				y -= speed/3 * Math.sin(angle);
			}
			move = false;
		}
	}
	public void hit(Beam b)
	{
		move = true;
		angle = b.getAngle();
		push = b.getPush();
	}
	public int getX() {
		return (int) x;
	}
	public int getY() {
		return (int) y;
	}
	public float getPreviousX() {
		return previousX;
	}
	public float getPreviousY() {
		return previousY;
	}
	public void setX(float x)
	{
		this.x = x;
	}
	public void setY(float y)
	{
		this.y = y;
	}
	public int getWidth() {
		return (int) width;
	}
	public int getHeight() {
		return (int) height;
	}

}
