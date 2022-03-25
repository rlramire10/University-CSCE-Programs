//Name: Rudy Ramirez
//Date: 10/7/20
//Assignment: Homework 4

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements MouseListener, KeyListener
{
	View view;
	Model model;
	
	boolean tubeDetected;
	boolean keyLeft;
	boolean keyRight;
	boolean spacebar;

	Controller()
	{
	}
	Controller(Model m)
	{
		model = m;
	}
	
	//Setters
	void setView(View v)
	{
		view = v;
	}
	void setModel(Model m)
	{
		model = m;
	}
	
	//Place New Tube With Mouse Clicks
	public void mousePressed(MouseEvent e)
	{
		tubeDetected = false;
		
		/*
		//Break Point
		if(e.getY() < 100)
		{
			System.out.println("break here");
		}
		*/
		
		//Checks If Any Tubes Were Clicked
		for(int i = 0; i < model.tubes.size(); i++)
		{
			Tube t = model.tubes.get(i);
			if (t.detectTube(e.getX() + model.mario.pos_x - model.mario.marioOffset, e.getY()) == true)
			{
				tubeDetected = true;
				model.removeTubeModel(i);
			}	
		}
		//Adds Tube
		if (tubeDetected == false)
			model.addTubeModel(e.getX() + model.mario.pos_x - model.mario.marioOffset, e.getY());
	}
	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
	
	//Move Tube Image With Arrow Keys
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_SPACE: spacebar = true; break;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_SPACE: spacebar = false; break;
		}
		char c = e.getKeyChar();
		if(c == 's')
		{
			model.marshal().save("map.json");
			System.out.println("map.json Was Saved");
		}
		if(c == 'l')
		{
			Json j = Json.load("map.json");
			model.unmarshal(j);
			System.out.println("map.json Was Loaded");
		}
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void update()
	{
		model.mario.savePrevPos();
		if(keyRight)
		{
			model.mario.pos_x += 5;
			model.mario.updateAnimation();
		}
		if(keyLeft)
		{
			model.mario.pos_x -= 5;
			model.mario.updateAnimation();
		}
		if(spacebar)
		{
			if(model.mario.air_time < 5)
				model.mario.marioJump();
		}
	}
}