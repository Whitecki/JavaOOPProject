package agh.ics.oop.simulation;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Vector2D;

import java.util.HashMap;

public class PriorityBreedingFeedingMap {
    private final HashMap<Vector2D, Animal[]> priorityBreedingFeedingMap;

    public PriorityBreedingFeedingMap(HashMap<Vector2D, Animal[]> priorityBreedingFeedingMap) {
        this.priorityBreedingFeedingMap = priorityBreedingFeedingMap;
    }

    public void add(Vector2D vector2D, Animal animal) {
        Animal[] animalsAtPosition = getAnimalsAtPosition(vector2D);
        if (animalsAtPosition == null) return;

        if (shouldReplaceFirstAnimal(animalsAtPosition, animal)) {
            animalsAtPosition[0] = animal;
        } else if (shouldReplaceSecondAnimal(animalsAtPosition, animal)) {
            animalsAtPosition[1] = animal;
        }
    }

    public Animal getTheBest(Vector2D vector2D) {
        Animal[] animalsAtPosition = getAnimalsAtPosition(vector2D);
        if (animalsAtPosition == null) return null;

        return animalsAtPosition[0].getEnergy() >= animalsAtPosition[1].getEnergy() ? animalsAtPosition[0] : animalsAtPosition[1];
    }

    public Animal getSecondTheBest(Vector2D vector2D) {
        Animal[] animalsAtPosition = getAnimalsAtPosition(vector2D);
        if (animalsAtPosition == null) return null;

        return animalsAtPosition[0].getEnergy() < animalsAtPosition[1].getEnergy() ? animalsAtPosition[0] : animalsAtPosition[1];
    }

    private boolean shouldReplaceFirstAnimal(Animal[] animals, Animal newAnimal) {
        return animals[0].getEnergy() < animals[1].getEnergy() && animals[0].getEnergy() < newAnimal.getEnergy();
    }

    private boolean shouldReplaceSecondAnimal(Animal[] animals, Animal newAnimal) {
        return animals[0].getEnergy() > animals[1].getEnergy() && animals[1].getEnergy() < newAnimal.getEnergy();
    }

    private Animal[] getAnimalsAtPosition(Vector2D vector2D) {
        return priorityBreedingFeedingMap.getOrDefault(vector2D, null);
    }
}
