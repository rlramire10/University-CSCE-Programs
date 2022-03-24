//Edits Made by Rudy Ramirez
//9/11/21
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
public class WordCount {
  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
	private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
	//Create New IntWritable object
	private Text map_message = new Text("Map Counter: ");
	public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
	  StringTokenizer itr = new StringTokenizer(value.toString());
	  while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        context.write(word, one);
      }
	  //Counts The Number of Instances The Map Method Is Used
	  context.write(map_message, one);
    }
  }
  public static class IntSumReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
	private IntWritable result = new IntWritable();
    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
	  int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }
    Job job = new Job(conf, "word count");
    job.setJarByClass(WordCount.class);
	job.setMapperClass(TokenizerMapper.class);
	job.setCombinerClass(IntSumReducer.class);
	job.setReducerClass(IntSumReducer.class);
	job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
	FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    
	System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
