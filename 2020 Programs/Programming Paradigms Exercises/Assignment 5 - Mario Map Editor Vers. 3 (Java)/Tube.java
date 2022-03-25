//Name: Rudy Ramirez
//Date: 10/23/20
//Assignment: Homework 5

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

class Tube extends Sprite
{
	BufferedImage tube_image = null;
	Model model;
	
	Tube(int x, int y, Model m)
	{
		//Load Image of the Tube
		loadTubeImage();
		
		//Initialize Parameters of Position
		type = "tube";
		width = 55;
		height = 400;
		pos_x = x;
		pos_y = y;
		
		model = m;
	}
	
	//Unmarshaling Constructor
	Tube(Json ob, Model m)
	{
		//Load Image of the Tube
		loadTubeImage();
		
		type = "tube";
		width = 55;
		height = 400;
		pos_x = (int)ob.getLong("x");
		pos_y = (int)ob.getLong("y");
		
		model = m;
	}
	
	//Marshaling Constructor
	Json marshal()
	{
		Json ob = Json.newObject();
      	ob.add("x", pos_x);
      	ob.add("y", pos_y);
      	return ob;
	}
	
	void loadTubeImage()
	{
		if(tube_image == null)
		{
			this.tube_image = loadImage("tube.png");
		}
	}
	
	void draw(Graphics g)
	{
		g.drawImage(tube_image, pos_x - model.mario.pos_x + model.mario.marioOffset, pos_y, null);
	}
	
	void update()
	{
		//Does Nothing
	}
	
	boolean detectTube(int mouse_x, int mouse_y)
	{
		if (mouse_x < pos_x)
			return false;
		if (mouse_x > pos_x + width)
			return false;
		if (mouse_y < pos_y)
			return false;
		if (mouse_y > pos_y + height)
			return false;
		return true;
	}
}