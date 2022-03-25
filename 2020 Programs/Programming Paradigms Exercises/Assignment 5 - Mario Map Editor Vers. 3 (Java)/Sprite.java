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

abstract class Sprite
{	
	String type;
	
	int width;
	int height;
	
	int pos_x;
	int pos_y;
	
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
	
	abstract void draw(Graphics g);
	abstract void update();
}