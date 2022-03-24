//Name: Rudy Ramirez
//Date: 9/21/20
//Assignment: Homework 3

import java.util.ArrayList;

class Model
{
	ArrayList<Tube> tubes;
	
	Model()
	{
		tubes = new ArrayList<Tube>();
	}
	public void addModel(int x, int y)
	{
		Tube t = new Tube(x, y);
		tubes.add(t);
	}
	public void removeModel(int index)
	{
		tubes.remove(index);
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