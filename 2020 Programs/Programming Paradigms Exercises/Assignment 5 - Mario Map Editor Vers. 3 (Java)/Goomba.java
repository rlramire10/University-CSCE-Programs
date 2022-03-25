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

public class Goomba extends Sprite
{
	BufferedImage goomba_image = null;
	Model model;
	
	double vert_vel;
	int direction;
	boolean flip;
	boolean onFire;
	int timer;
	
	int pos_px;
	int pos_py;
	
	Goomba(int x, int y, Model m)
	{
		//Load Image of the Tube
		loadGoombaImage();
		
		//Initialize Parameters of Position
		type = "goomba";
		width = 40;
		height = 48;
		pos_x = x;
		pos_y = y;
		
		model = m;
		direction = 1;
		flip = false;
		onFire = false;
		timer = 15;
	}
	
	//Unmarshaling Constructor
	Goomba(Json ob, Model m)
	{
		//Load Image of the Tube
		loadGoombaImage();
		
		type = "goomba";
		width = 40;
		height = 48;
		pos_x = (int)ob.getLong("x");
		pos_y = (int)ob.getLong("y");
		
		model = m;
		direction = 1;
		flip = false;
		onFire = false;
		timer = 15;
	}
	
	//Marshaling Constructor
	Json marshal()
	{
		Json ob = Json.newObject();
      	ob.add("x", pos_x);
      	ob.add("y", pos_y);
      	return ob;
	}
	
	//Normal Goomba Image
	void loadGoombaImage()
	{
		if(goomba_image == null)
		{
			this.goomba_image = loadImage("goomba.png");
		}
	}
	
	//Fire Goomba Image
	void loadGoombaFireImage()
	{
		if(goomba_image != null)
		{
			this.goomba_image = loadImage("goomba_fire.png");
		}
	}
	
	void draw(Graphics g)
	{
		if(flip)
			g.drawImage(goomba_image, pos_x - model.mario.pos_x + model.mario.marioOffset, pos_y, width, height, null);
		else
			g.drawImage(goomba_image, pos_x - model.mario.pos_x + model.mario.marioOffset + width, pos_y, -width, height, null);
	}
	
	void savePrevPos()
	{
		pos_px = pos_x;
		pos_py = pos_y;
	}
	
	void update()
	{
		savePrevPos();
		//Set Normal Gravity
		vert_vel += 5.0;
		pos_y += vert_vel;
		
		//Enable Ground
		if(pos_y > 400 - height)
		{
			vert_vel = 0.0;
			pos_y = 400 - height;
		}
		
		//Movement
		pos_x += 2 * direction;
		
		//Check Fire Status
		if(onFire == true)
		{
			loadGoombaFireImage();
		}
	}
	
	void getOutOfTube(Tube t)
	{
		//Move Left Out Of Tube
		if( (pos_x + width >= t.pos_x) && (pos_px + width <= t.pos_x) )
		{
			pos_x = t.pos_x - width;
			direction = -1;
			flip = true;
		}	
		//Move Right Out Of Tube
		if( (pos_x <= t.pos_x + t.width) && (pos_px >= t.pos_x + t.width) )
		{
			pos_x = t.pos_x + t.width;
			direction = 1;
			flip = false;
		}	
		//Move Up Out Of Tube
		if( (pos_y + height >= t.pos_y) && (pos_py + height <= t.pos_y) )
			pos_y = t.pos_y - height;
		//Move Down Out Of Tube
		if( (pos_y <= t.pos_y + t.height) && (pos_py >= t.pos_y + t.height) )
			pos_y = t.pos_y + t.height;
	}
	
	boolean detectGoomba(int mouse_x, int mouse_y)
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