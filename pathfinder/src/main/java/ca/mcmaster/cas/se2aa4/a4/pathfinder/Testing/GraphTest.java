package ca.mcmaster.cas.se2aa4.a4.pathfinder.Testing;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.asserts.Assertion;
import org.testng.internal.junit.ArrayAsserts;

import com.google.protobuf.DescriptorProtos.ExtensionRangeOptionsOrBuilder;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Dijkstra;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Node;

public class GraphTest {





    private void NodeInsertionTest(){

        Graph g = new Graph(); 

        System.out.println("Testing Node insertion");
        g.addNode(new Node(0));        
        Assert.assertEquals(g.getNodeCount(), 1);
        System.out.println("Test passed!");
    }


    public void EdgeInsertionTest(){
        Graph g = new Graph(); 

        System.out.println("Testing Edge insertion");

        Node n1 = new Node(0); 
        Node n2 = new Node(1);
        g.addNode(n1);        
        g.addNode(n2);

        g.addEdge(n1, new Edge(n2, 1, 0));
        Assert.assertEquals(g.getEdgeCount(), 1);
        System.out.println("Test passed!");
    }




    public void EdgeRetrievalTest(){


        Graph g = new Graph(); 

        System.out.println("Testing Edge retrieval");

        Node n1 = new Node(0); 
        Node n2 = new Node(1);
        Node n3 = new Node(2); 
        g.addNode(n1);        
        g.addNode(n2);
        g.addNode(n3);

        g.addEdge(n1, new Edge(n2, 1, 0));
        g.addEdge(n1, new Edge(n3, 1,1));


        Assert.assertEquals( g.getEdgesOfNode(n1).size(),2);
        System.out.println("Test passed!");
       

    }



    public void ShortestPathTest(){

        List<Node> nodes = new ArrayList<Node>(); 
        
        Graph g = new Graph(); 
        nodes.add(new Node(0)); 
        nodes.add(new Node(1)); 
        nodes.add(new Node(2)); 
        nodes.add(new Node(3)); 
        nodes.add(new Node(4)); 
      

        for(Node n : nodes){
            g.addNode(n);
        }

      

        g.addUndirected(nodes.get(0), nodes.get(1), 1.2, 1);
        g.addUndirected(nodes.get(1), nodes.get(2), 2.1, 2);
        g.addUndirected(nodes.get(2), nodes.get(3), 1.6, 3);
        g.addUndirected(nodes.get(1), nodes.get(3),2.4, 4);
        g.addUndirected(nodes.get(2), nodes.get(4), 1.5, 5);
        g.addUndirected(nodes.get(3), nodes.get(4), 1.0, 6);


        Dijkstra d = new Dijkstra(); 

       List<Edge> path = d.shortestPath(g, nodes.get(0), nodes.get(4));
       
       double pathLength=0; 
       for(Edge e : path){
            pathLength += e.getWeight(); 
       }


       System.out.println("Testing Shortest Path Correctness");
       Assert.assertEquals(pathLength, 4.6, 0.01);

       System.out.println("Test passed!");

       
    }


    public void TestSuite(){
        NodeInsertionTest();
        EdgeInsertionTest();
        EdgeRetrievalTest(); 
        ShortestPathTest();
    }

    
    
}
