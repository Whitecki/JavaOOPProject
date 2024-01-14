package agh.ics.oop.model.edge;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Vector2D;

import java.util.HashMap;

public class GlobeEdgeBehavior implements EdgeBehavior {

    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;

    public GlobeEdgeBehavior(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public void handleEdgeCrossing(Animal animal) {
        if (animal.nextMove().getY() > maxY || animal.nextMove().getY() < minY){
            animal.setDirection(animal.getDirection().opposite());
        }
        if (animal.nextMove().getX() > maxX) {
            animal.setPosition(new Vector2D(minX,animal.getPosition().getY()));
        } else if (animal.nextMove().getX() < minY) {
            animal.setPosition(new Vector2D(maxX,animal.getPosition().getY()));
        }
    }

    @Override
    public boolean isSpecialMove(Vector2D position) {
        return position.getX() > maxX || position.getX() < minX || position.getY() < minY || position.getY() > maxY;
    }
}
