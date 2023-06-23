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
	private static Image pushUp, pushUpLeft, pushLeft, pushDownLeft, pushDown, pushDownRight, pushRight, pushUpRight;
	private static Image pullUp, pullUpLeft, pullLeft, pullDownLeft, pullDown, pullDownRight, pullRight, pullUpRight;
    private Image current;
    private String direction;
	private int lastId;

    static {
        try
        {
            pushUp = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pushBeamUp.png"));
            pushUpLeft = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pushBeamUpLeft.png"));
            pushLeft = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pushBeamLeft.png"));
            pushDownLeft = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pushBeamDownLeft.png"));
            pushDown = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pushBeamDown.png"));
            pushDownRight = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pushBeamDownRight.png"));
            pushRight = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pushBeamRight.png"));
            pushUpRight = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pushBeamUpRight.png"));


            pullUp = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pullBeamUp.png"));
            pullUpLeft = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pullBeamUpLeft.png"));
            pullLeft = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pullBeamLeft.png"));
            pullDownLeft = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pullBeamDownLeft.png"));
            pullDown = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pullBeamDown.png"));
            pullDownRight = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pullBeamDownRight.png"));
            pullRight = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pullBeamRight.png"));
            pullUpRight = ImageIO.read(Beam.class.getResourceAsStream("/res/images/pullBeamUpRight.png"));
        } catch (Exception e){e.printStackTrace();}
    }
	
	public Beam(float x, float y, float angle, boolean push, Graphics2D g)
	{
		this.x = x;
		this.y = y;
		this.push = push;
		speed = 3;
		graphics = g;
		lastId = -1;
		
        this.updateAngle(angle);
	}
	public void draw()
	{
		graphics.drawImage(current, (int)x-5, (int)y-5, this);
	}
	public void logic(long delta)
	{
		x += (speed * Math.cos(angle)) * (delta > 30 ? 3 : 1);
		y += (speed * Math.sin(angle)) * (delta > 30 ? 3 : 1);
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
		if(direction == "Up")
		{
			return (int) (x + width/2);
		}
		if(direction == "UpRight")
		{
			return (int) (x + width);
		}
		if(direction == "Right")
		{
			return (int) (x + width);
		}
		if(direction == "DownRight")
		{
			return (int) (x + width);
		}
		if(direction == "Down")
		{
			return (int) (x + width/2);
		}
		if(direction == "DownLeft")
		{
			return (int) x;
		}
		if(direction == "Left")
		{
			return (int) x;
		}
		if(direction == "UpLeft")
		{
			return (int ) x;
		}
		return (int) x;
	}
	public int getY()
	{
		if(direction == "Up")
		{
			return (int) y;
		}
		if(direction == "UpRight")
		{
			return (int) y;
		}
		if(direction == "Right")
		{
			return (int) (y + height/2);
		}
		if(direction == "DownRight")
		{
			return (int) (y + height);
		}
		if(direction == "Down")
		{
			return (int) (y + height);
		}
		if(direction == "DownLeft")
		{
			return (int) (y + height);
		}
		if(direction == "Left")
		{
			return (int) (y + height/2);
		}
		if(direction == "UpLeft")
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

    public Image getImage() {
		if(direction == "UpLeft")
		{
			return push ? pushUpLeft : pullUpLeft;
		}
		else if(direction == "Up")
		{
			return push ? pushUp : pullUp;
		}
		else if(direction == "UpRight")
		{
			return push ? pushUpRight : pullUpRight;
		}
		else if(direction == "Right")
		{
			return push ? pushRight : pullRight;
		}
		else if(direction == "DownRight")
		{
			return push ? pushDownRight : pullDownRight;
		}
		else if(direction == "Down")
		{
			return push ? pushDown : pullDown;
		}
		else if(direction == "DownLeft")
		{
			return push ? pushDownLeft : pullDownLeft;
		}
		else if(direction == "Left")
		{
			return push ? pushLeft : pullLeft;
		}
        return null;
    }

	public void updateAngle(float angle)
	{
		this.angle = angle;
		float snapAngle = (float)(Math.PI/4);
		float offset = (float)(Math.PI/8);
		if(angle <= 3*-snapAngle + offset)
		{
			direction = "UpLeft";
		}
		else if(angle <= 2*-snapAngle + offset)
		{
			direction = "Up";
		}
		else if(angle <= -snapAngle + offset)
		{
			direction = "UpRight";
		}
		else if(angle <= 0 + offset)
		{
			direction = "Right";
		}
		else if(angle <= snapAngle + offset)
		{
			direction = "DownRight";
		}
		else if(angle <= 2*snapAngle + offset)
		{
			direction = "Down";
		}
		else if(angle <= 3*snapAngle + offset)
		{
			direction = "DownLeft";
		}
		else if(angle <= 4*snapAngle + offset)
		{
			direction = "Left";
		}
        current = this.getImage();
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
        return direction;
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
