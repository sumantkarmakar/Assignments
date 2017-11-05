import org.apache.spark.SparkContext
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.sql.SparkSession


object StructuredStreaming {
  def main(args: Array[String]): Unit = {
    
    Logger.getLogger("org").setLevel(Level.ERROR)
    
    val conf = new SparkConf().setMaster("local[*]")
    .setAppName("ReadNetwork")
    
    
    //val ssc = new StreamingContext(conf, Seconds(5))
    
 var spark=SparkSession.
 builder.
 appName("Movies").
 master("local[*]").
 getOrCreate()
 
  val streamingDF=spark.readStream.format("socket").
  option("host","localhost").option("port","9999").load()
  
  
  streamingDF.createOrReplaceTempView("sample")
  /*val wc=spark.sql("""select count(word),word 
    from (select explode(split(" ")) word from sample )A 
    group by word
    """)  */
  val wc=spark.sql("""select count(*) from sample
    """) 
  
  val op=wc.writeStream.outputMode("complete").
  format("console").start()
  
  op.awaitTermination()
  
//  .format("socket").
//  options("host","localhost").
//  options("port","9999").
//  load()
//  //streamingDF
  
  /* val wc= streamingDF.as[String].flatMap(line => line.split(" ")).
    groupBy("value").count()
    wc.writeStream.format("console").start()
    
    wc.awaitTermination()*/
   /* //lines is DStream
    val lines = ssc.socketTextStream("localhost", 9999)
    
    
    
    val word_count= lines.flatMap(x => x.split(" "))
    .map(word => (word, 1))
    .reduceByKey((x,y) => x+y)
    
    word_count.print()
    
    
    ssc.start()
    ssc.awaitTermination()*/
    
 }
}