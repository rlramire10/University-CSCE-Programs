//Name: Rudy Ramirez
//Date: 10/7/20
//Assignment: Homework 4

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

class Tube
{
	BufferedImage tube_image = null;
	
	final int width = 55;
	final int height = 400;
	int x;
	int y;
	
	Tube(int tube_x, int tube_y)
	{
		//Load Image of the Tube
		if(tube_image == null)
		{
			this.tube_image = loadImage("tube.png");
		}
		
		//Initialize Parameters of Position
		x = tube_x;
		y = tube_y;
	}
	
	static BufferedImage loadImage(String filename)
	{
		BufferedImage im = null;
		try
		{
			im = ImageIO.read(new File(filename));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return im;
	}
	
	public void drawTube(Graphics g, int mario_x, int marioOffset)
	{
		g.drawImage(tube_image, x - mario_x + marioOffset, y, null);
	}
	
	boolean detectTube(int mouse_x, int mouse_y)
	{
		if (mouse_x < x)
			return false;
		if (mouse_x > x + width)
			return false;
		if (mouse_y < y)
			return false;
		if (mouse_y > y + height)
			return false;
		return true;
	}
	
	//Unmarshaling Constructor
	Tube(Json ob)
	{
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
	}
	
	//Marshaling Constructor
	Json marshal()
	{
		Json ob = Json.newObject();
      	ob.add("x", x);
      	ob.add("y", y);
      	return ob;
	}
}