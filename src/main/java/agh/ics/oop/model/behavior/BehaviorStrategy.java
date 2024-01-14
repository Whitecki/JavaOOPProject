package agh.ics.oop.model.behavior;
import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.MoveDirection;
import agh.ics.oop.model.animal.Vector2D;

import java.util.HashMap;

public interface BehaviorStrategy {
    void performAction(HashMap<Vector2D, Animal> animalHashMap);
}
