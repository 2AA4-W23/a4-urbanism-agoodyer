package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Node;


/*
 * A contract defined for finding a path between two nodes 
 * 
 */
public interface ShortestPath {

    public List<Edge> shortestPath(Graph g, Node start, Node end); 

    
}

