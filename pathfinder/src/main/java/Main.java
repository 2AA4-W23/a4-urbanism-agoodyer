import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Node;

public class Main {
    
    public static void main(String[] args){
      


        Graph<Integer> g = new Graph<Integer>();
        

        List<Node<Integer>> nodes = new ArrayList<Node<Integer>>(); 

        for(int i=0; i<10;i++){
            nodes.add(new Node<Integer>(i)); 
            g.addNode(nodes.get(i));
            if(i>0) g.addEdge(nodes.get(i-1), new Edge<Integer>(nodes.get(i), 1));
        }



      

        System.out.println(g.toString());



    }

  



}
