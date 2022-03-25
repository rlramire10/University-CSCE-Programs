//Name: Rudy Ramirez
//Date: 10/7/20
//Assignment: Homework 4

import java.util.ArrayList;
import java.util.Iterator;

class Model
{
	//Instances of different models
	ArrayList<Tube> tubes;
	Mario mario;
	Iterator<Tube> iterator;
	
	Model()
	{
		tubes = new ArrayList<Tube>();
		mario = new Mario(200, 50);
	}
	public void addTubeModel(int x, int y)
	{
		Tube t = new Tube(x, y);
		tubes.add(t);
	}
	public void removeTubeModel(int index)
	{
		tubes.remove(index);
	}
	
	public void update()
	{
		iterator = tubes.iterator();
		mario.updateGravity();
		
		//Checks If Mario Collides With Tubes
		//Original
		/*
		for(int i = 0; i < tubes.size(); i++)
		{
			Tube t = tubes.get(i);
			if(modelCollision(t) == true)
			{
				mario.getOutOfTube(t);
			}
		}
		*/
		//Use Of Iterator
		while(iterator.hasNext())
		{
			Tube t = iterator.next();
			if(modelCollision(t) == true)
			{
				mario.getOutOfTube(t);
			}
		}
	}
	
	boolean modelCollision(Tube tube)
	{
		//Mario Left of Tube
		if(mario.pos_x + mario.width < tube.x)
			return false;
		//Mario Right of Tube
		if(mario.pos_x > tube.x + tube.width)
			return false;
		//Mario Above Tube
		if(mario.pos_y + mario.height < tube.y)
			return false;
		//Mario Below Tube
		if(mario.pos_y > tube.y + tube.height)
			return false;
		return true;
	}
	
	//Unmarshaling Constructor
	void unmarshal (Json ob)
	{
		tubes = new ArrayList<Tube>();
		Json tmpList = ob.get("tubes");
		for(int i = 0; i < tmpList.size(); i++)
			tubes.add(new Tube(tmpList.get(i)));
	}
	
	//Marshaling Constructor
	Json marshal()
	{
		Json ob = Json.newObject();
      	Json tmpList = Json.newList();
      	ob.add("tubes", tmpList);
      	
      	for(int i = 0; i < tubes.size(); i++)
    	  	tmpList.add(tubes.get(i).marshal());
      	return ob;
      	
	}
}