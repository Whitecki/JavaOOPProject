package agh.ics.oop.simulation;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Genome;
import agh.ics.oop.model.animal.MoveDirection;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.map.Map;
import agh.ics.oop.model.until.RandomVector2DGenerator;
import agh.ics.oop.model.visualization.ConsoleMapDisplay;
import agh.ics.oop.model.visualization.MapChangeListener;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Random;

public class Simulation implements Runnable{
    private final ConfigurationData cD;
    private final Map map;
    private final Random random = new Random();

    public Simulation(ConfigurationData configurationData) {
        this.cD = configurationData;
        this.map = new Map(cD.growthTypes(), cD.allEdges(), 0, 0, cD.mapWidth(), cD.mapHeight());
        putInitialGrass();
        putInitialAnimals();
        MapChangeListener listener = new ConsoleMapDisplay();
        map.subscribe(listener);
        map.notify("start");
    }
    public void putInitialGrass(){
        ArrayList<Vector2D> allPossibleFields = new ArrayList<>();
        for (int i = 0; i <= cD.mapWidth(); i++) {
            for(int j = 0; j <= cD.mapHeight(); j++){
                allPossibleFields.add(new Vector2D(i,j));
            }
        }
        RandomVector2DGenerator randomGrassGenerator = new RandomVector2DGenerator(map,allPossibleFields, cD.initialPlantCount());
        for (Vector2D vector2D: randomGrassGenerator) {
            map.addGrass(vector2D);
        }
    }

    public void putInitialAnimals(){
        ArrayList<Vector2D> allPossibleFields = new ArrayList<>();
        for (int i = 0; i <= cD.mapWidth(); i++) {
            for(int j = 0; j <= cD.mapHeight(); j++){
                allPossibleFields.add(new Vector2D(i,j));
            }
        }
        RandomVector2DGenerator randomGrassGenerator = new RandomVector2DGenerator(map,allPossibleFields, cD.initialAnimalCount());
        for (Vector2D vector2D: randomGrassGenerator) {
            MoveDirection randomDirection = MoveDirection.values()[random.nextInt(MoveDirection.values().length)];
            map.addAnimal(vector2D,new Animal(vector2D, cD.initialAnimalEnergy(),randomDirection, generateRandomGenome(), cD.allMutations(), cD.allAnimalBehaviors()));
        }
    }
    public void startSimulation() {

        for (int i = 0; i < 10; i++) {
            int j = i;
            Platform.runLater(() -> {
                DayManager dayManager = new DayManager(map, cD, j);
                dayManager.run();
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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


    @Override
    public void run() {
        startSimulation();
    }

    public Map getMap() {
        return map;
    }
}
