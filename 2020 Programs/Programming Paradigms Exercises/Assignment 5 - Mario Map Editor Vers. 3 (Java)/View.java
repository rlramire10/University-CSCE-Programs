//Name: Rudy Ramirez
//Date: 10/23/20
//Assignment: Homework 5

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

class View extends JPanel
{
	Model model;

	View(Controller c)
	{
		c.setView(this);
		
		//Initiate model variable from controller class
		model = c.model;
	}
	
	//Setters
	void setModel(Model m)
	{
		model = m;
	}
	
	//Display Images
	public void paintComponent(Graphics g)
	{
		//Background
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//Draw Sprites
		for(int i = 0; i < model.sprites.size(); i++)
		{
			model.sprites.get(i).draw(g);
		}
		//Draw Ground
		g.setColor(Color.gray);
		g.drawLine(0, 400, 2000, 400);
	}
}