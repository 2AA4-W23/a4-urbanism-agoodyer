
package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Node;

public class Dijkstra implements ShortestPath {

    public List<Edge> shortestPath(Graph g, Node start, Node end) {

        g.resetNodes(); // Set cost to infinity, path to null

        Comparator<Node> compareCost = new Comparator<Node>() {

            @Override
            public int compare(Node n1, Node n2) {
                return Double.compare(n1.getCost(), n2.getCost());
            }
        };

        PriorityQueue<Node> queue = new PriorityQueue<Node>(compareCost);

        queue.add(start);
        start.setCost(0);
        start.setPath(start);

        while (!queue.isEmpty()) {

            Node m = queue.poll();

            List<Edge> edges = g.getEdgesOfNode(m);

            for (Edge edge : edges) {

                if ((m.getCost() + edge.getWeight()) < edge.getDestinationNode().getCost()) {

                    double newCost = (m.getCost() + edge.getWeight());
                    edge.getDestinationNode().setCost(newCost);

                    edge.getDestinationNode().setPath(m);

                    queue.add(edge.getDestinationNode());
                }

            }

        }

        // get specific path

        Node current = end;
        List<Node> thePath = new ArrayList<Node>();

        while (current != start) {

            thePath.add(current);
            current = current.getPath();
        }
        thePath.add(current);

        System.out.println(thePath.toString());

        return new ArrayList<Edge>();
    }

}
