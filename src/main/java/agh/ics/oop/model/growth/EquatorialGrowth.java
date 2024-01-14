package agh.ics.oop.model.growth;

import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.map.Map;

public class EquatorialGrowth implements GrowthStrategy {
    private int equatorTop;
    private int equatorBottom;
    @Override
    public void growPlants(Map map) {
        this.equatorTop = (int) (map.getMaxY() - map.getMinY()) * 6/10;
        this.equatorBottom = (int) (map.getMaxY() - map.getMinY()) * 4/10;
    }
    // uustalamy funkcje która definiuje które pola trzeba zapsać poczwórnie
    // generator zwraca nam iterator wybranych pól
    // wszystkie wybrane trzeba posadzić blisko
    public boolean isPlacePreferedToGrowth(Map map, Vector2D vector2D){
        return vector2D.getY() <= equatorTop && vector2D.getY()  >= equatorBottom;
    }
}
