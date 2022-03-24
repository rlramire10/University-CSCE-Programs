
/* wordcount.scala */
/* Created By: Rudy Ramirez */
/* Date: 12/01/21 */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object wordcount {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Word Count")
    val sc = new SparkContext(conf)
    
	val input = sc.textFile("input.txt")
	val words = input.flatMap(x => x.split(" "))
	val result = words.map(x => (x,1)).reduceByKey((x, y) => x + y)
	result.saveAsTextFile("output")
	
    sc.stop()
  }
}
