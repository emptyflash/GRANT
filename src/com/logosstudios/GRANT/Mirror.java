package com.logosstudios.GRANT;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Mirror extends Component implements Entity {
	private static final long serialVersionUID = 1L;
	private float x, y, previousX, previousY, width, height;
	private String direction;
	private Image image;
	private Graphics2D graphics;
	private ArrayList<Beam> beams;
	private int id;
	
	public Mirror(float x, float y, String dir, Graphics2D g, ArrayList<Beam> b, int id)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		direction = dir;
		graphics = g;
		width = 50;
		height = 50;
		beams = b;
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream("/res/images/mirror" + direction + ".png"));
		}
		catch (Exception e){e.printStackTrace();}
	}
	public void draw() 
	{
		graphics.drawImage(image, (int)x, (int)y, this);
	}
	public void logic() 
	{
		
	}
	public void hit(ArrayList<Beam> b, int i)
	{
		Beam temp = b.get(i);
		temp.setId(id);
		float angle = (float)(Math.PI/4);
		
		if(direction.equals("UpLeft"))
		{
			if(temp.getAngle() ==  2 * -angle)
			{
				temp.updateAngle((float)(0));
			}
			else if(temp.getAngle() ==  3 * -angle)
			{
				temp.updateAngle(angle);
			}
			else if(temp.getAngle() ==  4 * angle)
			{
				temp.updateAngle(2 * angle);
			}
			else
			{
				previousX = x;
				previousY = y;
				if(temp.getPush())
				{
					x += 5 * Math.cos(temp.getAngle());
					y += 5 * Math.sin(temp.getAngle());
					b.remove(i);
				}
				else
				{
					x -= 5/2 * Math.cos(temp.getAngle());
					y -= 5/2 * Math.sin(temp.getAngle());
					b.remove(i);
				}
			}
		}
		else if(direction.equals("DownRight"))
		{
			if(temp.getAngle() ==  0)
			{
				temp.updateAngle((float)(-2 * angle));
			}
			else if(temp.getAngle() ==  2 * angle)
			{
				temp.updateAngle(4 * angle);
			}
			else if(temp.getAngle() ==  1 * angle)
			{
				temp.updateAngle(-3 * angle);
			}
			else
			{
				previousX = x;
				previousY = y;
				if(temp.getPush())
				{
					x += 5 * Math.cos(temp.getAngle());
					y += 5 * Math.sin(temp.getAngle());
					b.remove(i);
				}
				else
				{
					x -= 5/2 * Math.cos(temp.getAngle());
					y -= 5/2 * Math.sin(temp.getAngle());
					b.remove(i);
				}
			}
		}
		else if(direction.equals("DownLeft"))
		{
			if(temp.getAngle() ==  4 * angle)
			{
				temp.updateAngle((float)(-2 * angle));
			}
			else if(temp.getAngle() ==  2 * angle)
			{
				temp.updateAngle(0);
			}
			else if(temp.getAngle() ==  3 * angle)
			{
				temp.updateAngle(-angle);
			}
			else
			{
				previousX = x;
				previousY = y;
				if(temp.getPush())
				{
					x += 5 * Math.cos(temp.getAngle());
					y += 5 * Math.sin(temp.getAngle());
					b.remove(i);
				}
				else
				{
					x -= 5/2 * Math.cos(temp.getAngle());
					y -= 5/2 * Math.sin(temp.getAngle());
					b.remove(i);
				}
			}
		}
		else if(direction.equals("UpRight"))
		{
			if(temp.getAngle() ==  0)
			{
				temp.updateAngle((float)(2 * angle));
			}
			else if(temp.getAngle() ==  -2 * angle)
			{
				temp.updateAngle(4 * angle);
			}
			else if(temp.getAngle() ==  -1 * angle)
			{
				temp.updateAngle(3 * angle);
			}
			else
			{
				previousX = x;
				previousY = y;
				if(temp.getPush())
				{
					x += 5 * Math.cos(temp.getAngle());
					y += 5 * Math.sin(temp.getAngle());
					b.remove(i);
				}
				else
				{
					x -= 5/2 * Math.cos(temp.getAngle());
					y -= 5/2 * Math.sin(temp.getAngle());
					b.remove(i);
				}
			}
		}
	}
	public int getX() {
		return (int) x;
	}
	public int getY() {
		return (int) y;
	}
	public void setX(float x)
	{
		this.x = x;
	}
	public void setY(float y)
	{
		this.y = y;
	}
	public float getPreviousX() {
		return previousX;
	}
	public float getPreviousY() {
		return previousY;
	}
	public int getWidth() {
		return (int) width;
	}
	public int getHeight() {
		return (int) height;
	}
	public int getId()
	{
		return id;
	}
	public String getDirection()
	{
		return direction;
	}
}
