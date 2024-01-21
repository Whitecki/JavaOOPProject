package agh.ics.oop.model.behavior;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Vector2D;

import java.util.HashMap;
import java.util.List;

public class FullPredestination implements BehaviorStrategy {
    public void performAction(HashMap<Vector2D, List<Animal>> animalHashMap) {
        for (List<Animal> animalsList : animalHashMap.values()) {
            for (Animal animal : animalsList) {
                animal.getGenom().setActiveGenomeToNextIndex();
                animal.setNewDirection();
            }
        }
    }
}
