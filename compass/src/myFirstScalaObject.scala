//package compass
import org.apache.spark.{SparkConf, SparkContext}
/**
  * Created by root on 16-3-21.
  */
object myFirstScalaObject {
  def main(args: Array[String]) {
    val conf = new SparkConf()
    conf.setAppName("world")
    conf.setMaster("local")
    val sc = new SparkContext(conf)
    val lines = sc.textFile("/home/otto/Downloads/build.txt")   //数据路径
    val words = lines.flatMap{line => line.split(" ")}
    val pairs = words.map{ word => (word,1)}
    val wordCounts = pairs.reduceByKey(_+_)
    wordCounts.foreach(wordNumberPair => println(wordNumberPair._1 + ":" + wordNumberPair._2))


    val ReadingNodeAndEdgeCSVfile=new ImportCSVFile
//    val CSVedge = sc.textFile("/root/compass_layout_processor/input/edgeKVM_LanguageAdded.csv")
//    val ObjectArrayEdge=ReadingNodeAndEdgeCSVfile.ReadingCSVFileIntoArray(CSVedge,3)
//    val FrequencyofNode=ReadingNodeAndEdgeCSVfile.GetNodeFrequency(ObjectArrayEdge,sc)
//    println(FrequencyofNode.apply("-1"))
    sc.stop()
  }
}