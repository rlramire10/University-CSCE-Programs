//Name: Rudy Ramirez
//Date: 10/7/20
//Assignment: Homework 4

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
		
		//Draw Tube(s)
		for(int i = 0; i < model.tubes.size(); i++)
		{
			Tube t = model.tubes.get(i);
			t.drawTube(g, model.mario.pos_x, model.mario.marioOffset);
		}
		//Draw Ground
		g.setColor(Color.gray);
		g.drawLine(0, 400, 2000, 400);
		//Draw Mario
		model.mario.drawMario(g);
	}
}