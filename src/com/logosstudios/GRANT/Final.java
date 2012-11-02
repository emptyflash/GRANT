package com.logosstudios.GRANT;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

public class Final implements Level {
	private Graphics2D graphics;
	private Player player;
	private TractorBeam game;
	private int num;
	
	public Final(Player p, Graphics2D g, TractorBeam b)
	{
		player = p;
		graphics = g;
		game = b;
		player.stop();
		num = 1;
	}

	@Override
	public void draw() {
		if(num == 10)
		{
			game.nextLevel();
		}
		if(num!=1)
		{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		Image image = null;
		try
		{
			image = ImageIO.read(Player.class.getResource("end" + num + ".png"));
		}
		catch (Exception e){e.printStackTrace();}
		graphics.drawImage(image, 0, 0, 640, 480, game);
		num++;
	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub

	}

	@Override
	public float getStartX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getStartY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addBeam(Beam b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startBeam(boolean push) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopBeam() {
		// TODO Auto-generated method stub

	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitScreen() {
		// TODO Auto-generated method stub

	}

}
