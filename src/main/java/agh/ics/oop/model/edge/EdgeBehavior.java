package agh.ics.oop.model.edge;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Vector2D;

public interface EdgeBehavior {
    void handleEdgeCrossing(Animal animal);
    boolean isSpecialMove(Vector2D vector2D);
}
