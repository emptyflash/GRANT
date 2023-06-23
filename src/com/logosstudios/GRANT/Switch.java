package com.logosstudios.GRANT;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Switch extends Component implements Entity {
	private static final long serialVersionUID = 1L;
	private float x, y, width, height;
	private boolean onBool;
	private Graphics2D graphics;
	private Image current, on, off;
	
	public Switch(float x, float y, Graphics2D g)
	{
		this.x = x;
		this.y = y;
		graphics = g;
		onBool = false;
		try
		{
			on = ImageIO.read(getClass().getResourceAsStream("/res/images/switchEngaged.png"));
			off = ImageIO.read(getClass().getResourceAsStream("/res/images/switchDisengaged.png"));
			current = off;
		}
		catch (Exception e){e.printStackTrace();}
		width = current.getWidth(null);
		height = current.getHeight(null);
	}
	@Override
	public void draw() {
		graphics.drawImage(current, (int)x, (int)y, this);
	}

	@Override
	public void logic(long delta) {
		
	}
	public void on()
	{
		onBool = true;
		current = on;
	}
	public void off()
	{
		onBool = false;
		current = off;
	}
	public void setOn(boolean on)
	{
		onBool = on;
		current = on?this.on:this.off;
	}
	public boolean getOn()
	{
		return onBool;
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

}
