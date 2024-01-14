package agh.ics.oop.model.behavior;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.MoveDirection;
import agh.ics.oop.model.animal.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;

public class FullPredestination implements BehaviorStrategy {
    public void performAction(HashMap<Vector2D, Animal> animalHashMap) {
        for (Animal animal: animalHashMap.values()){
            ArrayList<MoveDirection> genom =animal.getGenom().getGenome();
            // przestawiam idx aktualnie aktywnego genomu o jeden do przodu
            animal.getGenom().setActiveGenomeIndex((animal.getGenom().getActiveGenomeIndex()+1)%genom.size());
            int activeGenomIndex =  animal.getGenom().getActiveGenomeIndex();
            // animal ma nowy kierunek
            animal.setDirection(genom.get(activeGenomIndex));
            // direction musi być przechowywane w dwóch miejscach, bo np. po odbiciu się od ściany dochodzi do zmiany
            // kierunku, bez zmiany odpowiedniego genomu. tj. od walnięcia głową w mur nie dojdzie do mutacji
        }
    }
}
