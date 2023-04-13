
package ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph;

public class Edge {

    private double weight;

    private int id; 

    Node destination;

    public Edge(Node node, double weight, int id) {
        this.destination = node;
        this.weight = weight;
        this.id = id; 

    }

    public Node getDestinationNode() {
        return this.destination;
    }

    public double getWeight() {
        return this.weight;
    }


    public int getId(){
        return this.id; 
    }
}
