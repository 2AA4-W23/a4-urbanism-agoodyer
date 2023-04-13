
package ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Graph class implementing an adjacency list based approach to
 * represent the relationship betwee nodes and edges
 * 
 */

public class Graph {

    private int nodeCount;
    private int edgeCount;

    Map<Node, List<Edge>> nodes;

    public Graph() {

        nodeCount = 0;
        edgeCount = 0;

        nodes = new HashMap<Node, List<Edge>>();

    }

    public void addNode(Node n) {
        if (!nodes.containsKey(n)) {

            nodes.put(n, new LinkedList<Edge>());
            nodeCount++;
        }

    }

    public void addEdge(Node n, Edge e) {

        if (!nodes.containsKey(n))
            throw new IllegalArgumentException("Source node does not exist.");
        if (!nodes.containsKey(e.getDestinationNode()))
            throw new IllegalArgumentException("Destination node does not exist.");

        List<Edge> edges = nodes.get(n);

        edgeCount++;
        edges.add(e);

    }

    public List<Edge> getEdgesOfNode(Node n) {
        return nodes.get(n);
    }

    public void resetNodes() {

        for (Node node : nodes.keySet()) {
            node.reset();
        }

    }

    public void addUndirected(Node n1, Node n2, double weight, int id) {

        Edge e2 = new Edge(n2, weight, id);
        Edge e1 = new Edge(n1, weight, id);

        List<Edge> n1Edges = nodes.get(n1);
        List<Edge> n2Edges = nodes.get(n2);

        n1Edges.add(e2);
        n2Edges.add(e1);

        edgeCount += 2;

    }

    public int getNodeCount() {
        return this.nodeCount;
    }

    public int getEdgeCount() {
        return this.edgeCount;
    }

    public String toString() {
        String s = "";

        for (Node n : nodes.keySet()) {
            s += n.getId() + ": { ";

            for (Edge e : nodes.get(n)) {
                s += " [" + e.getDestinationNode().getId() + ": weight:" + e.getWeight() + "] ";

            }

            s += " } \n";
        }

        return s;

    }

}
