package ca.mcmaster.cas.se2aa4.a3.island.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Dijkstra;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Node;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Edge;
public class GraphConverter {
    

    Graph graph;
    Mesh mesh; 
    List<Node> nodes; 
    public GraphConverter(Mesh mesh){

    this.mesh = mesh; 
      graph = new Graph(); 

    
         nodes = new ArrayList<Node>();

        for (int i = 0; i < mesh.getVerticesCount(); i++) {

            nodes.add(new Node(i)); 
            graph.addNode(nodes.get(i));
        }



        List<Segment> segments = mesh.getSegmentsList(); 
        for(int i=0; i<segments.size(); i++){

            Segment s = segments.get(i); 

            Vertex v1 =  mesh.getVertices(s.getV1Idx()); 
            Vertex v2 =  mesh.getVertices(s.getV2Idx()); 

            double length = distance(v1.getX(), v1.getY(), v2.getX(), v2.getY()); 


           graph.addUndirected( nodes.get(s.getV1Idx()), nodes.get(s.getV2Idx()), length, i);
        }
     

        System.out.println(graph.toString());

        

        Node n1=nodes.get(0); 

        for(Node n : nodes){
            n1= n; 
            if(graph.getEdgesOfNode(n).size() >0){
               break; 
            }

        }

        Node n2=nodes.get(0); 
        for(Node n : nodes){
            n2= n; 
            if(graph.getEdgesOfNode(n).size() >0 && !n2.equals(n1)){
               break; 
            }

        }




        Dijkstra d = new Dijkstra(); 


       List<Edge> path =  d.shortestPath(graph, n1, n2); 

      
       for(Edge e: path){
           Segment s =  segments.get(e.getId()); 
       }

       
        // System.out.println(graph);
        
    }

    public List<Node> getNodes(){
        return this.nodes; 
    }


    public Mesh newMesh(){


        Dijkstra d = new Dijkstra(); 

        Node n1=nodes.get(0); 

        for(Node n : nodes){
            n1= n; 
            if(graph.getEdgesOfNode(n).size() >0){
               break; 
            }

        }

        Node n2=nodes.get(0); 
        for(Node n : nodes){
            n2= n; 
            if(graph.getEdgesOfNode(n).size() >0 && !n2.equals(n1)){
               break; 
            }

        }


        List<Edge> path =  d.shortestPath(graph, n1, n2); 

        List<Integer> idxs = new ArrayList<Integer>(); 
        for(Edge e: path){
            idxs.add(e.getId()); 
        }

        List<Segment> oldSegments  = mesh.getSegmentsList(); 
        List<Segment> segments  = new ArrayList<Segment>(); 

        
      

        for(int i=0; i<oldSegments.size(); i++){


            Property road = Property.newBuilder().setKey("IsRoad").setValue("True").build();
            Property color = Property.newBuilder().setKey("rgb_color").setValue("255,255,0").build();
            
            if(idxs.contains(i)) color = Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();



            Segment s = Segment.newBuilder(oldSegments.get(i)).addAllProperties(oldSegments.get(i).getPropertiesList()).build();  

            if(idxs.contains(i)){
             s = Segment.newBuilder(oldSegments.get(i)).addProperties(color).addProperties(road).build();
            }
            segments.add(s); 
        }



        return Mesh.newBuilder().addAllVertices(mesh.getVerticesList()).addAllSegments(segments).addAllPolygons(mesh.getPolygonsList()).build(); 
        
    }


    public Graph getGraph(){
        return graph; 
    }


    public static double distance(double x1, double y1, double x2, double y2){
        return Math.sqrt( Math.pow((x2-x1),2) + Math.pow((y2-y1),2)); 
    }



}
