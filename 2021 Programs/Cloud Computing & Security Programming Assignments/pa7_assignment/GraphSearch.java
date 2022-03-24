
//Edited By: Rudy Ramirez
//Date: 11/4/21

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class GraphSearch extends Configured implements Tool {

  //Mapper Class
  public static class MapClass extends MapReduceBase implements
      Mapper<LongWritable, Text, IntWritable, Text> {

    public void map(LongWritable key, Text value, OutputCollector<IntWritable, Text> output,
        Reporter reporter) throws IOException {

      Node node = new Node(value.toString());

      // For each GRAY node, emit each of the edges as a new node (also GRAY)
      if (node.getColor() == Node.Color.GRAY) {
        for (int v : node.getEdges()) {
          Node vnode = new Node(v);
          vnode.setWeights(node.getWeights());
		  vnode.setDistance(node.getDistance() + 1);
          vnode.setColor(Node.Color.GRAY);
          output.collect(new IntWritable(vnode.getId()), vnode.getLine());
        }
        // We're done with this node now, color it BLACK
        node.setColor(Node.Color.BLACK);
      }

      // No matter what, we emit the input node
      // If the node came into this method GRAY, it will be output as BLACK
      output.collect(new IntWritable(node.getId()), node.getLine());
    }
  }

  //Reducer Class
  public static class Reduce extends MapReduceBase implements
      Reducer<IntWritable, Text, IntWritable, Text> {

    public void reduce(IntWritable key, Iterator<Text> values,
        OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {

      List<Integer> edges = null;
	  List<Integer> weights = null;
      int distance = Integer.MAX_VALUE;
      Node.Color color = Node.Color.WHITE;

      while (values.hasNext()) {
        Text value = values.next();

        Node u = new Node(key.get() + "\t" + value.toString());

        // One (and only one) copy of the node will be the fully expanded
        // version, which includes the edges
        if (u.getEdges().size() > 0) {
          edges = u.getEdges();
        }
		
		//Save the weights
		if (u.getWeights().size() > 0) {
          weights = u.getWeights();
        }

        // Save the minimum distance
        if (u.getDistance() < distance) {
          distance = u.getDistance();
		  // Save color of minimum distance
		  if (u.getColor() == Node.Color.BLACK)
		  {
			  color = u.getColor();
		  } else {
			  color = Node.Color.GRAY;
		  }  
        }

      }

      Node n = new Node(key.get());
      n.setEdges(edges);
	  n.setWeights(weights);
	  n.setDistance(distance);     
      n.setColor(color);
      output.collect(key, new Text(n.getLine()));
    }
  }

  static int printUsage() {
    System.out.println("graphsearch [-m <num mappers>] [-r <num reducers>]");
    ToolRunner.printGenericCommandUsage(System.out);
    return -1;
  }

  private JobConf getJobConf(String[] args) {
    JobConf conf = new JobConf(getConf(), GraphSearch.class);
    conf.setJobName("graphsearch");

    //The keys are the unique identifiers for a Node (ints in this case).
    conf.setOutputKeyClass(IntWritable.class);
    //The values are the string representation of a Node
    conf.setOutputValueClass(Text.class);

    conf.setMapperClass(MapClass.class);
    conf.setReducerClass(Reduce.class);

	int maxIterationCount = 0;
	
    for (int i = 0; i < args.length; ++i) {
      if ("-m".equals(args[i])) {
        conf.setNumMapTasks(Integer.parseInt(args[++i]));
      } else if ("-r".equals(args[i])) {
        conf.setNumReduceTasks(Integer.parseInt(args[++i]));
      } else if ("-i".equals(args[i])) {
		maxIterationCount = Integer.parseInt(args[++i]);
	  }
    }

    return conf;
  }

  public int run(String[] args) throws Exception {

	int maxIterationCount = 0;
	
	for (int i = 0; i < args.length; ++i) {
      if ("-i".equals(args[i])) {
        maxIterationCount = Integer.parseInt(args[++i]);
      } 
    }
    
	int iterationCount = 0;

    while (keepGoing(iterationCount, maxIterationCount)) {

      String input;
      if (iterationCount == 0)
        input = "input-graph";
      else
        input = "output-graph-" + iterationCount;

      String output = "output-graph-" + (iterationCount + 1);

      JobConf conf = getJobConf(args);
      FileInputFormat.setInputPaths(conf, new Path(input));
      FileOutputFormat.setOutputPath(conf, new Path(output));
      RunningJob job = JobClient.runJob(conf);

      iterationCount++;
    }

    return 0;
  }
  
  private boolean keepGoing(int iterationCount, int maxIterationCount) {
    if(iterationCount >= maxIterationCount) {
      return false;
    }
    
    return true;
  }

  public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new Configuration(), new GraphSearch(), args);
    System.exit(res);
  }

}
