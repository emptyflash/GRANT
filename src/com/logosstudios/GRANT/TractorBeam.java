package com.logosstudios.GRANT;

import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class TractorBeam extends Applet implements Runnable, KeyListener, MouseListener,MouseMotionListener
{
	public static final int width = 640, height = 480, refreshRate = (int)(1.0/60 * 1000);
	private static final long serialVersionUID = 1L;
	private boolean running;
	private BufferedImage buffer;
	private Graphics2D bufferGraphics;
	private Player player;
	private Level level;
	private Image titleScreen, splashScreen;
	private boolean title, splash;
	public final static boolean debug = false;
	private long lastTime, currentTime;
	private static Sounds sound;
	private float alpha;
	
	public TractorBeam()
	{
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		setSize(width, height);
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bufferGraphics = buffer.createGraphics();
		player = new Player(width/2, height/2, 0, (float)0.24, bufferGraphics);
		level = new Level(player, bufferGraphics, this);
		if(!debug)
		{
			title = false;
			splash = true;
		}
		try
		{
			titleScreen = ImageIO.read(new File("res/images/titleScreen.png"));
			splashScreen = ImageIO.read(new File("res/images/splashScreen.png"));
		}
		catch (Exception e){e.printStackTrace();}
		running = true;
	}
	/*
	public void init()
	{
		new TractorBeam().start();
		sound = new Sound();
		sound.start();
	}
	*/
	public static void main(String args[])
	{
		JFrame frame = new JFrame("GRANT Device");
		TractorBeam game = new TractorBeam();
		sound = new Sounds();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.black);
		frame.setLayout(new BorderLayout());
		frame.setSize(width, height+33);
		frame.setResizable(false);
		frame.add(game, BorderLayout.CENTER);
		frame.setVisible(true);
		
		game.start();
		//sound.start();
	}
	
	public void start()
	{
		running = true;
		new Thread(this).start();
	}
	
	public void run()
	{
		while(running)
		{
			lastTime = currentTime;
			currentTime = System.currentTimeMillis();
			long delta = (currentTime - lastTime);
			repaint();
			logic(delta);
			try
			{
				Thread.sleep(refreshRate - delta);
			}
			catch(Exception e){}
		}
	}
	public void logic(long delta)
	{	
		player.logic(delta);
		level.logic();
		if((player.getX()<0||player.getX()+64>width)||(player.getY()<0||player.getY()+64>height))
		{
			player.setXVel(0);
			player.setYVel(0);
			player.setX(player.getPreviousX());
			player.setY(player.getPreviousY());
		}
	}
	public void restart()
	{
		level.restart();
	}
	public void nextLevel()
	{
		level.nextLevel();
	}
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_A)
		{
			player.left(true);
		}
		if(keyCode == KeyEvent.VK_D)
		{
			player.right(true);
		}
		if(keyCode == KeyEvent.VK_W)
		{
			player.up(true);
		}
		if(keyCode == KeyEvent.VK_S)
		{
			player.down(true);
		}
		if(keyCode == KeyEvent.VK_N)
		{
			nextLevel();
		}
		if(keyCode == KeyEvent.VK_ENTER)
		{
			if(title)
			{
				title = false;
				sound.startBackgroundMusic();
			}
			else
			{
				level.exitScreen();
			}
		}
		if(keyCode == KeyEvent.VK_R)
		{
			restart();
		}
		if(keyCode == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}
	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_A)
		{
			player.left(false);
		}
		if(keyCode == KeyEvent.VK_D)
		{
			player.right(false);
		}
		if(keyCode == KeyEvent.VK_W)
		{
			player.up(false);
		}
		if(keyCode == KeyEvent.VK_S)
		{
			player.down(false);
		}
	}
	public void keyTyped(KeyEvent e){} 
	public void mouseMoved(MouseEvent e) 
	{
		player.updateMouse(e.getPoint());
	}
	public void mouseDragged(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) 
	{
		level.stopBeam();
		player.start();
		sound.stopPullStart();
		sound.stopPushStart();
	}
	public void mousePressed(MouseEvent e) 
	{
		if(!title&&!splash)
		{
			player.stop();
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				TractorBeam.playPushStart();
				level.startBeam(true);
			}
			else if (e.getButton() == MouseEvent.BUTTON3)
			{
				TractorBeam.playPullStart();
				level.startBeam(false);
			}
		}
	}
	
	public void paint(Graphics g)
	{
		bufferGraphics.clearRect(0, 0, width, height);
		if(splash)
		{
			bufferGraphics.setBackground(Color.white);
			bufferGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			bufferGraphics.drawImage(splashScreen, 0, 0, this);
			alpha += 0.005;
		}
		else if(title)
		{
			bufferGraphics.drawImage(titleScreen, 0, 0, this);
		}
		else
		{
			level.draw();
		}
		g.drawImage(buffer, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
		if(splash && alpha >= 1.0)
		{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			splash = false;
			title = true;
		}
	}
	public void update(Graphics g)
    {
         paint(g);
    } 
	public static void playDoorOpen()
	{
		 sound.playDoorOpen();
	}
	public static void playDoorClose()
	{
		 sound.playDoorClose();
	}
	public static void playPushStart()
	{
		 sound.playPushStart();
	}
	public static void playPullStart()
	{
		sound.playPushStart();
	}
}
