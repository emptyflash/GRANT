package com.logosstudios.GRANT;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Final extends Level {
	private Graphics2D graphics;
	private Player player;
	private TractorBeam game;
	private int num;
	
	public Final(Player p, Graphics2D g, TractorBeam b)
	{
        super(p, g, b);
		player = p;
		graphics = g;
		game = b;
		player.stop();
		num = 1;
	}

	@Override
	public void draw() {
		if(num!=1)
		{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        if (num == 10) {
            System.exit(0);
        }
		Image image = null;
		try
		{
            if(num == 9)
            {
                image = ImageIO.read(getClass().getResourceAsStream("/res/images/credits.png"));
            } else {
                image = ImageIO.read(getClass().getResourceAsStream("/res/images/end" + num + ".png"));
            }
		}
		catch (Exception e){e.printStackTrace();}
		graphics.drawImage(image, 0, 0, 640, 480, game);
		num++;
	}

	@Override
	public void logic(long delta) {
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
