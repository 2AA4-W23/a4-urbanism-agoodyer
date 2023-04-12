
package ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph; 




public class Edge<T> {

private double weight;  

Node<T> destination; 


public Edge(Node<T> node, double weight){

    this.destination = node; 
    this.weight = weight; 
   
}


public Node<T> getDestinationNode(){
    return this.destination; 
}




}
