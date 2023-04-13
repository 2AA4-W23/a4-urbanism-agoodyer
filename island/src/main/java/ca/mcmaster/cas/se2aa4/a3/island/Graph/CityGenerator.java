package ca.mcmaster.cas.se2aa4.a3.island.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a3.island.MeshAttributes.Tiles;

public class CityGenerator {

    List<Polygon> polygons;

    List<Integer> candidates;

    public CityGenerator(Mesh mesh) {

        polygons = mesh.getPolygonsList();

        candidates = getCityCandidates(mesh);

    }

    List<Integer> getCityCandidates(Mesh mesh) {

        List<Integer> locations = new ArrayList<Integer>();

        for (Polygon p : mesh.getPolygonsList()) {

            if (isLandTile(p)) {

                for (Integer idx : p.getSegmentIdxsList()) {

                    Segment s = mesh.getSegments(idx);

                    locations.add(s.getV1Idx());
                    locations.add(s.getV2Idx());

                }

            }

        }

        return locations;

    }

    public List<Integer> generateCities(int n) {

        Random r = new Random();

        List<Integer> cityIdxs = new ArrayList<Integer>();

        for (int i = 0; i < n; i++) {

            cityIdxs.add(candidates.remove(r.nextInt(candidates.size() - 1)));

        }

        return cityIdxs;

    }

    private boolean isLandTile(Polygon p) {

        if (Tiles.getTileType(p).equals("Land"))
            return true;
        return false;

    }

}
