package agh.ics.oop.model.behavior;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Vector2D;

import java.util.HashMap;

public class FullPredestination implements BehaviorStrategy {
    public void performAction(HashMap<Vector2D, Animal> animalHashMap) {
        for (Animal animal: animalHashMap.values()){
            animal.getGenom().setActiveGenomeToNextIndex();
            animal.setNewDirection();
        }
    }
}
