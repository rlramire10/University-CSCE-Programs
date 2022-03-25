//Name: Rudy Ramirez
//Date: 10/7/20
//Assignment: Homework 4

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
		
		this.setTitle("Side-Scroller");
		this.setSize(500, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);	
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
