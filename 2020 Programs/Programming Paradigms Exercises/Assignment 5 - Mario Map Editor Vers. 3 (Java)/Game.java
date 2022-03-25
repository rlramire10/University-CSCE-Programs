//Name: Rudy Ramirez
//Date: 10/23/20
//Assignment: Homework 5

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	Model model;
	Tube tube;
	Controller controller;
	View view;

	public Game()
	{
		model = new Model();
		controller = new Controller(model);
		view = new View(controller);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
		
		this.setTitle("Goomba Burn Test");
		this.setSize(500, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);	
		
		try {
			Json j = Json.load("map.json");
			model.unmarshal(j);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("There was an error Loading your map");
			System.exit(0);
		}
	}

	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 50 miliseconds
			try
			{
				Thread.sleep(50);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
}
