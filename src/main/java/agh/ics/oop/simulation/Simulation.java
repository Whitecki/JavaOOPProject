package agh.ics.oop.simulation;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Genome;
import agh.ics.oop.model.animal.MoveDirection;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.map.Map;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    private final ConfigurationData cD;
    private final Map map;
    private final Random random = new Random();

    public Simulation(ConfigurationData configurationData) {
        this.cD = configurationData;
        this.map = new Map(cD.growthTypes(), cD.allEdges(), 0, 0, cD.mapWidth(), cD.mapHeight());
        putInitialGrass();
        putInitialAnimals();
        startSimulation();
    }
    public void putInitialGrass(){
        int[] randomHeight = RandomUniqueValues.generateRandomUniqueValues(0, cD.mapHeight()+1, cD.initialPlantCount()).stream().mapToInt(i -> i).toArray();
        int[] randomWidth = RandomUniqueValues.generateRandomUniqueValues(0, cD.mapWidth()+1, cD.initialPlantCount()).stream().mapToInt(i -> i).toArray();
        for (int i = 0; i < cD.initialPlantCount(); i++) {
            Vector2D newVector = new Vector2D(randomWidth[i],randomHeight[i]);
            map.addGrass(newVector);
        }
    }

    public void putInitialAnimals(){
        int[] randomHeight = RandomUniqueValues.generateRandomUniqueValues(0, cD.mapHeight()+1, cD.initialAnimalCount()).stream().mapToInt(i -> i).toArray();
        int[] randomWidth = RandomUniqueValues.generateRandomUniqueValues(0, cD.mapWidth()+1, cD.initialAnimalCount()).stream().mapToInt(i -> i).toArray();
        for (int i = 0; i < cD.initialAnimalCount(); i++) {
            Vector2D newVector = new Vector2D(randomWidth[i],randomHeight[i]);
            MoveDirection randomDirection = MoveDirection.values()[random.nextInt(MoveDirection.values().length)];
            map.addAnimal(newVector,new Animal(newVector, cD.initialAnimalEnergy(),randomDirection, generateRandomGenome(), cD.allMutations(), cD.allAnimalBehaviors()));
        }
    }
    private void startSimulation() {
        for (int i = 0; i < 5; i++) {
            DayManager dayManager = new DayManager(map, cD, i);
            dayManager.run();
        }
    }
    public Genome generateRandomGenome(){
        ArrayList<MoveDirection> directions = new ArrayList<>(cD.genomeLength());
        for (int i = 0; i < cD.genomeLength(); i++) {
            MoveDirection randomDirection = MoveDirection.values()[random.nextInt(MoveDirection.values().length)];
            directions.add(i,randomDirection);
        }
        int activeGenIdx = random.nextInt(cD.genomeLength());
        return new Genome(directions,activeGenIdx);
    }






}