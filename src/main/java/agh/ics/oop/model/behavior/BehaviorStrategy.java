package agh.ics.oop.model.behavior;
import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Vector2D;

import java.util.HashMap;
import java.util.List;

public interface BehaviorStrategy {
    void performAction(HashMap<Vector2D, List<Animal>> animalHashMap);
}
