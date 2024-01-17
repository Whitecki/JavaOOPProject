package agh.ics.oop.model.edge;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Vector2D;

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
        Vector2D nextMove = animal.nextMove();

        if (nextMove.getY() > maxY || nextMove.getY() < minY) {
            animal.setDirection(animal.getDirection().opposite());
        }
        if (nextMove.getX() > maxX) {
            animal.setPosition(new Vector2D(minX, animal.getPosition().getY()));
        } else if (nextMove.getX() < minX) {
            animal.setPosition(new Vector2D(maxX, animal.getPosition().getY()));
        }
    }

    @Override
    public boolean isSpecialMove(Vector2D position) {
        return position.getX() > maxX || position.getX() < minX
                || position.getY() > maxY || position.getY() < minY;
    }
}
