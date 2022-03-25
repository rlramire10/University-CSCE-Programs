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

class Fireball extends Sprite
{
	BufferedImage fireball_image = null;
	Model model;
	
	int direction;
	double vert_vel;
	boolean offScreen;
	
	Fireball(int x, int y, Model m)
	{
		//Load Images
		loadFireballImage();
		
		type = "fireball";
		width = 25;
		height = 25;
		pos_x = x;
		pos_y = y;
		model = m;
		
		direction = 1;
		vert_vel = 9.81;
		offScreen = false;
	}
	
	void loadFireballImage()
	{
		if(fireball_image == null)
		{
			this.fireball_image = loadImage("fireball.png");
		}
	}
	
	void draw(Graphics g)
	{
		g.drawImage(fireball_image, pos_x - model.mario.pos_x + model.mario.marioOffset, pos_y, null);
	}
	
	void update()
	{
		//Set Normal Gravity
		vert_vel += 5.0;
		pos_y += vert_vel;
		
		//Enable Ground
		if(pos_y > 400 - height)
		{
			vert_vel = -30.0;
			pos_y = 400 - height;
		}
		//Enable Ceiling
		if(pos_y < 0)
		{
			pos_y = 0;
		}
		
		//Direction
		if(model.mario.flip == false)
			direction = 1;
		if(model.mario.flip == true)
			direction = -1;
		//Movement	
		pos_x += 5 * direction;
		
		//Checks If Fireballs are Off Screen
		if((pos_x - model.mario.pos_x + model.mario.marioOffset > 500) || (pos_x - model.mario.pos_x + model.mario.marioOffset < 0))
		{
			offScreen = true;
		}
			
			
	}
}