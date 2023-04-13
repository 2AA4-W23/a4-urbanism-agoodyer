package ca.mcmaster.cas.se2aa4.a3.island.Graph;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a3.island.MeshAttributes.Tiles;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Dijkstra;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.ShortestPath;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Graph.Node;

public class RoadNetwork {

    public Mesh roadNetwork(Mesh mesh) {

        CityGenerator cityGenerator = new CityGenerator(mesh);
        List<Integer> cities = cityGenerator.generateCities(8);

        GraphConverter graphConverter = new GraphConverter(mesh);

        Graph graph = graphConverter.getGraph();

        List<Node> nodes = graphConverter.getNodes();

        List<Integer> idxs = new ArrayList<Integer>();

        for (int i = 1; i < cities.size(); i++) {

            ShortestPath pathMaker = new Dijkstra();

            List<Edge> path = pathMaker.shortestPath(graph, nodes.get(cities.get(0)), nodes.get(cities.get(i)));

            for (Edge e : path) {
                idxs.add(e.getId());
            }
        }

        List<Segment> oldSegments = mesh.getSegmentsList();
        List<Segment> segments = new ArrayList<Segment>();

        for (int i = 0; i < oldSegments.size(); i++) {

            Property road = Property.newBuilder().setKey("IsRoad").setValue("True").build();
            Property color = Property.newBuilder().setKey("rgb_color").setValue("255,255,0").build();

            if (idxs.contains(i))
                color = Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();

            Segment s = Segment.newBuilder(oldSegments.get(i)).addAllProperties(oldSegments.get(i).getPropertiesList())
                    .build();

            if (idxs.contains(i)) {
                s = Segment.newBuilder(oldSegments.get(i)).addProperties(color).addProperties(road).build();
            }
            segments.add(s);
        }

        return Mesh.newBuilder().addAllVertices(mesh.getVerticesList()).addAllSegments(segments)
                .addAllPolygons(mesh.getPolygonsList()).build();

    }

}
