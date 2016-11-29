import java.awt.{Dimension, Graphics2D}
import javax.swing.JFrame

import at.ait.dme.forcelayout.renderer.{BufferedInteractiveGraphRenderer, Edge2D}
import at.ait.dme.forcelayout.{Edge, Node, SpringGraph}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.SQLContext
import java.io._
/**
  * Created by otto on 11/3/16.
  */
object GraphDrawingforCompassTest {
  def main(args: Array[String]) {
    val conf = new SparkConf()
    conf.setAppName("world")
    conf.setMaster("local")
    val sc = new SparkContext(conf)
    //    val lines = sc.textFile("/home/otto/Downloads/build.txt")   //数据路径


    val CSVnode = sc.textFile("/root/compass_layout_processor/input/node.csv") // original file
//    val headerAndRowsNode = CSVnode.map(line => line.split(",").map(_.trim))
//    // get header
//    val headerNode = headerAndRowsNode.first
//    // filter out header (eh. just check if the first val matches the first header name)
//    val dataNode = headerAndRowsNode.filter(_ (0) != headerNode(0))
//    // splits to map (header/value pairs)
//    val mapsNode = dataNode.map(splits => headerNode.zip(splits).toMap.values.toBuffer)
//    // filter out the user "me"
//    val resultNode = mapsNode.filter(map => map.length == 2)
//    // print result
//    //    val a=resultresult.foreach
//    //    result.foreach(map=>map.values.toBuffer)
//    val ObjectArrayNode = new ArrayBuffer[String]()
//    resultNode.foreach(println)
//    val BufferClusterNode = resultNode.collect
//
//    for (i <- 0 until BufferClusterNode.length) {
//      ObjectArrayNode ++= BufferClusterNode(i)
//    }
    val ReadingNodeAndEdgeCSVfile=new ImportCSVFile
    val ObjectArrayNode=ReadingNodeAndEdgeCSVfile.ReadingCSVFileIntoArray(CSVnode,2)
    println(ObjectArrayNode)
    val productnode = ObjectArrayNode.length / 2
    println(productnode)
    //    print(CSVdata)
    var nodes = Seq[Node]()
    val IndexofNode = new ArrayBuffer[Int]()
    for (i <- 0 until productnode) {
      if(ObjectArrayNode(2 * i + 1)=="C"){
        nodes = nodes :+ Node(ObjectArrayNode(2 * i), ObjectArrayNode(2 * i), 1.0, 6)
      }
      else if(ObjectArrayNode(2 * i + 1)=="Java"){
        nodes = nodes :+ Node(ObjectArrayNode(2 * i), ObjectArrayNode(2 * i), 1.0, 0)
      }
      else if(ObjectArrayNode(2 * i + 1)=="Python"){
        nodes = nodes :+ Node(ObjectArrayNode(2 * i), ObjectArrayNode(2 * i), 1.0, 2)
      }
      else if(ObjectArrayNode(2 * i + 1)=="Shell"){
        nodes = nodes :+ Node(ObjectArrayNode(2 * i), ObjectArrayNode(2 * i), 1.0, 3)
      }
      else if(ObjectArrayNode(2 * i + 1)=="HTML"){
        nodes = nodes :+ Node(ObjectArrayNode(2 * i), ObjectArrayNode(2 * i), 1.0, 9)
      }
      else if(ObjectArrayNode(2 * i ).toInt<0){
        nodes = nodes :+ Node(ObjectArrayNode(2 * i), ObjectArrayNode(2 * i), 1.0, 1)
      }

      else if(ObjectArrayNode(2 * i + 1)=="JavaScript"){
        nodes = nodes :+ Node(ObjectArrayNode(2 * i), ObjectArrayNode(2 * i), 1.0, 5)}
      //      else{
      //        nodes = nodes :+ Node(ObjectArrayNode(2 * i), ObjectArrayNode(2 * i), 1.0, ObjectArrayNode(2 * i + 1).length)
      //      }
      else if(ObjectArrayNode(2 * i + 1)=="user"){
        nodes = nodes :+ Node(ObjectArrayNode(2 * i), ObjectArrayNode(2 * i), 1.0, 7)
      }
      else{
        nodes = nodes :+ Node(ObjectArrayNode(2 * i), ObjectArrayNode(2 * i), 1.0, ObjectArrayNode(2 * i + 1).length)
      }
      IndexofNode += ObjectArrayNode(2 * i).toInt
    }
    println("Nodes are loaded.")
    //    println(nodes)
    println("The Index of Nodes are ready.")
    //    println(IndexofNode)




    val CSVedge = sc.textFile("/root/compass_layout_processor/input/edge.csv") // original file
//    val headerAndRowsEdge = CSVedge.map(line => line.split(",").map(_.trim))
//    // get header
//    val headerEdge = headerAndRowsEdge.first
//    // filter out header (eh. just check if the first val matches the first header name)
//    val dataEdge = headerAndRowsEdge.filter(_ (0) != headerEdge(0))
//    // splits to map (header/value pairs)
//    val mapsEdge = dataEdge.map(splits => headerEdge.zip(splits).toMap.values.toBuffer)
//    // filter out the user "me"
//    val resultEdge = mapsEdge.filter(map => map.length == 3)
//    // print result
//    //    val a=resultresult.foreach
//    //    result.foreach(map=>map.values.toBuffer)
//    val ObjectArrayEdge = new ArrayBuffer[String]()
//    //    resultEdge.foreach(println)
//    val BufferClusterEdge = resultEdge.collect
//
//    for (i <- 0 until BufferClusterEdge.length) {
//      ObjectArrayEdge ++= BufferClusterEdge(i)
//    }
    //    println(ObjectArrayEdge)
    val ObjectArrayEdge=ReadingNodeAndEdgeCSVfile.ReadingCSVFileIntoArray(CSVedge,3)

    val productedge = ObjectArrayEdge.length / 3
    //    println(ObjectArrayEdge)
    //     print(CSVdataedge)
    var edges = Seq[Edge]()
    for (i <- 0 until productedge) yield {
      if (IndexofNode.contains(ObjectArrayEdge(3 * i).toInt)&&IndexofNode.contains(ObjectArrayEdge(3 * i+1).toInt)) {

        edges = edges :+ Edge(nodes(IndexofNode.indexOf(ObjectArrayEdge(3 * i).toInt)), nodes(IndexofNode.indexOf(ObjectArrayEdge(3 * i + 1).toInt)), 1.0)

      }
    }
    println("Nodes are loaded.")
    println("Start Building Graph")
    val t0 = System.nanoTime() : Double
    //    var t0 = System.currentTimeMillis()
    val graph = new SpringGraph(nodes, edges)
    println("Original Graph is ready")
        println(graph.nodes)


        val vis = new BufferedInteractiveGraphRenderer(graph)

        val frame = new JFrame("KVM")
        frame.setPreferredSize(new Dimension(920,720))
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        frame.getContentPane().add(vis)
        frame.pack()
        frame.setVisible(true)

        println("Omitting edges for faster drawing...")
        vis.setEdgePainter((edges: Seq[Edge2D], g2d: Graphics2D) => { /** Do nothing **/ })
        vis.start
//    graph.doLayout()
    val t1 = System.nanoTime : Double
    //    var t1 = System.currentTimeMillis()
    println("The calculation time is:")
    println((t1-t0)/1000000000)
    //    println("The final nodes results are:")
    //    println(nodes)

    println("Start building index for node size and node colors. ")

    val StdNodeSize=scala.math.sqrt(0.1/IndexofNode.length)
    val IndexofSize = new ArrayBuffer[Double]()

    val FrequencyofNodesID=ReadingNodeAndEdgeCSVfile.GetNodeFrequency(ObjectArrayEdge,sc)

    for (i <-0 until nodes.length){
      if(FrequencyofNodesID.contains(nodes(i).id)){
        val FrequencyofNode=FrequencyofNodesID.apply(nodes(i).id).toDouble
        val PerofNode=FrequencyofNode/(productedge*2.toDouble)
        IndexofSize+=PerofNode*StdNodeSize
      }
      else{
        IndexofSize+=0
      }
    }
    val t2 = System.nanoTime : Double
    println("Finishing adjustment in node size within:"+(t2-t1)/1000000000+" s.")
    println("Start Writing to CSV files.")
    val fileWriter=new FileWriter("/root/compass_layout_processor/output//layout.csv")
    for (i <-0 until nodes.length){
      fileWriter.write(nodes(i).id+","+nodes(i).group+","+nodes(i).state.pos.x.toString+","+nodes(i).state.pos.y.toString+","+"0"+","+IndexofSize(i)+","+"\n")
      //      fileWriter.write(nodes(i).id+","+nodes(i).group+","+nodes(i).state.pos.x.toString+","+nodes(i).state.pos.y.toString+","+"0"+","+(ObjectArrayEdge.toList.count(_==nodes(i).id).toDouble/(productedge*2))*scala.math.sqrt(0.1/IndexofNode.length)+","+"\n")

    }

    fileWriter.flush()
    fileWriter.close()
    println("CSV files already.")
    sc.stop()
  }
}

