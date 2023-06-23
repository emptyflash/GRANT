package com.logosstudios.GRANT;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Button extends Component implements Entity {
	private static final long serialVersionUID = 1L;
	private float x, y, width, height;
	private boolean activated;
	private Graphics2D graphics;
	private Image current, red, green;
	private ArrayList<Entity> boxes;
	
	public Button(float x, float y, ArrayList<Entity> b, Graphics2D g)
	{
		this.x = x;
		this.y = y;
		width = 60;
		height = 60;
		boxes = b;
		graphics = g;
		activated = false;
		try
		{
			red = ImageIO.read(getClass().getResourceAsStream("/res/images/buttonRed.png"));
			green = ImageIO.read(getClass().getResourceAsStream("/res/images/buttonGreen.png"));
			current = red;
		}
		catch (Exception e){e.printStackTrace();}
	}

	@Override
	public void draw() {
		graphics.drawImage(current, (int)x, (int)y, this);
	}

	@Override
	public void logic() {
		int num = 0;
		for(int i = 0; i < boxes.size(); i++)
		{
			Box box = (Box)boxes.get(i);
			if((box.getX()+box.getWidth()>x&&box.getX()<x+width) && (box.getY()+box.getHeight()>y && box.getY() < y+height))
			{
				num++;
			}
		}
		activated = num > 0;
		if(activated)
		{
			current = green;
		}
		else
		{
			current = red;
		}
		
	}

	public int getX() {
		return (int) x;
	}

	@Override
	public int getY() {
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
		return (int) height;
	}

	@Override
	public void setX(float x) {
		// TODO Auto-generated method stub
		this.x = x;
	}

	@Override
	public void setY(float y) {
		// TODO Auto-generated method stub
		this.y = y;

	}
	public boolean getActivated()
	{
		return activated;
	}

}
