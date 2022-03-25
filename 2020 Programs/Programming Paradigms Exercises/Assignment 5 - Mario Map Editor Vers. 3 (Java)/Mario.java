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

class Mario extends Sprite
{
	BufferedImage[] mario_images = null;
	
	int marioImageNum;
	int marioOffset;
	boolean flip;
	
	double vert_vel;
	int air_time;
	
	int pos_px;
	int pos_py;
	
	Mario(int x, int y)
	{
		//Load Images
		loadMarioImages();
		
		type = "mario";
		width = 60;
		height = 95;
		pos_x = x;
		pos_y = y;
		
		marioImageNum = 0;
		marioOffset = 100;
		flip = false;
		
		vert_vel = 9.81;
		air_time = 0;
	}
	
	void loadMarioImages()
	{
		if (mario_images == null)
		{
			mario_images = new BufferedImage[5];
			mario_images[0] = loadImage("mario1.png");
			mario_images[1] = loadImage("mario2.png");
			mario_images[2] = loadImage("mario3.png");
			mario_images[3] = loadImage("mario4.png");
			mario_images[4] = loadImage("mario5.png");
		}
	}
	
	void draw(Graphics g)
	{
		if(flip)
			g.drawImage(mario_images[marioImageNum], pos_x - pos_x + marioOffset + width, pos_y, -width, height, null);
		else
			g.drawImage(mario_images[marioImageNum], pos_x - pos_x + marioOffset, pos_y, width, height, null);
	}
	
	void savePrevPos()
	{
		pos_px = pos_x;
		pos_py = pos_y;
	}
	
	void update()
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
	
	void marioJump()
	{
		vert_vel = -30.0;
	}
	
	void getOutOfTube(Tube t)
	{
		//Jump Left Out Of Tube
		if( (pos_x + width >= t.pos_x) && (pos_px + width <= t.pos_x) )
			pos_x = t.pos_x - width;
		//Jump Right Out Of Tube
		if( (pos_x <= t.pos_x + t.width) && (pos_px >= t.pos_x + t.width) )
			pos_x = t.pos_x + t.width;
		//Jump Up Out Of Tube
		if( (pos_y + height >= t.pos_y) && (pos_py + height <= t.pos_y) )
		{
			pos_y = t.pos_y - height;
			//Allows Mario To Better Jump Off Of Top Of Tube
			air_time = 0;
		}
		//Jump Down Out Of Tube
		if( (pos_y <= t.pos_y + t.height) && (pos_py >= t.pos_y + t.height) )
			pos_y = t.pos_y + t.height;
	}
}