
//Edited By: Rudy Ramirez
//Date: 11/4/21

import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;

public class Node {

  public static enum Color {
    WHITE, GRAY, BLACK
  };

  private final int id;
  private List<Integer> edges = new ArrayList<Integer>();
  private List<Integer> weights = new ArrayList<Integer>();
  private int distance;
  private Color color = Color.WHITE;

  public Node(String str) {

    String[] map = str.split("\t");
    String key = map[0];
    String value = map[1];

    String[] tokens = value.split("\\|");

    this.id = Integer.parseInt(key);

    //Store Edges From Line
	for (String s0 : tokens[0].split(",")) {
      if (s0.length() > 0) {
        edges.add(Integer.parseInt(s0));
      }
    }
	
	//Store Weights From Line
	for (String s1 : tokens[1].split(",")) {
      if (s1.length() > 0) {
        weights.add(Integer.parseInt(s1));
      }
    }
	
	//Store Distance From Line
    if (tokens[2].equals("Integer.MAX_VALUE")) {
      this.distance = Integer.MAX_VALUE;
    } else {
      this.distance = Integer.parseInt(tokens[2]);
    }
    
	//Store Color From Line
    this.color = Color.valueOf(tokens[3]);

  }


  public Node(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
  
  public List<Integer> getEdges() {
    return this.edges;
  }
  
  public void setEdges(List<Integer> edges) {
    this.edges = edges;
  }
  
  public List<Integer> getWeights() {
    return this.weights;
  }
  
  public void setWeights(List<Integer> weights) {
    this.weights = weights;
  }
  
  public int getDistance() {
    return this.distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Text getLine() {
    StringBuffer s = new StringBuffer();
    
    for (int v1 : edges) {
      s.append(v1).append(",");
    }
	s.append("|");
	
	for (int v2 : weights) {
      s.append(v2).append(",");
    }
	s.append("|");
	
    if (this.distance < Integer.MAX_VALUE) {
      s.append(this.distance).append("|");
    } else {
      s.append("Integer.MAX_VALUE").append("|");
    }

    s.append(color.toString());

    return new Text(s.toString());
  }

}
