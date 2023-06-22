package com.logosstudios.GRANT;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Beam extends Component implements Entity {
	private static final long serialVersionUID = 1L;
	private float x, y, angle, speed, width, height;
	private boolean push;
	private Graphics2D graphics;
	private Image up, upLeft, left, downLeft, down, downRight, right, upRight, current;
	private int lastId;
	
	public Beam(float x, float y, float angle, boolean push, Graphics2D g)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.push = push;
		speed = 6;
		graphics = g;
		lastId = -1;
		if(push)
		{
			try
			{
				up = ImageIO.read(new File("res/images/pushBeamUp.png"));
				upLeft = ImageIO.read(new File("res/images/pushBeamUpLeft.png"));
				left = ImageIO.read(new File("res/images/pushBeamLeft.png"));
				downLeft = ImageIO.read(new File("res/images/pushBeamDownLeft.png"));
				down = ImageIO.read(new File("res/images/pushBeamDown.png"));
				downRight = ImageIO.read(new File("res/images/pushBeamDownRight.png"));
				right = ImageIO.read(new File("res/images/pushBeamRight.png"));
				upRight = ImageIO.read(new File("res/images/pushBeamUpRight.png"));
			}catch (Exception e){e.printStackTrace();}
		}
		else
		{
			try
			{
				up = ImageIO.read(new File("res/images/pullBeamUp.png"));
				upLeft = ImageIO.read(new File("res/images/pullBeamUpLeft.png"));
				left = ImageIO.read(new File("res/images/pullBeamLeft.png"));
				downLeft = ImageIO.read(new File("res/images/pullBeamDownLeft.png"));
				down = ImageIO.read(new File("res/images/pullBeamDown.png"));
				downRight = ImageIO.read(new File("res/images/pullBeamDownRight.png"));
				right = ImageIO.read(new File("res/images/pullBeamRight.png"));
				upRight = ImageIO.read(new File("res/images/pullBeamUpRight.png"));
			}catch (Exception e){e.printStackTrace();}
		}
		
		float snapAngle =(float)(Math.PI/4);
		float offset = (float)(Math.PI/8);
		if(angle <= 3*-snapAngle + offset)
		{
			current = upLeft;
		}
		else if(angle <= 2*-snapAngle + offset)
		{
			current = up;
		}
		else if(angle <= -snapAngle + offset)
		{
			current = upRight;
		}
		else if(angle <= 0 + offset)
		{
			current = right;
		}
		else if(angle <= snapAngle + offset)
		{
			current = downRight;
		}
		else if(angle <= 2*snapAngle + offset)
		{
			current = down;
		}
		else if(angle <= 3*snapAngle + offset)
		{
			current = downLeft;
		}
		else if(angle <= 4*snapAngle + offset)
		{
			current = left;
		}
		width = current.getWidth(null);
		height = current.getHeight(null);
	}
	public void draw()
	{
		graphics.drawImage(current, (int)x-5, (int)y-5, this);
	}
	public void logic()
	{
		x += (speed * Math.cos(angle));
		y += (speed * Math.sin(angle));
	}
	public float getAngle(){
		return angle;
	}
	public boolean getPush()
	{
		return push;
	}
	public int getX()
	{
		if(current == up)
		{
			return (int) (x + width/2);
		}
		if(current == upRight)
		{
			return (int) (x + width);
		}
		if(current == right)
		{
			return (int) (x + width);
		}
		if(current == downRight)
		{
			return (int) (x + width);
		}
		if(current == down)
		{
			return (int) (x + width/2);
		}
		if(current == downLeft)
		{
			return (int) x;
		}
		if(current == left)
		{
			return (int) x;
		}
		if(current == upLeft)
		{
			return (int ) x;
		}
		return (int) x;
	}
	public int getY()
	{
		if(current == up)
		{
			return (int) y;
		}
		if(current == upRight)
		{
			return (int) y;
		}
		if(current == right)
		{
			return (int) (y + height/2);
		}
		if(current == downRight)
		{
			return (int) (y + height);
		}
		if(current == down)
		{
			return (int) (y + height);
		}
		if(current == downLeft)
		{
			return (int) (y + height);
		}
		if(current == left)
		{
			return (int) (y + height/2);
		}
		if(current == upLeft)
		{
			return (int ) y;
		}
		return (int) y;
	}
	public int getLastId()
	{
		return lastId;
	}
	public void setId(int id)
	{
		lastId = id;
	}
	public void updateAngle(float angle)
	{
		this.angle = angle;
		float snapAngle = (float)(Math.PI/4);
		float offset = (float)(Math.PI/8);
		if(angle <= 3*-snapAngle + offset)
		{
			current = upLeft;
		}
		else if(angle <= 2*-snapAngle + offset)
		{
			current = up;
		}
		else if(angle <= -snapAngle + offset)
		{
			current = upRight;
		}
		else if(angle <= 0 + offset)
		{
			current = right;
		}
		else if(angle <= snapAngle + offset)
		{
			current = downRight;
		}
		else if(angle <= 2*snapAngle + offset)
		{
			current = down;
		}
		else if(angle <= 3*snapAngle + offset)
		{
			current = downLeft;
		}
		else if(angle <= 4*snapAngle + offset)
		{
			current = left;
		}
		width = current.getWidth(null);
		height = current.getHeight(null);
	}
	public int getRealX()
	{
		return (int) x;
	}
	public int getRealY()
	{
		return (int) y;
	}
	public String getDirection()
	{
		if(current == up)
		{
			return "Up";
		}
		if(current == upRight)
		{
			return "UpRight";
		}
		if(current == right)
		{
			return "Right";
		}
		if(current == downRight)
		{
			return "DownRight";
		}
		if(current == down)
		{
			return "Down";
		}
		if(current == downLeft)
		{
			return "DownLeft";
		}
		if(current == left)
		{
			return "Left";
		}
		if(current == upLeft)
		{
			return "UpLeft";
		}
		return "none";
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
	public void setX(float x) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setY(float x) {
		// TODO Auto-generated method stub
		
	}
	public int getWidth()
	{
		return (int) 0;
	}
	public int getHeight()
	{
		return (int) 0;
	}

}
