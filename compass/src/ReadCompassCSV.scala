package at.ait.dme.forcelayout.examples


import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.collection.mutable
import javax.swing.JFrame
import java.awt.{BasicStroke, Color, Dimension, Graphics2D}
import java.awt.geom.Ellipse2D

import at.ait.dme.forcelayout.{Edge, Node, SpringGraph}
import at.ait.dme.forcelayout.renderer.{BufferedInteractiveGraphRenderer, ColorPalette, Edge2D, Node2D}
/**
  * Created by otto on 10/28/16.
  */
object ReadCompassCSV {
  class reader() {
    def read(path: String): scala.io.BufferedSource = Source.fromFile(path)

    def data(s: scala.io.BufferedSource) = {
      val ObjectArray = new ArrayBuffer[String]()
      //val attributes=new ArrayBuffer[String] ()
      //val len=s.length
      //ObjectArray+= len.toString
      for (line <- s.getLines) {
        val temp = Array[String](line)
        val cols = line.split(",").map(_.trim)
        //println(cols.length)
        ObjectArray ++= cols
        val len = cols.length
        if (len<2){
          ObjectArray +="NoLabel"
        }
        //ObjectArray += len.toString
      }
      //ObjectArray+= len.toString
      ObjectArray

    }

    def run(path: String) = data(read(path))
  }
  def main(args: Array[String]){
    val conf = new SparkConf()
    conf.setAppName("world")
    conf.setMaster("local")
    val sc = new SparkContext(conf)
    val readnode = new reader()
    val pathnode = "/home/otto/IdeaProjects/compass/src/test/resources/examples/node.csv"
    val CSVdata = readnode.run(pathnode)
    val productnode = CSVdata.length/2
    print(productnode)
    print('\n')
//    print(CSVdata)
    var nodes=Seq[Node]()
    val IndexofNode=new ArrayBuffer[String] ()
    for (i<-1 until productnode){
      nodes=nodes:+Node(CSVdata(2*i), CSVdata(2*i),1.0,CSVdata(2*i+1).length)
      IndexofNode+=CSVdata(2*i)
    }
   print(nodes(2))
    print("\n")
    val readedge = new reader()
    val pathedge = "/home/otto/IdeaProjects/compass/src/test/resources/examples/edge.csv"
    val CSVdataedge = readedge.run(pathedge)
    val productedge = CSVdataedge.length/3
    print(CSVdataedge.length)
    print('\n')
//     print(CSVdataedge)
    var edges=Seq[Edge]()
    for (i<-1 until productedge) yield
      {
        if(IndexofNode.contains(CSVdataedge(3*i))){
          edges=edges:+Edge(nodes(IndexofNode.indexOf(CSVdataedge(3*i))), nodes(IndexofNode.indexOf(CSVdataedge(3*i+1))), 1.0)
        }
//      edges=edges:+Edge(nodes.(CSVdataedge(3*i).toInt), nodes(CSVdataedge(3*i+1).toInt), 1.0)

         }

//    print(edges(2))

    val t0 = System.nanoTime() : Double
//    var t0 = System.currentTimeMillis()
    val graph = new SpringGraph(nodes, edges)

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

    val t1 = System.nanoTime : Double
//    var t1 = System.currentTimeMillis()
    print((t1-t0)/1000000000)


  }
}
