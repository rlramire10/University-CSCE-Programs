
/* pagerank.scala */
/* Created By: Rudy Ramirez */
/* Date: 12/09/21 */
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object pagerank {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Page Rank")
    val sc = new SparkContext(conf)
	
	val maxIterations = 10
	val alpha = 0.1
	
	val iteration = if (args.length > 1) args(1).toInt else maxIterations
	val lines = sc.textFile("input.txt").cache()
	val edges = lines.map{ s => 
		val parts = s.split("\\s+")
		(parts(0), parts(1))
	}
	val links = edges.distinct().groupByKey().cache()

	var ranks = links.mapValues(v => 1.0)
	for(i <- 0 until iteration)
	{
		val contributions = links.join(ranks).flatMap {
			case (pageId, (pageLinks, rank)) =>
				pageLinks.map(dest => (dest, rank/pageLinks.size))
		}
		
		ranks = contributions.reduceByKey(_+_)
		val G = ranks.count()
		val oriMass = ranks.reduce((x, y) => ("mass", x._2 + y._2))._2
		val m = oriMass - 1
		ranks = ranks.mapValues(v => (alpha*(1/G))+((1-alpha)*(m/G + v)))
	}
	
	ranks.saveAsTextFile("output")
	
    sc.stop()
  }
}
