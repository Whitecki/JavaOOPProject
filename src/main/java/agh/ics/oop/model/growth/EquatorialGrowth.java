package agh.ics.oop.model.growth;

import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.map.Map;
import agh.ics.oop.model.until.RandomVector2DGenerator;

import java.util.ArrayList;

public class EquatorialGrowth implements GrowthStrategy {
    private int equatorTop;
    private int equatorBottom;
    private final ArrayList<Vector2D> placesToGrowthGrass = new ArrayList<>();
    @Override
    public void growPlants(Map map,int numberOfGrassToGrowth) {
        this.equatorTop = (map.getMaxY() - map.getMinY()) * 6/10;
        this.equatorBottom = (map.getMaxY() - map.getMinY()) * 4/10;
        for (int i = map.getMinX(); i <= map.getMaxX(); i++) {
            for (int j = map.getMinY(); j <= map.getMaxY(); j++) {
                Vector2D vector2D = new Vector2D(i, j);
                if (!map.getGrassHashMap().containsKey(vector2D)) {
                    placesToGrowthGrass.add(vector2D);
                    if (isPlacePreferedToGrowth(map, vector2D)) {
                        placesToGrowthGrass.add(vector2D);
                        placesToGrowthGrass.add(vector2D);
                        placesToGrowthGrass.add(vector2D);
                    }
                }
            }
        }
        RandomVector2DGenerator randomPositionGenerator = new RandomVector2DGenerator(map, placesToGrowthGrass,numberOfGrassToGrowth);
        for (Vector2D grassPosition : randomPositionGenerator) {
            map.addGrass(grassPosition);
        }
    }
    public boolean isPlacePreferedToGrowth(Map map, Vector2D vector2D){
        return vector2D.getY() <= equatorTop && vector2D.getY()  >= equatorBottom;
    }
}