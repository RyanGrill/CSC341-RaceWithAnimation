import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Racer implements IRacer 
{
	IRacer racer;
	
	public Racer(RACER_TYPE racerType)
	{
		this.racer = RacerFactory.getRacer(racerType);
	}
	
	@Override
	public void move() 
	{
		racer.move();
	}
	
	@Override
	public int getDist() 
	{
		return racer.getDist();
	}
	
	public void draw(Graphics g)
	{
		racer.draw(g);
	}
}

class Tortoise implements IRacer
{
	int speed;
	int dist;
	int y;
	
	public Tortoise(int y)
	{
		this.y = y;
		Random r = new Random();
		speed = r.nextInt(5) + 10;
		dist = 0;
	}
	
	@Override
	public void move() 
	{
		dist += speed;
	}
	
	public int getDist()
	{
		return dist;
	}
	
	public int getY()
	{
		return y;
	}

	@Override
	public void draw(Graphics g) 
	{
		BufferedImage image = null;
        try
        {
        	image = ImageIO.read(new File("D:/CSC-341/Week8/Assignment/RaceWithAnimation/src/tortoise.png"));
        }
        catch (Exception e)
        {
        	System.out.println("Could not find image");
        }
        g.drawImage(image, dist-50, y-50, dist+50, y+50, 0, 0, 500, 500, null);
	}
	
}

class Hare implements IRacer
{
	int speed;
	int dist;
	int waitDist;
	int waitLength;
	int y;
	
	public Hare(int y)
	{
		this.y = y; 
		Random r = new Random();
		speed = r.nextInt(5) + 20;
		dist = 0;
		waitDist = speed * 3;
	}
	
	@Override
	public void move() 
	{
		if(waitLength == 0)
		{
			dist += speed;
			waitDist -= speed;
			
			if(waitDist == 0)
			{
				waitLength = 3;
				waitDist = speed * 2;
			}
		}
		else
		{
			waitLength--;
		}
	}
	
	public int getDist()
	{
		return dist;
	}
	
	public int getY()
	{
		return y;
	}

	@Override
	public void draw(Graphics g) 
	{
		BufferedImage image = null;
		if(waitLength != 0)
		{
		    try
		    {
		    	image = ImageIO.read(new File("D:/CSC-341/Week8/Assignment/RaceWithAnimation/src/hareRunning.jpg"));
		    }
		    catch (Exception e)
		    {
		    	System.out.println("Could not find image");
		    }
		    
		}
		else
		{
			try
		    {
		    	image = ImageIO.read(new File("D:/CSC-341/Week8/Assignment/RaceWithAnimation/src/hareResting.jpg"));
		    }
		    catch (Exception e)
		    {
		    	System.out.println("Could not find image");
		    }
		    
		}
		g.drawImage(image, dist-50, y-25, dist+50, y+25, 0, 0, 500, 500, null);
	}
}
