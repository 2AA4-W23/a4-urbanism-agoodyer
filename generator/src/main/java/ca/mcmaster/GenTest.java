package ca.mcmaster;

import java.util.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;



import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

public class GenTest {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 40;
    private final int offset = 15;

    public List<Vertex> vertices = new ArrayList<Vertex>();
    public List<Segment> segments = new ArrayList<Segment>();
    public List<Polygon> polygons = new ArrayList<Polygon>();  

    public Mesh generate() {

    
        
        
        List<Coordinate> coords = populatePoints(width, height, square_size, offset); //generate grid points with a random x,y offset

       voronoi(coords);
       lloyd(4);

        //OPTIONAL: Filters all polygons whose centers are outside canvas area
        //filterPolygons(polygons,vertices );

       //trimVertexPosition();
        //create final mesh
        return Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).addAllPolygons(polygons).build();
    }


    /**
     * 
     * @param width  width of point generation area
     * @param height  height of point generation area
     * @param square_size frequency of grid points
     * @param offset deviation from absolute grid position
     * @return list of x,y coordinates
     */
    public static List<Coordinate> populatePoints(int width, int height, int square_size, double offset) {

        List<Coordinate> coordinates = new ArrayList<Coordinate>();

        //create a grid bound by width,height, and divided by square_size
       for (int x = 0; x < width; x += square_size) {
           for (int y = 0; y < height; y += square_size) {

                    //offset grid points by += offset value 
                   double xPos = offset*(Math.random()*2-1) + x; 
                   double yPos = offset*(Math.random()*2-1) + y; 

                   //add point to coordinate list
                   coordinates.add(new Coordinate(xPos,yPos)); 
           }
       }
       return coordinates; 

   }


   /**
    * Creates vertex object from coordinate
    * @param c source coordinate
    * @return Vertex with coordinate x,y data
    */
    public static Vertex createVertex(Coordinate c){
        return Vertex.newBuilder().setX(c.x).setY(c.y).build(); 
    }

    /**
     * Add random color to a vertex
     * @param v source vertex
     * @return randomly colored vertex
     */
    public static Vertex randomized(Vertex v){
        Vertex modified = Vertex.newBuilder(v).addProperties(randomColor()).build(); 
        return modified; 
    }


    /**
     * @return random color property
     */
    public static Property randomColor(){

        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        int alpha = 255; 

        
        
        String colorCode = String.format("%d,%d,%d,%d",red,green,blue,alpha); 
        return Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

    }



        /**
     * Used to determine the average color between two vertices in a mesh
     * @param v1 a colored Vertex
     * @param v2 a colored Vertex
     * @return the average color between the two vertices
     */
    public static Property averageColor(Vertex v1, Vertex v2){

        //Initialize default color to black. Attempt to find color in vertex properties
        String val1="0,0,0";
        for(Property p : v1.getPropertiesList()){
            if (p.getKey().equals("rgb_color"))  val1 = p.getValue();
        }

        String val2="0,0,0";
        for(Property p : v2.getPropertiesList()){
            if (p.getKey().equals("rgb_color")) val2 = p.getValue();
        }

        //parse numeric data from color string, compute average values
        String[] s1 = val1.split(",");
        String[] s2 = val2.split(",");
        int rgba[] = new int[4];
        for(int i=0;i<3;i++) rgba[i] = (Integer.parseInt(s1[i]) + Integer.parseInt(s2[i]))/2;

        //identifies if the both color strings have an alpha value. If absent, specify default of 255 (No transparency)
        rgba[3] = ((s1.length==4 ? Integer.parseInt(s1[3]) : 255 ) + (s2.length==4 ? Integer.parseInt(s2[3]) : 255 ))/2 ;

        //rebuild string with new average
        String colorCode = rgba[0]+","+rgba[1]+","+rgba[2] + "," + rgba[3];

        return Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
    }


    public  void filterPolygons(List<Polygon> polygons ){
        //iterate over all polygons backwards
        for (int i = polygons.size()-1; i >=0 ; i--) {
          
            Polygon p = polygons.get(i); 
            Vertex centroid = vertices.get(p.getCentroidIdx()); //get the centroid vertex from each polygon 
            
            if(centroid.getX()>width || centroid.getX()<0 || centroid.getY()>height || centroid.getY()<0 ){
                polygons.remove(i);  //filter polygons outside of canvas dimensions
            } 
        }
    }


    /**
     * Move all off-canvas vertices to canvas edge
     */
    public void trimVertexPosition(){

        for(int i=0; i<vertices.size();i++){
            Vertex v = vertices.get(i); 

            List<Property> properties = v.getPropertiesList(); 

            if (v.getX() > width || v.getY() > height){

                  //compute new x,y values 
                double x = (v.getX() > width) ? width : v.getX();
                double y = (v.getY() > height) ? height : v.getY(); 

                //replace current vertex with new one on canvas edge
                vertices.set(i, Vertex.newBuilder().setX(x).setY(y).addAllProperties(properties).build()); 
            }
        }
    }



    public List<Coordinate> getCentroids(List<Polygon>polygons){

        List<Coordinate> centroids = new ArrayList<Coordinate>(); 


        for(Polygon p: polygons){

            Vertex v = vertices.get(p.getCentroidIdx()); 
            Coordinate c = new Coordinate(v.getX(), v.getY()); 
            centroids.add(c); 
        }

        return centroids; 
    }



    public void voronoi(List<Coordinate> coords){

        vertices.clear();
        segments.clear(); 
        polygons.clear();


        VoronoiDiagramBuilder voronoi = new VoronoiDiagramBuilder(); 
        voronoi.setSites(coords); //perform voronoi calculations for generated points
        
        GeometryFactory factory = new GeometryFactory(); 
       Geometry rawVoronoiGeometry = voronoi.getDiagram(factory); //convert voronoi diagram into Geometry object 

        List<Geometry> voronoiCells = new ArrayList<Geometry>(); //store individual voronoi cells

        //iterate over each voronoi cell contained within parent geometry
        for(int i=0; i<rawVoronoiGeometry.getNumGeometries();  i++){
            voronoiCells.add(rawVoronoiGeometry.getGeometryN(i)); 
        }
        

        //convert voronoi geometry into Polygons 
        for(Geometry cell : voronoiCells){
            
            //store number of vertices,segments to make index operations simple and relative to current polygon 
            int vertexOffset=vertices.size(); 
            int segmentOffset=segments.size(); 

            List<Integer> segId = new ArrayList<Integer>(); 
            Coordinate[] cellCoords = cell.getCoordinates(); //get coordinate x,y pairs 
            
            for(int i=0; i<cellCoords.length;i++){
                vertices.add(createVertex(cellCoords[i])); //create vertex from x,y data
                
                //prevent OOB error by skipping the last vertex 
                if(i!= cellCoords.length-1){ 
                    segId.add(i+segmentOffset);  
                    segments.add(Segment.newBuilder().setV1Idx(i+vertexOffset).setV2Idx(i+1+vertexOffset).build()); 
                }
            }
            

            //get polygon centroid location and create vertex with randomized color
            Vertex centroid = randomized(createVertex(cell.getCentroid().getCoordinate())); 
            vertices.add(centroid); 

            //create polgon with segment data extracted from cell
            Polygon p = Polygon.newBuilder().addAllSegmentIdxs(segId).setCentroidIdx(vertices.size()-1).build();  
            polygons.add(p); 


        }


    }


    public void lloyd( int iterations){


        for (int i = 0; i < iterations; i++) {
            
            List<Coordinate> coords = getCentroids(polygons); 

            voronoi(coords);


        }

    }




}
