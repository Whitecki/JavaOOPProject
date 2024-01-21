package agh.ics.oop.simulation;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Reproduction;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.behavior.FullPredestination;
import agh.ics.oop.model.map.Map;
import agh.ics.oop.model.map.PriorityBreedingFeedingMap;

import java.util.*;

public class DayManager {
    private final Map map;
    private final PriorityBreedingFeedingMap priorityBreedingFeedingMap;
    private final ConfigurationData configurationData;
    private final FullPredestination fullPredestination = new FullPredestination();
    private final int day;

    public DayManager(Map map, ConfigurationData configurationData, int day) {
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
        map.notify("map has been changed");

    }

    public void removeDeathAnimals() {
        for (List<Animal> animalList : map.getAnimalHashMap().values()) {
            animalList.stream()
                    .filter(animal -> animal.getEnergy() == 0)
                    .forEach(animal -> animal.died(day));

            for(Animal animal : animalList){
                if(animal.getEnergy() == 0){
                    map.addToDeadAnimals(animal);
                }
            }
            animalList.removeIf(animal -> animal.getEnergy() == 0);
        }
    }

    public void rotateAndMoveAnimals() {
        List<Animal> plannedMoves = new ArrayList<>();

        // Przemieszczenie zwierzaków
        for (List<Animal> animalList : new ArrayList<>(map.getAnimalHashMap().values())) {
            for (Animal animal : new ArrayList<>(animalList)) {
                animal.rotateAndDecreaseEnergy();
                Vector2D oldPosition = animal.getPosition();
                Vector2D newPosition = animal.nextMove();
                if (map.getEdgeBehavior().isSpecialMove(newPosition)) {
                    map.getEdgeBehavior().handleEdgeCrossing(animal);
                } else {
                    animal.setPosition(animal.nextMove());
                }
                plannedMoves.add(animal);
            }
        }
        HashMap<Vector2D, List<Animal>> animalHashMap = new HashMap<>();
        map.setAnimalHashMap(animalHashMap);
        // Wkładam nowe pozycje zwierzakó na nową HashMape
        for (Animal animal : plannedMoves) {
            map.addAnimal(animal.getPosition(), animal);
            priorityBreedingFeedingMap.add(animal.getPosition(), animal);
        }
        fullPredestination.performAction(map.getAnimalHashMap());
    }


    public void keepCalmAndEatGrass() {
        // Tworzenie kopii zestawu kluczy
        Set<Vector2D> keySetCopy = new HashSet<>(map.getGrassHashMap().keySet());

        // Iterowanie po kopii zestawu kluczy
        for (Vector2D vector2D : keySetCopy) {
            if (priorityBreedingFeedingMap.isTheBestOnField(vector2D)){
                Animal animal = priorityBreedingFeedingMap.getTheBest(vector2D);
                if (animal != null) {
                    animal.haveEaten();
                    animal.changeEnergy(configurationData.energyFromPlant());
                    map.removeGrass(vector2D);
            }
            }
        }
    }


    public void keepCalmAndReproduce() {
        for (Vector2D vector2D : map.getAnimalHashMap().keySet()) {
            if (priorityBreedingFeedingMap.isSecondTheBest(vector2D)){
                Animal animal1 = priorityBreedingFeedingMap.getTheBest(vector2D);
                Animal animal2 = priorityBreedingFeedingMap.getSecondTheBest(vector2D);
                if (animal1.getEnergy() >= configurationData.energyToReproduce() && animal2.getEnergy() >= configurationData.energyToReproduce()) {
                    Reproduction reproduction = new Reproduction(animal1, animal2, configurationData.energyUsedToCreateChild(), configurationData.minMutationCount(), configurationData.maxMutationCount());
                    map.addAnimal(reproduction.getChild().getPosition(), reproduction.getChild());
                    reproduction.getChild().Birth(day);
                }
            }
        }
    }

    public void newPlantsGrowth() {
        map.getGrowthStrategy().growPlants(map,configurationData.dailyPlantGrowth());
    }
}
