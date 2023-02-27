package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.geom.Ellipse2D;
import java.awt.geom.*;

import java.util.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GraphicRenderer {



    public void render(Mesh aMesh, Graphics2D canvas) {

        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        //CLIPS EXCESS - vertices still exist
        // canvas.setClip(0, 0, 500, 500);


        ArrayList<Segment> segmentswithcolour = new ArrayList<>();
        for (Segment s:aMesh.getSegmentsList()){
            segmentswithcolour.add(s);
        }
        int polyCount=0;
        for (Polygon p : aMesh.getPolygonsList()){


            int[][] coords = getPolygonCoords(p, aMesh);
            canvas.setColor(extractColor(aMesh.getPolygonsList().get(polyCount).getPropertiesList()));

            //THIS PART IS FOR TESTING IF THE NEIGHBOUR IDS ARE CORRECT, DOES NOT ACTUALLY DO ANYTHING RELEVANT
            //List<Integer> neighbour = aMesh.getPolygonsList().get(55).getNeighborIdxsList();
            //if(polyCount==55) {
            //    canvas.setColor(Color.BLACK);
            //}
            //for (int n:neighbour){
            //    if (polyCount==n) canvas.setColor(Color.RED);
            //}
            canvas.fillPolygon(coords[0], coords[1], coords[0].length);


            for(int id_s : p.getSegmentIdxsList()){

                Segment s = aMesh.getSegments(id_s);

                canvas.setColor(extractColor(s.getPropertiesList()));
                int[] point1 = {(int)aMesh.getVertices(s.getV1Idx()).getX(),(int)aMesh.getVertices(s.getV1Idx()).getY()};
                int[] point2 = {(int)aMesh.getVertices(s.getV2Idx()).getX(),(int)aMesh.getVertices(s.getV2Idx()).getY()};
                canvas.setStroke(new BasicStroke(extractThickness(s.getPropertiesList())));
                canvas.drawLine(point1[0],point1[1],point2[0],point2[1]);


            }


            polyCount+=1;
        }

         for (Segment s : aMesh.getSegmentsList() ){


             // render segment on canvas
              canvas.setColor(extractColor(s.getPropertiesList()));
              int[] point1 = {(int)aMesh.getVertices(s.getV1Idx()).getX(),(int)aMesh.getVertices(s.getV1Idx()).getY()};
              int[] point2 = {(int)aMesh.getVertices(s.getV2Idx()).getX(),(int)aMesh.getVertices(s.getV2Idx()).getY()};
              canvas.setStroke(new BasicStroke(extractThickness(s.getPropertiesList())));
              canvas.drawLine(point1[0],point1[1],point2[0],point2[1]);
          }


        int vertex_point=0;
        for(Vertex v : aMesh.getVerticesList()){

            int thickness = extractThickness(v.getPropertiesList());
            //render vertex on canvas
            double centre_x = v.getX() - (thickness/2.0d);
            double centre_y = v.getY() - (thickness/2.0d);
            canvas.setColor(extractColor(v.getPropertiesList()));
            //canvas.setColor(new Color(0,0,0));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y,thickness,thickness);
            canvas.fill(point);
            vertex_point+=1;
        }

    }



    private int[][] getPolygonCoords(Polygon p, Mesh m){

        List<Integer> segmentList = p.getSegmentIdxsList();


        int[][] loc = new int[2][2*p.getSegmentIdxsCount()];


        for (int i=0; i<p.getSegmentIdxsCount(); i++){

            Segment s = m.getSegments(p.getSegmentIdxs(i));

            Vertex v1 = m.getVertices(s.getV1Idx());
            Vertex v2 = m.getVertices(s.getV2Idx());


            loc[0][i]= (int)v1.getX();
            loc[0][i+1] = (int)v2.getX();

            loc[1][i]= (int)v1.getY();
            loc[1][i+1] = (int)v2.getY();



        }

        return loc;




    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);


        //extract alpha value if present in color string
        if(raw.length>3){
            int alpha = Integer.parseInt(raw[3]);
            return new Color(red, green, blue, alpha);
        }

        return new Color(red, green, blue);
    }


    private int extractThickness(List<Property> properties){

        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("thickness")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return 3;

        else return Integer.parseInt(val);
    }







}
