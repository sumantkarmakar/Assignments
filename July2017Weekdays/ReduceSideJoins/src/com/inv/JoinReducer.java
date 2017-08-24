package com.inv;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class JoinReducer extends Reducer<Text, Text, Text, Text>{
	
	private	String deptname="";
	private	String deptid="";
	
	@Override
	protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		
		String t_key=key.toString();
		
			//002_dept, hive
		if(t_key.contains("dept")){
			for (Text val : values) {
				deptid=t_key.split("_")[0];
				deptname=val.toString();
			}
		}
		//002_emp , < 2,name2,4000,002  ::: 7,name7,4000,002   >
		if(t_key.contains("emp")){
			
			String emp_deptid=t_key.split("_")[0];
			if(!emp_deptid.equals(deptid)){
				deptname="NotFound";				
			}
			for(Text val:values){
				context.write(val, new Text(deptname));
			}
		}
	
	}
	

}
