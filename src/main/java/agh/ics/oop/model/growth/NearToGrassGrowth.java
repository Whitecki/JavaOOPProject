package agh.ics.oop.model.growth;

import agh.ics.oop.model.map.Grass;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.map.Map;

import java.util.ArrayList;

public class NearToGrassGrowth implements GrowthStrategy {
    private ArrayList<Vector2D> nwm = new ArrayList<>();

    @Override
    public void growPlants(Map map) {
        for (int i = map.getMinX(); i <= map.getMaxX(); i++) {
            for (int j = map.getMinY(); j <= map.getMaxY(); j++) {
                if (!map.getGrassHashMap().containsKey(new Vector2D(i, j))) {
                    Vector2D vector2D = new Vector2D(i, j);
                    nwm.add(vector2D);
                    if (isPlacePreferedToGrowth(map, vector2D)) {
                        nwm.add(vector2D);
                        nwm.add(vector2D);
                        nwm.add(vector2D);
                    }
                }
            }
        }
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(map, nwm);
        for (Vector2D grassPosition : randomPositionGenerator) {
            map.addGrass(grassPosition);
        }
    }

    // uustalamy funkcje która definiuje które pola trzeba zapisać poczwórnie
    // generator zwraca nam iterator wybranych pól
    // wszystkie wybrane trzeba posadzić blisko
    @Override
    public boolean isPlacePreferedToGrowth(Map map, Vector2D vector2D) {
        int cnt = 0;
        for (int i = vector2D.getX() - 1; i <= vector2D.getX() + 1; i++) {
            for (int j = vector2D.getY() - 1; j <= vector2D.getY() + 1; j++) {
                // nie musimy dodawać warunku, czy wyszliśmy poza mapę, bo tam i tak nie ma trawy :)))
                if (i != vector2D.getX() && j != vector2D.getY() && map.getGrassHashMap().containsKey(new Vector2D(i, j))) {
                    cnt++;
                }
            }
        }
        return cnt > 0;
    }
}
