import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Dijkstra;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.ShortestPath;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Node;

public class Main {
    

    public static void main(String[] args){
      


        Graph g = new Graph();

        List<Node> nodes = new ArrayList<Node>(); 

        // for(int i=0; i<10;i++){
          

        //     nodes.add(new Node(i)); 
        //     g.addNode(nodes.get(i));


        //     if(i>0) g.addUndirected(nodes.get(i), nodes.get(i-1), 1.0);

        // }


     
        
        // g.addUndirected(nodes.get(3), nodes.get(0), 0);





        nodes.add(new Node(0)); 
        nodes.add(new Node(1)); 
        nodes.add(new Node(2)); 
        nodes.add(new Node(3)); 
        nodes.add(new Node(4)); 
      

        for(Node n : nodes){
            g.addNode(n);
        }

        //g.addUndirected(nodes.get(0), nodes.get(1), 0);

        g.addUndirected(nodes.get(0), nodes.get(1), 1.2);
        g.addUndirected(nodes.get(1), nodes.get(2), 2.1);
        g.addUndirected(nodes.get(2), nodes.get(3), 1.6);
        g.addUndirected(nodes.get(1), nodes.get(3),2.4);
        g.addUndirected(nodes.get(2), nodes.get(4), 1.5);
        g.addUndirected(nodes.get(3), nodes.get(4), 1.0);


        Dijkstra d = new Dijkstra(); 


        d. shortestPath(g, nodes.get(0), nodes.get(4)); 

       

        System.out.println(g.toString());



    }

  



}
