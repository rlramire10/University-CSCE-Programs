
//Edits Made by Rudy Ramirez
//9/22/21
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
public class BigramCount {
  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
	private final static IntWritable one = new IntWritable(1);
    //New Text Objects
	private Text word1 = new Text();
	private Text word2 = new Text();
	private Text bigram = new Text();
	private Text maptask_message = new Text("Maptasks Counter: ");
	
	protected void cleanup(Context context
	                           ) throws IOException, InterruptedException {
		//Counts how many map tasks are launched
		context.write(maptask_message, one);
	}
	
	public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
	  StringTokenizer itr = new StringTokenizer(value.toString());
	  //Read First Word
	  word1.set(itr.nextToken());
	  while (itr.hasMoreTokens()) {
		//Read Second Word
		word2.set(itr.nextToken());
		//Concatenating The Two Words into One Bigram
		String s1 = word1.toString();
		String s2 = word2.toString();
		String s3 = s1.concat(" ").concat(s2);
		bigram.set(s3);
		//Write bigram to output file
		context.write(bigram, one);
		//Update Word1
		word1.set(s2);
      }
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
      System.err.println("Usage: bigramcount <in> <out>");
      System.exit(2);
    }
    Job job = new Job(conf, "word count");
    job.setJarByClass(BigramCount.class);
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
