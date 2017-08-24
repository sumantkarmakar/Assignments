import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountDriver extends Configured implements Tool{

	@Override
	public int run(String[] arg0) throws Exception {
		
		for (int i = 0; i < arg0.length; i++) {
			System.out.println("IN RUN ARG0"+i+"==="+arg0[i]);
		}
		
		
		Configuration conf=this.getConf(); // Configuration();
		
		System.out.println("MY FAV COLOR IS:::"+conf.get("COLOR"));

		System.out.println("MY FAV COLOR1 IS:::"+conf.get("COLOR1"));

		System.out.println("MY FAV COLOR2 IS:::"+conf.get("COLOR2"));
		

		System.out.println("MY FAV COLOR DEF IS:::"+conf.get("COLOR2","I AM DEF COLOR"));
		
		//conf.set("mapreduce.output.fileoutputformat.compress", "true");
		//conf.set("mapreduce.output.fileoutputformat.compress.codec", "org.apache.hadoop.io.compress.GzipCodec");
		
		//conf.set("mapreduce.map.output.compress", "true");
		//conf.set("mapreduce.map.output.compress.codec", "org.apache.hadoop.io.compress.BZip2Codec");
		//Job job=new Job();
		Job job=Job.getInstance(conf, "Sample Word Count");
		
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		job.setJarByClass(WordCountDriver.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//program arg0(i/pfile) args1(o/plocation)
		
		FileSystem fs=FileSystem.get(conf);
		if(fs.exists(new Path(arg0[1]))){
			System.out.println("+++ output path exists");
			System.exit(1);
			fs.delete(new Path(arg0[1]), true);
		}else
			System.out.println("----- output path does not exists");
		
		
		
		FileInputFormat.addInputPath(job, new Path(arg0[0]));
		FileOutputFormat.setOutputPath(job,new Path(arg0[1]));
		
		/*FileOutputFormat.setCompressOutput(job, true);*/
		//FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);
		return job.waitForCompletion(true)?0:1;
	}
	public static void main(String[] args) throws Exception {
			

		for (int i = 0; i < args.length; i++) {
			System.out.println("IN MAIN ARGS"+i+"==="+args[i]);
		}
		
		
		int res=ToolRunner.run(new WordCountDriver(), args);
		System.exit(res);
		
	}
	
	
}
