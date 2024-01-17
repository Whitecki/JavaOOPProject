package agh.ics.oop.app;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.simulation.Simulation;

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
                sum += animal.getNumberOfChildren();
            }
        }
        return (int) sum/numOfAnimals;
    }
}
