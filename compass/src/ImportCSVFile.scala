import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import scala.collection.mutable.ArrayBuffer

/**
  * Created by otto on 11/27/16.
  */
class ImportCSVFile {
  def ReadingCSVFileIntoArray(RDDfile: RDD[String], FilterLength: Int)={

  val headerAndRows = RDDfile.map(line => line.split(",").map(_.trim))
  // get header
  val header = headerAndRows.first
  // filter out header (eh. just check if the first val matches the first header name)
  val data = headerAndRows.filter(_ (0) != header(0))
  // splits to map (header/value pairs)
  val maps = data.map(splits => header.zip(splits).toMap.values.toBuffer)
  // filter out the user "me"
  val result = maps.filter(map => map.length == FilterLength)
  // print result
  //    val a=resultresult.foreach
  //    result.foreach(map=>map.values.toBuffer)
  val ObjectArray = new ArrayBuffer[String]()
  val BufferCluster = result.collect

  for (i <- 0 until BufferCluster.length) {
    ObjectArray ++= BufferCluster(i)
  }
    ObjectArray
  }
  def GetNodeFrequency(EdgesinArray: ArrayBuffer[String], SourceRDD: SparkContext)={


    val ObjectRDD=SourceRDD.parallelize(EdgesinArray)
    val pairs=ObjectRDD.map{word=>(word,1)}.filter(map=>map._1!="contribute").reduceByKey(_+_)
//    pairs.foreach(map=>println(map))
    pairs.collect().toMap
  }
}
