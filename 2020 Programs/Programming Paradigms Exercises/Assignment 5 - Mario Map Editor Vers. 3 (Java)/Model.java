//Name: Rudy Ramirez
//Date: 10/23/20
//Assignment: Homework 5

import java.util.ArrayList;
import java.util.Iterator;

class Model
{
	//Instances of different models
	ArrayList<Sprite> sprites;
	Mario mario;
	
	Model()
	{
		sprites = new ArrayList<Sprite>();
		mario = new Mario(200, 50);
		sprites.add(mario);
	}
	
	//Unmarshaling Constructor
	void unmarshal (Json ob)
	{
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
		
		Json tmpList = ob.get("sprites");
		Json tubeList = tmpList.get("tubes");
		Json goombaList = tmpList.get("goombas");
		//Load Tubes
		for(int i = 0; i < tubeList.size(); i++)
		{
			sprites.add(new Tube(tubeList.get(i), this));	
		}
		//Load Goombas
		for(int i = 0; i < goombaList.size(); i++)
		{
			sprites.add(new Goomba(goombaList.get(i), this));	
		}	
	}
	
	//Marshaling Constructor
	Json marshal()
	{
		Json ob = Json.newObject();
      	Json spriteOb = Json.newObject();
		Json tmpList = Json.newList();
      	ob.add("sprites", spriteOb);
      	//Save Tubes
      	spriteOb.add("tubes", tmpList);
      	for(int i = 0; i < sprites.size(); i++)
      	{
      		if(sprites.get(i).type == "tube")
      		{
      			Tube t = (Tube)sprites.get(i);
      			tmpList.add(t.marshal());
      		}
      	}
      	//Save Goombas
      	tmpList = Json.newList();
      	spriteOb.add("goombas", tmpList);
      	for(int i = 0; i < sprites.size(); i++)
      	{
      		if(sprites.get(i).type == "goomba")
      		{
      			Goomba g = (Goomba)sprites.get(i);
      			tmpList.add(g.marshal());
      		}
      	}
      	return ob;
	}
	
	public void addTubeModel(int x, int y)
	{
		Tube t = new Tube(x, y, this);
		boolean tubeDetected = false;
		
		for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).type == "tube")
			{
				Tube temp = (Tube)sprites.get(i);
				//Checks If Any Tubes Were Clicked
				if (temp.detectTube(x,y) == true)
				{
					tubeDetected = true;
					//Removes Tube
					sprites.remove(i);
				}	
			}
		}
		//Adds Tube
		if (tubeDetected == false)
			sprites.add(t);
	}
	
	public void addGoombaModel(int x, int y)
	{
		Goomba g = new Goomba(x, y, this);
		sprites.add(g);
	}
	
	public void addFireballModel(int x, int y)
	{
		Fireball fb = new Fireball(x, y, this);
		sprites.add(fb);
	}
	
	public void update()
	{
		for(int i = 0; i < sprites.size(); i++)
		{
			sprites.get(i).update();
			//Checks If Mario Collides With Tubes
			if(sprites.get(i).type == "tube")
			{
				Tube t = (Tube)sprites.get(i);
				//Checks Collision Of Two Sprites
				if(modelCollision(mario, t) == true)
				{
					mario.getOutOfTube(t);
				}
			}
			
			//Checks If Goombas Collides With Tubes
			if(sprites.get(i).type == "goomba")
			{
				Goomba g = (Goomba)sprites.get(i);
				for(int i2 = 0; i2 < sprites.size(); i2++)
				{
					if(sprites.get(i2).type == "tube")
					{
						Tube t = (Tube)sprites.get(i2);
						//Checks Collision Of Two Sprites
						if(modelCollision(g, t) == true)
						{
							g.getOutOfTube(t);
						}
					}
				}
			}
			
			//Checks If Fireball Collides With Goomba
			if(sprites.get(i).type == "fireball")
			{
				Fireball fb = (Fireball)sprites.get(i);
				for(int i2 = 0; i2 < sprites.size(); i2++)
				{
					if(sprites.get(i2).type == "goomba")
					{
						Goomba g = (Goomba)sprites.get(i2);
						//Checks Collision Of Two Sprites
						if(modelCollision(fb, g) == true)
						{
							g.onFire = true;
							g.timer--;
							if(g.timer == 0)
								sprites.remove(i2);
						}
					}
				}
				
				//Removes Fireballs if Fireballs are Off Screen
				if (fb.offScreen == true)
					sprites.remove(i);
			}
		}
	}
	
	boolean modelCollision(Sprite a, Sprite b)
	{
		//Sprite a Left of Sprite b
		if(a.pos_x + mario.width < b.pos_x)
			return false;
		//Sprite a Right of Sprite b
		if(a.pos_x > b.pos_x + b.width)
			return false;
		//Sprite a Above Sprite b
		if(a.pos_y + mario.height < b.pos_y)
			return false;
		//Sprite a Below Sprite b
		if(a.pos_y > b.pos_y + b.height)
			return false;
		return true;
	}
}