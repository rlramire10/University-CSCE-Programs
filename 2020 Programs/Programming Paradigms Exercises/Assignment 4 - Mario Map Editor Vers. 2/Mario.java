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

class Mario
{
	BufferedImage[] mario_images = null;
	
	final int width = 60;
	final int height = 95;
	int marioImageNum;
	int marioOffset;
	
	double vert_vel;
	int air_time;
	
	int pos_x;
	int pos_y;
	int pos_px;
	int pos_py;
	
	Mario(int x, int y)
	{
		//Load Images
		if (mario_images == null)
		{
			mario_images = new BufferedImage[5];
			mario_images[0] = loadImage("mario1.png");
			mario_images[1] = loadImage("mario2.png");
			mario_images[2] = loadImage("mario3.png");
			mario_images[3] = loadImage("mario4.png");
			mario_images[4] = loadImage("mario5.png");
		}
		
		marioOffset = 100;
		
		pos_x = x;
		pos_y = y;
		marioImageNum = 0;
		vert_vel = 9.81;
		air_time = 0;
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
	
	public void drawMario(Graphics g)
	{
		g.drawImage(mario_images[marioImageNum], pos_x - pos_x + marioOffset, pos_y, null);
	}
	
	void savePrevPos()
	{
		pos_px = pos_x;
		pos_py = pos_y;
	}
	
	void getOutOfTube(Tube t)
	{
		//Jump Left Out Of Tube
		if( (pos_x + width >= t.x) && (pos_px + width <= t.x) )
			pos_x = t.x - width;
		//Jump Right Out Of Tube
		if( (pos_x <= t.x + t.width) && (pos_px >= t.x + t.width) )
			pos_x = t.x + t.width;
		//Jump Up Out Of Tube
		if( (pos_y + height >= t.y) && (pos_py + height <= t.y) )
		{
			pos_y = t.y - height;
			
			//Allows Mario To Better Jump Off Of Top Of Tube
			air_time = 0;
		}
		//Jump Down Out Of Tube
		if( (pos_y <= t.y + t.height) && (pos_py >= t.y + t.height) )
			pos_y = t.y + t.height;
	}
	
	void marioJump()
	{
		vert_vel = -30.0;
	}
	
	void updateGravity()
	{
		//Set Normal Gravity
		vert_vel += 5.0;
		pos_y += vert_vel;
		air_time += 1;
		
		//Enable Ground
		if(pos_y > 400 - height)
		{
			vert_vel = 0.0;
			pos_y = 400 - height;
			air_time = 0;
		}
		//Enable Ceiling
		if(pos_y < 0)
		{
			pos_y = 0;
		}
	}
	
	void updateAnimation()
	{
		marioImageNum++;
		if(marioImageNum > 4)
			marioImageNum = 0;
	}
}