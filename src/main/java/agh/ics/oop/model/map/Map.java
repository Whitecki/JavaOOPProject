package agh.ics.oop.model.map;

import agh.ics.oop.model.edge.AllEdges;
import agh.ics.oop.model.edge.EdgeBehavior;
import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.edge.GlobeEdgeBehavior;
import agh.ics.oop.model.growth.EquatorialGrowth;
import agh.ics.oop.model.growth.GrowthStrategy;
import agh.ics.oop.model.growth.GrowthTypes;
import agh.ics.oop.model.growth.NearToGrassGrowth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;
    private final GrowthStrategy growthStrategy;
    private final EdgeBehavior edgeBehavior;
    private HashMap<Vector2D, List<Animal>> animalHashMap = new HashMap<>();
    private final HashMap<Vector2D, Grass> grassHashMap = new HashMap<>();
    private final Vector2D lowerLeftBands;
    private final Vector2D upperRightBands;
    public Map(GrowthTypes growthTypes, AllEdges allEdges, int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.lowerLeftBands = new Vector2D(minX, minY);
        this.upperRightBands = new Vector2D(maxX, maxY);
        this.growthStrategy = grow(growthTypes,maxX,maxY);
        this.edgeBehavior = edge(allEdges,maxX,maxY);
    }
    public GrowthStrategy grow(GrowthTypes growthTypes, int maxX, int maxY){
        return switch (growthTypes){
            case EquatorialGrowth -> new EquatorialGrowth();
            case NearToGrassGrowth -> new NearToGrassGrowth();
        };
    }

    public EdgeBehavior edge(AllEdges allEdges, int maxX, int maxY){
        return switch (allEdges){
            case GlobeEdgeBehavior -> new GlobeEdgeBehavior(0,0,maxX,maxY);
        };
    }
    public GrowthStrategy getGrowthStrategy() {
        return growthStrategy;
    }

    public EdgeBehavior getEdgeBehavior() {
        return edgeBehavior;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public Vector2D getLowerLeftBands() {
        return lowerLeftBands;
    }

    public Vector2D getUpperRightBands() {
        return upperRightBands;
    }

    public List<Animal> getAnimalsAt(Vector2D position) {
        return animalHashMap.get(position);
    }

    public Grass getGrassAt(Vector2D position) {
        return grassHashMap.get(position);
    }

    public boolean isGrassAt(Vector2D position){
        if (grassHashMap.get(position) == null){
            return false;
        }
        return true;
    }

    public void addAnimal(Vector2D position, Animal animal) {
        animalHashMap.computeIfAbsent(position, k -> new ArrayList<>()).add(animal);
    }

    public void addGrass(Vector2D position) {
        grassHashMap.put(position, new Grass(position));
    }

    public void removeAnimal(Vector2D position, Animal animal) {
        List<Animal> animalsAtPosition = animalHashMap.get(position);
        if (animalsAtPosition != null) {
            animalsAtPosition.remove(animal);
            if (animalsAtPosition.isEmpty()) {
                animalHashMap.remove(position);
            }
        }
    }
    public void removeGrass(Vector2D position) {
        grassHashMap.remove(position);
    }

    public HashMap<Vector2D, List<Animal>> getAnimalHashMap() {
        return animalHashMap;
    }
    public void setAnimalHashMap(HashMap<Vector2D, List<Animal>> animalHashMap) {
        this.animalHashMap = animalHashMap;
    }
    public HashMap<Vector2D, Grass> getGrassHashMap() {
        return grassHashMap;
    }

}
