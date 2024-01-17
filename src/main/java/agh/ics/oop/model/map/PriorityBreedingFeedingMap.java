package agh.ics.oop.model.map;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.map.WorldElement;

import java.util.ArrayList;
import java.util.HashMap;

public class PriorityBreedingFeedingMap {
    private final HashMap<Vector2D, ArrayList<Animal>> priorityBreedingFeedingMap;

    public PriorityBreedingFeedingMap(HashMap<Vector2D, ArrayList<Animal>> priorityBreedingFeedingMap) {
        this.priorityBreedingFeedingMap = priorityBreedingFeedingMap;
    }

    public void add(Vector2D vector2D, Animal animal) {
        ArrayList<Animal> animalsAtPosition = priorityBreedingFeedingMap.computeIfAbsent(vector2D, k -> new ArrayList<>());
        if (animalsAtPosition.isEmpty()) {
            animalsAtPosition.add(animal);
        } else if (animalsAtPosition.size() == 1) {
            animalsAtPosition.add(animal);
            animalsAtPosition.sort((a1, a2) -> Integer.compare(a2.getEnergy(), a1.getEnergy()));
        } else if (shouldReplaceFirstAnimal(animalsAtPosition, animal)) {
            animalsAtPosition.set(1,animalsAtPosition.get(0));
            animalsAtPosition.set(0, animal);
        } else if (shouldReplaceSecondAnimal(animalsAtPosition, animal)) {
            animalsAtPosition.set(1, animal);
        }
    }

    public boolean isTheBestOnField(Vector2D vector2D){
        ArrayList<Animal> animalsAtPosition = priorityBreedingFeedingMap.getOrDefault(vector2D, new ArrayList<>());
        return !animalsAtPosition.isEmpty();
    }

    public Animal getTheBest(Vector2D vector2D) {
        ArrayList<Animal> animalsAtPosition = priorityBreedingFeedingMap.getOrDefault(vector2D, new ArrayList<>());
        //return null trzeba zmienić
        return animalsAtPosition.get(0);
    }

    public boolean isSecondTheBest(Vector2D vector2D){
        ArrayList<Animal> animalsAtPosition = priorityBreedingFeedingMap.getOrDefault(vector2D, new ArrayList<>());
        return animalsAtPosition.size() > 1;
    }
    public Animal getSecondTheBest(Vector2D vector2D) {
        ArrayList<Animal> animalsAtPosition = priorityBreedingFeedingMap.getOrDefault(vector2D, new ArrayList<>());
        return animalsAtPosition.get(1);
    }

    private boolean shouldReplaceFirstAnimal(ArrayList<Animal> animals, Animal newAnimal) {
        return animals.get(0).getEnergy() < newAnimal.getEnergy();
    }

    private boolean shouldReplaceSecondAnimal(ArrayList<Animal> animals, Animal newAnimal) {
        return animals.get(1).getEnergy() < newAnimal.getEnergy();
    }
}
