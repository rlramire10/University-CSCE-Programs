
//Created By: Rudy Ramirez
//Date: 10/23/21
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
//***************************************************************************
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import java.util.HashMap;
import java.util.Map;

public class invertedindex {
	//Mapper class
	public static class InvertedIndexMapper extends Mapper<LongWritable,Text,wordpair,IntWritable> { 
		Map <wordpair, Integer> occurenceList = new HashMap<wordpair, Integer>();
		
		@Override
		public void map(LongWritable key, Text value, Context context)
		throws IOException,InterruptedException
		{
			/*Get the name of the file using context.getInputSplit()method*/
			String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
			StringTokenizer itr = new StringTokenizer(value.toString());
			int sum = 0;
			while(itr.hasMoreTokens()){
				wordpair WP = new wordpair();
				WP.setKey(itr.nextToken());
				WP.setValues(fileName); 
				if(occurenceList.containsKey(WP)) {
					sum = (int) occurenceList.get(WP) + 1;
					occurenceList.put(WP, sum);
				}
				else 
					occurenceList.put(WP,1);
				}
		}
		
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			//For unique word, emit a wordpair as key and its occurence as value
			for(Map.Entry<wordpair, Integer> temp : occurenceList.entrySet()){
				context.write(new wordpair(temp.getKey().getKey(), temp.getKey().getValues()), new IntWritable(temp.getValue()));
			}
		}
	}
	
	//Reducer class
	public static class InvertedIndexReducer extends Reducer<wordpair, IntWritable, Text, Text> {
		Text currentTerm = new Text();
		Text results =     new Text();
		Text prevTerm =    new Text();
		Text emptyText =   new Text();

		public void reduce(wordpair key, IntWritable value,Context context)
		throws IOException, InterruptedException {			
			StringBuilder postingList = new StringBuilder();
			currentTerm = key.getKey();
			Text fileID = key.getValues();
			
			//if t != t_prev && t_prev != 0
			if ((currentTerm != prevTerm) && (prevTerm != emptyText)){
				//emit(term t, postings P)
				context.write(currentTerm, results);
				
				//P.Reset()
				postingList.delete(0, postingList.length());
				results = emptyText;
			}			
			
			//P.Add(<n,f>)
			postingList.append(fileID.toString() + ":" + value.toString() + ";");
			results.set(postingList.toString());
			
			//t_prev = t;
			prevTerm = currentTerm;
		}
		
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			//emit(term t, postings P)
			context.write(currentTerm, results);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
		  System.err.println("Usage: invertedindex <in> <out>");
		  System.exit(2);
		}
		Job job = new Job(conf, "inverted index");
		
		//Set Specific Amount of Reducers
		job.setNumReduceTasks(3);
		
		//Utilize Mapper and Reducer Classes From invertedindex.java
		job.setJarByClass(invertedindex.class);
		job.setMapperClass(InvertedIndexMapper.class);
		
		job.setReducerClass(InvertedIndexReducer.class);
		
		//Set Map Output
		job.setMapOutputKeyClass(wordpair.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		//Set Output
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		//Set File Formats
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}