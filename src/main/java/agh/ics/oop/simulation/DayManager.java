package agh.ics.oop.simulation;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Reproduction;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.map.Map;

import java.util.HashMap;
import java.util.List;

public class DayManager {
    private final Map map;
    private final PriorityBreedingFeedingMap priorityBreedingFeedingMap;
    private final ConfigurationData configurationData;
    private final int day;

    public DayManager(Map map, ConfigurationData configurationData,int day) {
        this.map = map;
        this.configurationData = configurationData;
        this.day = day;
        this.priorityBreedingFeedingMap = new PriorityBreedingFeedingMap(new HashMap<>());
    }

    public void run() {
        removeDeathAnimals();
        rotateAndMoveAnimals();
        keepCalmAndEatGrass();
        keepCalmAndReproduce();
        newPlantsGrowth();
    }

    public void removeDeathAnimals() {
        for (List<Animal> animalList : map.getAnimalHashMap().values()) {
            animalList.stream()
                    .filter(animal -> animal.getEnergy() == 0)
                    .forEach(animal -> animal.died(day));
            animalList.removeIf(animal -> animal.getEnergy() == 0);
        }
    }

    public void rotateAndMoveAnimals() {
        for (List<Animal> animalList : map.getAnimalHashMap().values()) {
            for (Animal animal : animalList) {
                animal.rotateAndDecreaseEnergy();
                animalList.remove(animal);
                if (map.getEdgeBehavior().isSpecialMove(animal.nextMove())) {
                    map.getEdgeBehavior().handleEdgeCrossing(animal);
                } else {
                    animal.setPosition(animal.nextMove());
                }
                map.addAnimal(animal.getPosition(), animal);
                priorityBreedingFeedingMap.add(animal.getPosition(), animal);
            }
        }
    }

    public void keepCalmAndEatGrass() {
        for (Vector2D vector2D : map.getGrassHashMap().keySet()) {
            Animal animal = priorityBreedingFeedingMap.getTheBest(vector2D);
            animal.haveEaten();
            animal.changeEnergy(animal.getEnergy() + configurationData.energyFromPlant());
            map.removeGrass(vector2D);
            //
        }
    }

    public void keepCalmAndReproduce() {
        for (Vector2D vector2D : map.getAnimalHashMap().keySet()) {
            Animal animal1 = priorityBreedingFeedingMap.getTheBest(vector2D);
            Animal animal2 = priorityBreedingFeedingMap.getSecondTheBest(vector2D);
            if (animal1.getEnergy() >= configurationData.energyToReproduce() && animal2.getEnergy() >= configurationData.energyToReproduce()) {
                Reproduction reproduction = new Reproduction(animal1, animal2, configurationData.energyUsedToCreateChild(), configurationData.minMutationCount(), configurationData.maxMutationCount());
                map.addAnimal(reproduction.getChild().getPosition(),reproduction.getChild());
                reproduction.getChild().Birth(day);
            }
        }
    }

    public void newPlantsGrowth() {
        map.getGrowthStrategy().growPlants(map);
    }
}
