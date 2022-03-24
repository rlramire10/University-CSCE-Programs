
//Edited By: Rudy Ramirez
//Date: 10/13/21
import java.io.IOException;
import java.util.StringTokenizer;
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
public class relativefreq {
	
	//Order Inversion Mapper
	public static class PairsRelativeOccurrenceMapper extends Mapper<LongWritable, Text, wordpair, IntWritable> {
		private wordpair wordPair = new wordpair();
		private IntWritable ONE = new IntWritable(1);
		private IntWritable totalCount = new IntWritable();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			int neighbors = context.getConfiguration().getInt("neighbors", 1);
			String[] tokens = value.toString().split("\\s+");          // split the words using spaces
			if (tokens.length > 1) {
				for (int i = 0; i < tokens.length; i++) {
					tokens[i] = tokens[i].replaceAll("\\W+","");   // remove all non-word characters

					if(tokens[i].equals("")){
						continue;
					}

					wordPair.setWord(tokens[i]);

					int start = (i - neighbors < 0) ? 0 : i - neighbors;
					int end = (i + neighbors >= tokens.length) ? tokens.length - 1 : i + neighbors;
					for (int j = start; j <= end; j++) {
						if (j == i) continue;
						wordPair.setNeighbor(tokens[j].replaceAll("\\W",""));
						context.write(wordPair, ONE);
					}
					wordPair.setNeighbor("*");
					totalCount.set(end - start);
					context.write(wordPair, totalCount);
				}
			}
		}
	}
	
	//Order Inversion Reducer
	public static class PairsRelativeOccurrenceReducer extends Reducer<wordpair, IntWritable, wordpair, DoubleWritable> {
		private DoubleWritable totalCount = new DoubleWritable();
		private DoubleWritable relativeCount = new DoubleWritable();
		private Text currentWord = new Text("NOT_SET");
		private Text flag = new Text("*");

		@Override
		protected void reduce(wordpair key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			if (key.getNeighbor().equals(flag)) {          //start a new section of word pairs with a new left word
				if (key.getWord().equals(currentWord)) {   //keep adding the counts of (word, *).
														   //In fact, this will not happen because ...
					totalCount.set(totalCount.get() + getTotalCount(values));
				} else {                                   //reset the count when encounting (word, *) for the first time
					currentWord.set(key.getWord());
					totalCount.set(0);
					totalCount.set(getTotalCount(values));
				}
			} else {                                       //calculate the relative frequency
				int count = getTotalCount(values);
				relativeCount.set((double) count / totalCount.get());
				context.write(key, relativeCount);
			}
		}
		private int getTotalCount(Iterable<IntWritable> values) {
			int count = 0;
			for (IntWritable value : values) {
				count += value.get();
			}
			return count;
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
		  System.err.println("Usage: relativefreq <in> <out>");
		  System.exit(2);
		}
		Job job = new Job(conf, "relative frequency");
		
		//Set Specific Amount of Reducers
		job.setNumReduceTasks(3);
		
		//Utilize Mapper and Reducer Classes From relativefreq.java
		job.setJarByClass(relativefreq.class);
		job.setMapperClass(PairsRelativeOccurrenceMapper.class);
		job.setReducerClass(PairsRelativeOccurrenceReducer.class);
		
		//Set Map Output
		job.setMapOutputKeyClass(wordpair.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		//Set Output
		job.setOutputKeyClass(wordpair.class);
		job.setOutputValueClass(DoubleWritable.class);
		
		//Set File Formats
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
