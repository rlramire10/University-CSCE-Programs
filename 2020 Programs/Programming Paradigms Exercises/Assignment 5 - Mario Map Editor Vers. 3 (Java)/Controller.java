//Name: Rudy Ramirez
//Date: 10/23/20
//Assignment: Homework 5

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
	boolean ctrl;
	boolean addTubeEditor = false;
	boolean addGoombaEditor = false;

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
	
	//Place New Sprites With Mouse Clicks
	public void mousePressed(MouseEvent e)
	{
		//Add Tubes
		if(addTubeEditor == true)
		{
			model.addTubeModel(e.getX() + model.mario.pos_x - model.mario.marioOffset, e.getY());
		}
		//Add Goombas
		if(addGoombaEditor == true)
		{
			model.addGoombaModel(e.getX() + model.mario.pos_x - model.mario.marioOffset, e.getY());
		}	
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
			case KeyEvent.VK_CONTROL: ctrl = true; break;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_SPACE: spacebar = false; break;
			case KeyEvent.VK_CONTROL: ctrl = false; break;
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
		if(c == 't')
		{
			addGoombaEditor = false;
			addTubeEditor = !addTubeEditor;
			System.out.println("Tube Editor Status: " + addTubeEditor);
			System.out.println("Goomba Editor Status: " + addGoombaEditor);
		}
		if(c == 'g')
		{
			addTubeEditor = false;
			addGoombaEditor = !addGoombaEditor;
			System.out.println("Tube Editor Status: " + addTubeEditor);
			System.out.println("Goomba Editor Status: " + addGoombaEditor);
		}
		if(c == 'q')
			System.exit(0);
	}
	public void keyTyped(KeyEvent e) {}
	public void update()
	{
		model.mario.savePrevPos();
		if(keyRight)
		{
			//Mario Move Right
			model.mario.flip = false;
			model.mario.pos_x += 5;
			model.mario.updateAnimation();
		}
		if(keyLeft)
		{
			//Mario Move Left
			model.mario.flip = true;
			model.mario.pos_x -= 5;
			model.mario.updateAnimation();
		}
		if(spacebar)
		{
			if(model.mario.air_time < 5)
				model.mario.marioJump();
		}
		if(ctrl)
		{
			model.addFireballModel(model.mario.pos_x + model.mario.width/2, model.mario.pos_y + model.mario.height/2);
		}
	}
}