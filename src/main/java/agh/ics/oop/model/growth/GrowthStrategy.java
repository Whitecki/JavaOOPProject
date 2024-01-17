package agh.ics.oop.model.growth;

import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.map.Map;

public interface GrowthStrategy {
    void growPlants(Map map,int numberOfGrassToGrowth);
    boolean isPlacePreferedToGrowth(Map map, Vector2D vector2D);
}
