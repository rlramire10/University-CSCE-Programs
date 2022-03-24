//Name: Rudy Ramirez
//Date: 9/21/20
//Assignment: Homework 3

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	BufferedImage image;
	Model model;
	
	int scrollPos;

	View(Controller c)
	{
		c.setView(this);
		
		//Initiate model variable from controller class
		model = c.model;

		//Load Image of the Tube
		try
		{
			this.image =
				ImageIO.read(new File("tube.png"));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
	
	//Setters
	void setImage(BufferedImage i)
	{
		image = i;
	}
	void setModel(Model m)
	{
		model = m;
	}
	
	//Display the Tube Image
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for(int i = 0; i < model.tubes.size(); i++)
		{
			Tube t = model.tubes.get(i);
			g.drawImage(image, t.x - scrollPos, t.y, null);
		}
	}

}