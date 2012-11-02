package com.logosstudios.GRANT;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.File;

import javax.imageio.ImageIO;

public class Player extends Component implements Entity{
	private static final long serialVersionUID = 1L;
	private float x, y, xVel, yVel, speed, angle, previousX, previousY;
	private static final float FRICTION = 0.9f, MAX_VEL = 10;
	private double mouseX, mouseY;
	private Graphics2D graphics;
	private boolean move;
	private Image up, upLeft, left, downLeft, down, downRight, right, upRight, current;
	private boolean rightBool;
	private boolean leftBool;
	private boolean upBool;
	private boolean downBool;
	
	public Player(float x, float y, float angle, float speed, Graphics2D g)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = speed;
		graphics = g;
		try
		{
			up = ImageIO.read(new File("res/images/playerUp.png"));
			upLeft = ImageIO.read(new File("res/images/playerUpLeft.png"));
			left = ImageIO.read(new File("res/images/playerLeft.png"));
			downLeft = ImageIO.read(new File("res/images/playerDownLeft.png"));
			down = ImageIO.read(new File("res/images/playerDown.png"));
			downRight = ImageIO.read(new File("res/images/playerDownRight.png"));
			right = ImageIO.read(new File("res/images/playerRight.png"));
			upRight = ImageIO.read(new File("res/images/playerUpRight.png"));
		}
		catch (Exception e){e.printStackTrace();}
	}
	public void logic()
	{
		logic(0);
	}
	public void logic(long delta)
	{
		double dX = (mouseX-(x+32));
		double dY = (mouseY-(y+32));
		angle = (float) Math.atan2(dY, dX);
		float snapAngle = (float)(Math.PI/4);
		float offset = (float)(Math.PI/8);
		
		if(angle <= 3*-snapAngle + offset)
		{
			current = upLeft;
			angle = 3*-snapAngle;
			System.out.println("-3");
		}
		else if(angle <= 2*-snapAngle + offset)
		{
			current = up;
			angle = 2*-snapAngle;
			System.out.println("-2");
		}
		else if(angle <= -snapAngle + offset)
		{
			current = upRight;
			angle = -snapAngle;
			System.out.println("-1");
		}
		else if(angle <= 0 + offset)
		{
			current = right;
			angle = 0;
			System.out.println("0");
		}
		else if(angle <= snapAngle + offset)
		{
			current = downRight;
			angle = snapAngle;
			System.out.println("1");
		}
		else if(angle <= 2*snapAngle + offset)
		{
			current = down;
			angle = 2*snapAngle;
			System.out.println("2");
		}
		else if(angle <= 3*snapAngle + offset)
		{
			current = downLeft;
			angle = 3*snapAngle;
			System.out.println("3");
		}
		else if(angle <= 4*snapAngle + offset)
		{
			current = left;
			angle = 4*snapAngle;
			System.out.println("4");
		}
		
		if(rightBool)
		{
			xVel += speed;
		}
		if(leftBool)
		{
			xVel -= speed;
		}
		if(upBool)
		{
			yVel -= speed;
		}
		if(downBool)
		{
			yVel += speed;
		}
		
		if(xVel>=MAX_VEL)
		{
			xVel = MAX_VEL;
		}
		if(xVel<=-MAX_VEL)
		{
			xVel = -MAX_VEL;
		}
		if(yVel>=MAX_VEL)
		{
			yVel = MAX_VEL;
		}
		if(yVel<=-MAX_VEL)
		{
			yVel = -MAX_VEL;
		}
		if(move)
		{
			xVel *= FRICTION;
			yVel *= FRICTION;
			previousX = x;
			previousY = y;
			x += xVel * (delta/14.0f);
			y += yVel * (delta/14.0f);
		}
	}
	public void updateMouse(Point p)
	{
		mouseX = p.getX();
		mouseY = p.getY();
	}
	public void draw()
	{
		graphics.drawImage(current, (int)x, (int)y, this);
	}
	public void up(boolean b)
	{
		upBool = b;
	}
	public void down(boolean b)
	{
		downBool = b;
	}
	public void left(boolean b)
	{
		leftBool = b;
	}
	public void right(boolean b)
	{
		rightBool = b;
	}
	public float getAngle()
	{
		return angle;
	}
	public int getX()
	{
		return (int) x;
	}
	public int getY()
	{
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
	public float getPreviousY() {
		return previousY;
	}
	public float getPreviousX() {
		return previousX;
	}
	public void start()
	{
		move = true;
	}
	public void stop()
	{
		move = false;
		xVel = 0;
		yVel = 0;
	}
	public void setYVel(float yVel) {
		this.yVel = yVel;
	}
	public void setXVel(float xVel) {
		this.xVel = xVel;
	}
	public double getMouseX()
	{
		return mouseX;
	}
	public double getMouseY()
	{
		return mouseY;
	}
}
