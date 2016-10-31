package at.ait.dme.forcelayout.examples

import java.awt.Dimension
import javax.swing.{ JFrame, JLabel, ImageIcon }

import at.ait.dme.forcelayout.renderer.ImageRenderer
import at.ait.dme.forcelayout.{ Node, Edge, SpringGraph }

object HelloWorld extends App {
  
  val nodes = Seq(
      Node("A", "Node A"),
      Node("B", "Node B"),
      Node("C", "Node C"),
      Node("D", "Node D"),
      Node("E", "Node E"),
      Node("F", "Node F"),
      Node("G", "Node G"),
      Node("H", "Node h"))
  val edges = Seq(
      Edge(nodes(0), nodes(1)),
      Edge(nodes(1), nodes(2)),
      Edge(nodes(2), nodes(3)),
      Edge(nodes(1), nodes(3)),
      Edge(nodes(3), nodes(4)),
      Edge(nodes(1), nodes(4)),
      Edge(nodes(0), nodes(6)),
      Edge(nodes(1), nodes(7)),
      Edge(nodes(3), nodes(5)),
      Edge(nodes(1), nodes(5)),
      Edge(nodes(3), nodes(7)),
      Edge(nodes(1), nodes(2)),
      Edge(nodes(1), nodes(6)),
      Edge(nodes(3), nodes(5)),
      Edge(nodes(3), nodes(7)),
      Edge(nodes(0), nodes(2)),
      Edge(nodes(0), nodes(3)))
  val graph = new SpringGraph(nodes, edges) 
   
  val frame = new JFrame()
  frame.setPreferredSize(new Dimension(500,500))
  val imgIcon = new ImageIcon()
  val imgLabel = new JLabel(imgIcon)
  frame.add(imgLabel)
  frame.pack();
  frame.setVisible(true);
  
  graph.doLayout(
      onComplete = (it => println("completed in " + it + " iterations")),
      onIteration = (it => imgLabel.setIcon(new ImageIcon(ImageRenderer.drawGraph(graph, 500, 500)))))

}

