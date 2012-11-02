package com.logosstudios.GRANT;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;


public class Door extends Component implements Entity {
	private static final long serialVersionUID = 1L;
	private float x, y, width, height;
	private Graphics2D graphics;
	private boolean opened;
	private Image current, open, close;
	
	public Door(float x, float y, float w, float h, Graphics2D g, boolean flipped)
	{
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		graphics = g;
		try
		{
			if(!flipped)
			{
				open = ImageIO.read(new File("res/images/doorOpen.png"));
				close = ImageIO.read(new File("res/images/doorClose.png"));
			}
			else
			{
				open = ImageIO.read(new File("res/images/doorOpenFlipped.png"));
				close = ImageIO.read(new File("res/images/doorCloseFlipped.png"));
			}
			current = close;
		}
		catch (Exception e){e.printStackTrace();}
	}
	@Override
	public void draw() {
		graphics.drawImage(current, (int)x, (int)y, this);
	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return (int) x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return (int) y;
	}

	@Override
	public float getPreviousX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getPreviousY() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return (int) width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return (int) width;
	}

	@Override
	public void setX(float x) {
		this.x = x;

	}

	@Override
	public void setY(float y) {
		this.y = y;

	}
	public void open()
	{
		if(!opened)
		{
			TractorBeam.playDoorOpen();
		}
		opened = true;
		current = open;
	}
	public void close()
	{
		if(opened)
		{
			TractorBeam.playDoorClose();
		}
		opened = false;
		current = close;
	}


}
