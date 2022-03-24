//Name: Rudy Ramirez
//Date: 9/21/20
//Assignment: Homework 3

class Tube
{
	final int width = 55;
	final int height = 400;
	int x;
	int y;
	
	Tube(int tube_x, int tube_y)
	{
		//Initialize Parameters of Position
		x = tube_x;
		y = tube_y;
	}
	
	//Unmarshaling Constructor
	Tube(Json ob)
	{
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
	}
	
	//Marshaling Constructor
	Json marshal()
	{
		Json ob = Json.newObject();
      	ob.add("x", x);
      	ob.add("y", y);
      	return ob;
	}
	
	boolean detectTube(int mouse_x, int mouse_y)
	{
		if (mouse_x < x)
			return false;
		if (mouse_x > x + width)
			return false;
		if (mouse_y < y)
			return false;
		if (mouse_y > y + height)
			return false;
		return true;
	}
}