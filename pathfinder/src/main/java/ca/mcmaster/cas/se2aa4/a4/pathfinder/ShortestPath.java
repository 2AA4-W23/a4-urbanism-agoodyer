package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Node;

public interface ShortestPath {

    public List<Edge> shortestPath(Graph g, Node start, Node end ); 
    
}
