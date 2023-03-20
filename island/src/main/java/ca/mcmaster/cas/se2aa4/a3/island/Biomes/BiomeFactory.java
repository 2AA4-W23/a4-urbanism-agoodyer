package ca.mcmaster.cas.se2aa4.a3.island.Biomes;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Extractor;
import ca.mcmaster.cas.se2aa4.a3.island.MeshAttributes.Elevation;
import ca.mcmaster.cas.se2aa4.a3.island.MeshAttributes.Humidity;
import ca.mcmaster.cas.se2aa4.a3.island.MeshAttributes.Tiles;
import java.util.ArrayList;
import java.util.List;

public class BiomeFactory {
    public Structs.Mesh BiomeSetter(Structs.Mesh aMesh){
        Whittaker w = new Whittaker();
        List<Structs.Polygon> polysNew = new ArrayList<>();
        for (Structs.Polygon p: aMesh.getPolygonsList()){
            Structs.Polygon newTile = p;
            String biome;
            try{
                biome = w.evaluateBiome(Extractor.getPolyHumidity(p),Extractor.getPolyElevation(p));
            }catch (Exception e){
                biome = "Land";
            }
            if (biome.equals("tropical")){
                newTile = Tiles.setType(newTile, Tiles.TileType.TROPICAL);
            }else if (biome.equals("tundra")){
                newTile = Tiles.setType(newTile, Tiles.TileType.TROPICALDESERT);
            }
            polysNew.add(newTile);
        }
        return Structs.Mesh.newBuilder().addAllVertices(aMesh.getVerticesList()).addAllSegments(aMesh.getSegmentsList()).addAllPolygons(polysNew).build();
    }

    public  boolean isTropTile(Structs.Mesh aMesh,Structs.Polygon p){

        if(Tiles.getTileType(p).equals("Water")) return false;
        if(Tiles.getTileType(p).equals("Lagoon")) return false;
        if(Tiles.getTileType(p).equals("Beach")) return false;
        for (int i: p.getNeighborIdxsList()){


            String type = Tiles.getTileType(aMesh.getPolygonsList().get(i));


            if (type.equals("Lake")){
                return true;
            }
        }
        return false;
    }
}
