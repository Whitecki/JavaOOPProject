package agh.ics.oop.app;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.growth.GrowthStrategy;
import agh.ics.oop.model.growth.NearToGrassGrowth;
import agh.ics.oop.model.map.Grass;
import agh.ics.oop.simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private Simulation simulation;

    public Statistics(Simulation simulation) {
        this.simulation = simulation;
    }

    public int allAnimalsCount(){
        int sum = 0;
        for (List<Animal> animalList : simulation.getMap().getAnimalHashMap().values()){
            sum += animalList.size();
        }
        return sum;
    }

    public int allGrassCount(){
        return simulation.getMap().getGrassHashMap().size();
    }

    public int freeFieldCount(){
        int numOfAnimals = simulation.getMap().getAnimalHashMap().size();
        int numOfGrass = allGrassCount();
        int numOfFields = (simulation.getMap().getMaxX()+1)*(simulation.getMap().getMaxY()+1);
        return numOfFields-numOfGrass-numOfAnimals;
    }

    public int averageEnergyCount(){
        int sum = 0;
        int numOfAnimals = 0;
        for (List<Animal> animalList : simulation.getMap().getAnimalHashMap().values()){
            for(Animal animal : animalList){
                sum += animal.getEnergy();
                numOfAnimals++;
            }
        }
        if(numOfAnimals == 0){
            return 0;
        }
        return (int) sum/numOfAnimals;
    }

    public int averageLifeLength(){
        int sum = 0;
        int numOfDeadAnimals = simulation.getMap().getDeadAnimals().size();
        for(Animal animal : simulation.getMap().getDeadAnimals()){
            sum += animal.getDayOfDeath()-animal.getDayOfBirth();
        }
        if(numOfDeadAnimals == 0){
            return 0;
        }
        return (int) sum/numOfDeadAnimals;
    }

    public int averageKidsCount(){
        int sum = 0;
        int numOfAnimals = allAnimalsCount();
        for(List<Animal> animalList : simulation.getMap().getAnimalHashMap().values()){
            for(Animal animal : animalList){
                sum += animal.getNumberOfChilds();
            }
        }
        if(numOfAnimals == 0){
            return 0;
        }
        return (int) sum/numOfAnimals;
    }

    public List<Vector2D> mostPreferedByPlants(){
        ArrayList<Vector2D> preferedFields = new ArrayList<>();
        GrowthStrategy growthStrategy = simulation.getMap().getGrowthStrategy();

        if(growthStrategy instanceof NearToGrassGrowth){

            for(Vector2D vector2D : simulation.getMap().getGrassHashMap().keySet()){
                for (int i = vector2D.getX() - 1; i <= vector2D.getX() + 1; i++) {
                    for (int j = vector2D.getY() - 1; j <= vector2D.getY() + 1; j++) {
                       if(i >= 0 && i <= simulation.getMap().getMaxX() && j >= 0 && j <= simulation.getMap().getMaxY() && (i != vector2D.getX() || j != vector2D.getY())){
                           preferedFields.add(new Vector2D(i, j));
                       }
                    }
                }
            }
        }else{
            int equatorTop = (simulation.getMap().getMaxY() - simulation.getMap().getMinY()) * 6/10;
            int equatorBottom = (simulation.getMap().getMaxY() - simulation.getMap().getMinY()) * 4/10;

            for(int i = 0; i <= simulation.getMap().getMaxX(); i++){
                for(int j = equatorBottom; j <= equatorTop; j++){
                    preferedFields.add(new Vector2D(i,j));
                }
            }
        }
        return preferedFields;
    }
}
