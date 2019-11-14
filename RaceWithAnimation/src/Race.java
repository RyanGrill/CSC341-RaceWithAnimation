import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Race 
{
	public static void main(String[] args)
	{
		RaceController controller = new RaceController();
		controller.runRace();
	}
}

enum RACER_TYPE{tortoise, hare} 

class RacerFactory
{
	static int loc = 0;
	
	static IRacer getRacer(RACER_TYPE racerType)
	{
		switch(racerType)
		{
		case tortoise:
			loc += 50;
			return new Tortoise(loc);	
		case hare:
			loc += 50;
			return new Hare(loc);
		default:
			return null;
		}
	}
}

class RaceController
{
	private IRacer[] racers;
	private int raceLength;
	RaceSimFrame fr;
	RaceTask task;
	Thread animator;
	
	public RaceController()
	{
		raceLength = 500;
		init();
	}
	
	public void init()
	{
		racers = new IRacer[6];
		task = new RaceTask();
		animator = new Thread(task);
		fr = new RaceSimFrame(racers, animator);
		
		for(int i = 0; i < racers.length / 2 ; i++)
		{
			racers[i] = new Racer(RACER_TYPE.tortoise);
		}
		
		for(int i = racers.length / 2; i < racers.length; i++)
		{
			racers[i] = new Racer(RACER_TYPE.hare);
		}
	}
	
	public void runRace()
	{
		animator.start();
	}
	
	class RaceTask implements Runnable
	{
		@Override
		public void run() 
		{
			while(true)
			{
				for(int i = 0; i < racers.length; i++)
				{
					IRacer racer = racers[i];
					racer.move();
					if(racer.getDist() == raceLength)
					{
						System.exit(0);
					}
				}
				
				try{
					Thread.sleep(60);
				}
				catch(InterruptedException e)
				{
					System.out.println(e.getMessage());
				}
				fr.repaint();
			}
		}	
	}
}

class RaceSimFrame extends JFrame
{
	IRacer racers[];
	RacePanel pnl;
	Thread animator;
	
	public RaceSimFrame(IRacer racers[], Thread anim)
	{
		animator = anim;
		this.racers = racers;
		this.setSize(600, 600);
		this.setLocation(300, 100);
		pnl = new RacePanel();
		this.add(pnl);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				animator.stop();
				e.getWindow().dispose();
				System.exit(0);
			}
		});
		this.setVisible(true);
	}
	
	public void repaint()
	{
		super.repaint();
		pnl.repaint();
	}
	
	class RacePanel extends JPanel
	{
		public RacePanel()
		{
			this.setBackground(Color.white);
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			for(int i = 0; i < racers.length; i++)
			{
				racers[i].draw(g);
			}
		}
	}
}