package com.logosstudios.GRANT;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Level {
	
	private Player player;
	private Graphics2D graphics;
	private TractorBeam game;
	private float startX, startY;
	private ArrayList<Beam> beams;
	private ArrayList<ArrayList<Entity>> entities;
	private ArrayList<Wall> walls, wallsSolid;
	private Wall disappear;
	private ArrayList<Entity> boxes;
	private ArrayList<Button> buttons;
	private ArrayList<Entity> mirrors;
	private ArrayList<Switch> switches;
	private Door door;
	private boolean beamFlow, push;
	private Image background, splash, instructions;
	private boolean open;
	private boolean splashBool;
	private boolean instructionBool;
	private ArrayList<BufferedImage> levels;
	private int level;
	private boolean changingLevel;
	private boolean debug = TractorBeam.debug;
	private int ticks;
	
	public Level(Player p, Graphics2D g, TractorBeam b)
	{
		player = p;
		graphics = g;
		game = b;
		beams = new ArrayList<Beam>();
		entities = new ArrayList<ArrayList<Entity>>();
		walls = new ArrayList<Wall>();
		wallsSolid = new ArrayList<Wall>();
		boxes = new ArrayList<Entity>();
		buttons = new ArrayList<Button>();
		mirrors = new ArrayList<Entity>();
		switches = new ArrayList<Switch>();
		splashBool = !debug;
		/*boxes.add(new Box(450+3, 200, 10, graphics));
		buttons.add(new Button(450, 100, boxes, graphics));
		mirrors.add(new Mirror(485, 300, "DownRight", graphics, beams));
		door = new Door(50, 460, 65, 20, graphics, false);
		*/
		level = 0;
		levels = new ArrayList<BufferedImage>();
		for(int i = 1; i < 8; i++)
		{
			try {
				levels.add(ImageIO.read(new File("res/images/level"+i+".png")));
				//levels.add(ImageIO.read(new File("level"+i+".png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		load();
		try
		{
			background = ImageIO.read(new File("res/images/background2.png"));
			splash = ImageIO.read(new File("res/images/black-wellMiniSplash.png"));
			instructions = ImageIO.read(new File("res/images/instructions.png"));
		}
		catch (Exception e){e.printStackTrace();}
	}
	public void draw() 
	{
		if(!changingLevel)
		{
			graphics.drawImage(background, 0, 0, game);
			for(int i = 0; i < walls.size(); i++)
			{
				walls.get(i).draw();
			}
			if(wallsSolid.size() > 0)
			{
				for(int i = 0; i < wallsSolid.size(); i++)
				{
					wallsSolid.get(i).draw();
				}
			}
			door.draw();
			for(int i = 0; i < buttons.size(); i++)
			{
				buttons.get(i).draw();
			}
			for(int i = 0; i < beams.size(); i++)
			{
				beams.get(i).draw();
			}
			for(int i = 0; i < boxes.size(); i++)
			{
				boxes.get(i).draw();
			}
			for(int i = 0; i < mirrors.size(); i++)
			{
				mirrors.get(i).draw();
			}
			for(int i = 0; i < switches.size(); i++)
			{
				switches.get(i).draw();
			}
			player.draw();
			if(splashBool)
			{
				graphics.drawImage(splash, 64, 42, game);
			}
			if(instructionBool)
			{
				graphics.drawImage(instructions, 64, 42, game);
			}
		}
	}
	public void exitScreen()
	{
		if(instructionBool)
		{
			instructionBool = false;
		}
		if(splashBool)
		{
			splashBool = false;
			instructionBool = true;
		}
	}
	public void logic() 
	{
		ticks++;
		if(beamFlow && ticks%2==0)
		{
			beams.add(new Beam(player.getX()+28, player.getY()+28, player.getAngle(), push, graphics));
		}
		else if(!beamFlow)
		{
			beams.clear();
		}
		for(int i = 0; i < walls.size(); i++)
		{
			walls.get(i).logic();
		}
		if(wallsSolid.size() > 0)
		{
			for(int i = 0; i < wallsSolid.size(); i++)
			{
				wallsSolid.get(i).logic();
			}
		}
		for(int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).logic();
		}
		for(int i = 0; i < boxes.size(); i++)
		{
			boxes.get(i).logic();
		}
		for(int i = 0; i < switches.size(); i++)
		{
			switches.get(i).logic();
		}
		for(int i = 0; i < beams.size(); i++)
		{
			beams.get(i).logic();
		}
		for(int i = 0; i < buttons.size(); i++)
		{
			open = open && buttons.get(i).getActivated();
		}
		Beams:
		for(int i = 0; i < beams.size(); i++)
		{
			for(int j = 0; j < boxes.size(); j++)
			{
				if(hitTest(beams.get(i), boxes.get(j)))
				{
					((Box)boxes.get(j)).hit(beams.get(i));
					beams.remove(i);
					continue Beams;
				}
			}
			for(int j = 0; j < mirrors.size(); j++)
			{
				if(!(beams.get(i).getLastId()==((Mirror)mirrors.get(j)).getId()) && triangleHitTest(beams.get(i), (Mirror)mirrors.get(j)))
				{
					((Mirror)mirrors.get(j)).hit(beams, i);
					continue Beams;
				}
			}
			if(wallsSolid.size() > 0)
			{
				for(int j = 0; j < wallsSolid.size(); j++)
				{
					if(hitTest(beams.get(i), wallsSolid.get(j)))
					{
						beams.remove(i);
						continue Beams;
					}
				}
			}
			/*
			if(beams.get(i).getX() >= TractorBeam.width || beams.get(i).getX()+beams.get(i).getWidth() <= 0  ||  beams.get(i).getY() >= TractorBeam.height || beams.get(i).getY()+beams.get(i).getHeight() <= 0)
			{
				beams.remove(i);
				continue Beams;
			}
			*/
		}
		if(switches.size()>0)
		{
			for(int i = 0; i < switches.size(); i++)
			{
				boolean on = false;
				for(int j = 0; j < beams.size(); j++)
				{
					if(hitTest(beams.get(j), switches.get(i)))
					{
						on =  on || true;
					}
				}
				switches.get(i).setOn(on);
			}
		}
		if(buttons.size()>0)
		{
			open = buttons.get(0).getActivated();
		}
		for(int i = 0; i<buttons.size(); i++)
		{
			open = open && buttons.get(i).getActivated();
		}
		if(switches.size()>0)
		{
			open = switches.get(0).getOn();
			for(int i = 0; i < switches.size(); i++)
			{
				open = open && switches.get(i).getOn();
			}
		}
		if(open)
		{
			door.open();
			if(disappear != null)
				disappear.off();
			if((player.getX()+64>door.getX()&&player.getX()<door.getX()+door.getWidth()) && (player.getY()+64>door.getY() && player.getY() < door.getY()+door.getHeight()))
			{
				nextLevel();
			}
		}
		else
		{
				door.close();
				if(disappear != null)
					disappear.on();
		}
	}
	public void nextLevel()
	{
		changingLevel = true;
		level++;
		beams = new ArrayList<Beam>();
		entities = new ArrayList<ArrayList<Entity>>();
		walls = new ArrayList<Wall>();
		wallsSolid = new ArrayList<Wall>();
		boxes = new ArrayList<Entity>();
		buttons = new ArrayList<Button>();
		mirrors = new ArrayList<Entity>();
		/*boxes.add(new Box(450+3, 200, 10, graphics));
		buttons.add(new Button(450, 100, boxes, graphics));
		mirrors.add(new Mirror(485, 300, "DownRight", graphics, beams));
		door = new Door(50, 460, 65, 20, graphics, false);
		*/
		load();
		changingLevel = false;
	}
	public boolean hitTest(Entity a, Entity b)
	{
		return ((a.getX()+a.getWidth()>b.getX()&&a.getX()<b.getX()+b.getWidth()) && (a.getY()+a.getHeight()>b.getY() && a.getY() < b.getY()+b.getHeight()));
	}
	public boolean triangleHitTest(Beam b, Mirror m)
	{
		String dir = m.getDirection();
		if(dir.equals("UpLeft"))
		{
			if((b.getY()>m.getY() && b.getY()<m.getY()+m.getHeight()) && (b.getX()>m.getX() && b.getX()<m.getX()+m.getWidth()))
			{
				if(b.getDirection().equals("Down") || b.getDirection().equals("DownRight") || b.getDirection().equals("Right"))
					return true;
				else if(b.getX()-m.getX() <= m.getHeight()-(b.getY()-m.getY())+3)
					return true;
			}
		}
		else if(dir.equals("UpRight"))
		{
			if((b.getY()>m.getY() && b.getY()<m.getY()+m.getHeight()) && (b.getX()>m.getX() && b.getX()<m.getX()+m.getWidth()))
			{
				if(b.getDirection().equals("Down") || b.getDirection().equals("DownLeft") || b.getDirection().equals("Left"))
					return true;
				else if(b.getX()-m.getX() >= b.getY()-m.getY())
					return true;
			}
		}
		else if(dir.equals("DownRight"))
		{
			if((b.getY()>m.getY() && b.getY()<m.getY()+m.getHeight()) && (b.getX()>m.getX() && b.getX()<m.getX()+m.getWidth()))
			{
				if(b.getDirection().equals("Up") || b.getDirection().equals("UpLeft") || b.getDirection().equals("Left"))
					return true;
				if(b.getX()-m.getX()>=m.getHeight()-(b.getY()-m.getY()))
					return true;
				
			}
		}
		else if(dir.equals("DownLeft"))
		{
			if((b.getY()>m.getY() && b.getY()<m.getY()+m.getHeight()) && (b.getX()>m.getX() && b.getX()<m.getX()+m.getWidth()))
			{
				if(b.getDirection().equals("Up") || b.getDirection().equals("UpRight") || b.getDirection().equals("Right"))
					return true;
				else if(b.getY()-m.getY() >= b.getX()-m.getX())
					return true;
			}
		}
		return false;
	}
	public float getStartX()
	{
		return startX;
	}
	public float getStartY() 
	{
		return startY;
	}
	public void addBeam(Beam b)
	{
		beams.add(b);
	}
	public void startBeam(boolean push)
	{
		beamFlow = true;
		this.push = push;
	}
	public void stopBeam()
	{
		beamFlow = false;
	}
	public void restart()
	{
		beams = new ArrayList<Beam>();
		entities = new ArrayList<ArrayList<Entity>>();
		walls = new ArrayList<Wall>();
		wallsSolid = new ArrayList<Wall>();
		boxes = new ArrayList<Entity>();
		buttons = new ArrayList<Button>();
		mirrors = new ArrayList<Entity>();
		/*boxes.add(new Box(450+3, 200, 10, graphics));
		buttons.add(new Button(450, 100, boxes, graphics));
		mirrors.add(new Mirror(485, 300, "DownRight", graphics, beams));
		door = new Door(50, 460, 65, 20, graphics, false);
		*/
		load();
		
	}
	@SuppressWarnings("unused")
	public void load()
	{
		for(int y = 0; y < levels.get(level).getHeight(null); y++)
		{
			for(int x = 0; x < levels.get(level).getWidth(null); x++)
			{
				Color color = new Color(levels.get(level).getRGB(x,y));
				int red = color.getRed();
				int blue = color.getBlue();
				int green = color.getGreen();
				
				if(red == 178 && green == 0 && blue == 255)
				{
					player.setX(x*10);
					player.setY(y*10);
					player.setXVel(0);
					player.setYVel(0);
					continue;
				}
				if(red == 0 && green == 0 && blue == 0)
				{
					door = new Door(x*10, y*10, 65, 20, graphics, false);
				}
				if(red == 10 && green == 0 && blue == 0)
				{
					door = new Door(x*10, y*10, 65, 20, graphics, true);
				}
				if(red == 0 && green == 255 && blue == 0)
				{
					buttons.add(new Button(x*10, y*10, boxes, graphics));
				}
				if(red == 255 && green == 0 && blue == 0)
				{
					boxes.add(new Box(x*10+5, y*10+5, 10, graphics));
				}
				if(red == 0 && green == 0 && blue == 255)
				{
					mirrors.add(new Mirror(x*10, y*10, "DownRight", graphics, beams, mirrors.size()));
				}
				if(red == 10 && green == 0 && blue == 255)
				{
					mirrors.add(new Mirror(x*10, y*10, "UpRight", graphics, beams, mirrors.size()));
				}
				if(red == 20 && green == 0 && blue == 255)
				{
					mirrors.add(new Mirror(x*10, y*10, "UpLeft", graphics, beams, mirrors.size()));
				}
				if(red == 30 && green == 0 && blue == 255)
				{
					mirrors.add(new Mirror(x*10, y*10, "DownLeft", graphics, beams, mirrors.size()));
				}
				if(red == 255 && green == 127 && blue == 39)
				{
					switches.add(new Switch(x*10, y*10, graphics));
				}
			}
		}
		entities.add(boxes);
		entities.add(mirrors);
		y:
		for(int y = 0; y < levels.get(level).getHeight(null); y++)
		{
			for(int x = 0; x < levels.get(level).getWidth(null); x++)
			{
				Color color = new Color(levels.get(level).getRGB(x,y));
				int red = color.getRed();
				int blue = color.getBlue();
				int green = color.getGreen();
				
				if(green == 128 && blue == 128)
				{
					Color end = new Color(64, 64, 64);
					Color temp = color;
					
					int tempX = x;
					int tempY = y;
					int h = 1;
					int w = 1;
					
					while(!temp.equals(end))
					{
						w++;
						if(tempX<levels.get(level).getWidth(null))
						{
							tempX++;
						}
						else
						{
							break;
						}
						temp = new Color(levels.get(level).getRGB(tempX, y));
					}
					temp = color;
					while(!temp.equals(end))
					{
						h++;
						if(tempY<levels.get(level).getHeight(null))
						{
							tempY++;
						}
						else
						{
							break;
						}
						temp = new Color(levels.get(level).getRGB(x, tempY));
					}
					if(red == 128)
					{
						if(h>w)
						{
							walls.add(new Wall(x*10, y*10, w*10, h*10, graphics, player, entities, false, false));
						}
						else
						{
							walls.add(new Wall(x*10, y*10, w*10, h*10, graphics, player, entities, false, true));
						}
					}
					else if(red == 138)
					{
						if(h>w)
						{
							disappear = new Wall(x*10, y*10, w*10, h*10, graphics, player, entities, false, false);
							walls.add(disappear);
						}
						else
						{
							disappear = new Wall(x*10, y*10, w*10, h*10, graphics, player, entities, false, true);
							walls.add(disappear);
						}
					}
				}
				else if(green == 32 && blue == 32)
				{
					Color end = new Color(64, 64, 64);
					Color temp = color;
					
					int tempX = x;
					int tempY = y;
					int h = 1;
					int w = 1;
					
					while(!temp.equals(end))
					{
						w++;
						if(tempX<levels.get(level).getWidth(null))
						{
							tempX++;
						}
						else
						{
							break;
						}
						temp = new Color(levels.get(level).getRGB(tempX, y));
					}
					temp = color;
					while(!temp.equals(end))
					{
						h++;
						if(tempY<levels.get(level).getHeight(null))
						{
							tempY++;
						}
						else
						{
							break;
						}
						temp = new Color(levels.get(level).getRGB(x, tempY));
					}
					if(red == 32)
					{
						if(h>w)
						{
							wallsSolid.add(new Wall(x*10, y*10, w*10, h*10, graphics, player, entities, true, false));
						}
						else
						{
							wallsSolid.add(new Wall(x*10, y*10, w*10, h*10, graphics, player, entities, true, true));
						}
					}
					else if(red == 42)
					{
						if(h>w)
						{
							disappear = new Wall(x*10, y*10, w*10, h*10, graphics, player, entities, true, false);
							wallsSolid.add(disappear);
						}
						else
						{
							disappear = new Wall(x*10, y*10, w*10, h*10, graphics, player, entities, true, true);
							wallsSolid.add(disappear);
						}
					}
				}
			}
		}
		wallsSolid.add(new Wall(0, -20, TractorBeam.width, 20, graphics, player, entities, true, false));
		wallsSolid.add(new Wall(-20, 0, 20, TractorBeam.height, graphics, player, entities, true, false));
		wallsSolid.add(new Wall(TractorBeam.width, 0, 20, TractorBeam.height, graphics, player, entities, true, false));
		wallsSolid.add(new Wall(0, TractorBeam.height, TractorBeam.width, 20, graphics, player, entities, true, false));
	}
}
