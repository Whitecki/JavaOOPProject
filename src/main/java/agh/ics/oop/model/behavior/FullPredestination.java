package agh.ics.oop.model.behavior;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.MoveDirection;
import agh.ics.oop.model.animal.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;

public class FullPredestination implements BehaviorStrategy {
    public void performAction(HashMap<Vector2D, Animal> animalHashMap) {
        for (Animal animal: animalHashMap.values()){
            // przestawiam idx aktualnie aktywnego genomu o jeden do przodu
            animal.getGenom().setActiveGenomeToNextIndex();
            // animal ma nowy kierunek
            animal.setNewDirection();
            // direction musi być przechowywane w dwóch miejscach, bo np. po odbiciu się od ściany dochodzi do zmiany
            // kierunku, bez zmiany odpowiedniego genomu. tj. od walnięcia głową w mur nie dojdzie do mutacji
        }
    }
}
