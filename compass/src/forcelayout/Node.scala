package at.ait.dme.forcelayout





/**
 * A container for the (mutable) force simulation state of a graph node. 
 * @author Rainer Simon <rainer.simon@ait.ac.at>
 */
case class NodeState(var pos: Vector2D = Vector2D.random(1.0), var velocity: Vector2D = Vector2D(0, 0), var force: Vector2D = Vector2D(0, 0))  
