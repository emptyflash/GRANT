package com.logosstudios.GRANT;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Wall extends Component implements Entity{
	private static final long serialVersionUID = 1L;
	private float x, y, width, height;
	private Graphics2D graphics;
	private Player player;
	private ArrayList<ArrayList<Entity>> entities;
	private Image pattern, border;
	private boolean visible;
	
	public Wall(float x, float y, float w, float h, Graphics2D g, Player p, ArrayList<ArrayList<Entity>> a, boolean solid, boolean rotated)
	{
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		graphics = g;
		player = p;
		entities = a;
		visible = true;
		if(solid)
		{
			try
			{
				pattern = ImageIO.read(getClass().getResourceAsStream("/res/images/wallPatternSolid.png"));
				border = ImageIO.read(getClass().getResourceAsStream("/res/images/wallBorder.png"));
			}
			catch (Exception e){e.printStackTrace();}
			
		}
		else
		{
			try
			{
				pattern = ImageIO.read(getClass().getResourceAsStream("/res/images/wallPattern.png"));
				border = ImageIO.read(getClass().getResourceAsStream("/res/images/wallBorder.png"));
			}
			catch (Exception e){e.printStackTrace();}
		}
	}
	public void draw() {
		if(visible)
		{
			//Fill
			for(int r = border.getWidth(null); r < width-border.getWidth(null); r+=pattern.getWidth(null))
			{
				for(int c = border.getHeight(null); c < height-border.getHeight(null); c+=pattern.getHeight(null))
				{
					graphics.drawImage(pattern, (int)(x+r), (int)(y+c), null);
				}
			}
			//Draw border
			//Draw top
			for(int i = 0; i < width; i+=border.getWidth(null))
			{
				graphics.drawImage(border, (int)x+i, (int)y, null);
			}
			//Draw bottom
			for(int i = 0; i < width; i+=border.getWidth(null))
			{
				graphics.drawImage(border, (int)x+i, (int) (y + (height-border.getHeight(null))), null);
			}
			//Draw left
			for(int i = 0; i < height; i+=border.getHeight(null))
			{
				graphics.drawImage(border, (int)x, (int)y+i, null);
			}
			//Draw right
			for(int i = 0; i < height; i+=border.getHeight(null))
			{
				graphics.drawImage(border, (int)(x + (width-border.getWidth(null))), (int)y+i, null);
			}
		}
	}

	public void logic(long delta) {
		if(visible)
		{
			if((player.getX()+64>x&&player.getX()<x+width) && (player.getY()+64>y && player.getY() < y+height))
			{
				player.setXVel(0);
				player.setYVel(0);
				player.setX(player.getPreviousX());
				player.setY(player.getPreviousY());
			}
			for(int i = 0; i < entities.size(); i++)
			{
				ArrayList<Entity> tempList = entities.get(i);
				for(int j = 0; j < tempList.size(); j++)
				{
					Entity temp = tempList.get(j);
					if((temp.getX()+temp.getWidth()>x&&temp.getX()<x+width) && (temp.getY()+temp.getHeight()>y && temp.getY() < y+height))
					{
						temp.setX(temp.getPreviousX());
						temp.setY(temp.getPreviousY());
					}
				}
			}
		}
	}
	public void off()
	{
		visible = false;
	}
	public void on()
	{
		visible = true;
	}
	public boolean getVisible()
	{
		return visible;
	}
	public int getX()
	{
		return (int)x;
	}
	public int getY()
	{
		return (int)y;
	}
	public int getWidth()
	{
		return (int)width;
	}
	public int getHeight()
	{
		return (int)height;
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
	public void setY(float y) {
		// TODO Auto-generated method stub
		
	}

}
