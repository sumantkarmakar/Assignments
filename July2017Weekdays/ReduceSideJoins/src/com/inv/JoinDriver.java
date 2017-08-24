package com.inv;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JoinDriver {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf=new Configuration();
		conf.set("mapreduce.output.textoutputformat.separator", ",");
		
		
		Job job=Job.getInstance(conf,"Reduce Side Join Driver");
		
		job.setJarByClass(JoinDriver.class);
		//job.setMapperClass(cls);
		job.setReducerClass(JoinReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		
		MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, EmpMapper.class);
		MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class,DeptMapper.class);
		
		System.exit(job.waitForCompletion(true)?0:1);
		
		
		
		
		
		
	}
}
