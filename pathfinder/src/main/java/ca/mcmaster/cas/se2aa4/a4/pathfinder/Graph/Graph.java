
package ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph<T> {


    List<Edge<T>> edges; 

    private int vertexCount;
    private int edgeCount; 


    Map<Node<T>, List<Edge<T> >> nodes; 


    public Graph(){

      nodes = new HashMap<Node<T>, List<Edge<T> >>();   

    }



    public void addNode(Node<T> n){
     if(!nodes.containsKey(n)) nodes.put(n, new LinkedList<Edge<T> >()); 
    }

    public void addEdge(Node<T> n, Edge<T> e){

    if(!nodes.containsKey(n)) throw new IllegalArgumentException("Source node does not exist.");
    if(!nodes.containsKey(e.getDestinationNode())) throw new IllegalArgumentException("Destination node does not exist.");

     List<Edge<T>> edges =  nodes.get(n); 
     edges.add(e); 

    }


    public String toString(){
        String s=""; 

        for(Node n : nodes.keySet()){
            s+=n.getData() + ": { "; 
          
            for(Edge e : nodes.get(n)){
                s+= " " + e.getDestinationNode().getData(); 
            }


            s+=" } \n"; 
        }

        return s; 

    }

 

    


}
