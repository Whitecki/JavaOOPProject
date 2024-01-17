package agh.ics.oop.app;

import agh.ics.oop.model.animal.Animal;

public class AnimalStatistics{
    private Animal animal;
    public AnimalStatistics(Animal animal){
        this.animal = animal;
    }

    public String getEnergyLevel(){
        return String.valueOf(animal.getEnergy());
    }

    public String getPlantsEaten(){
        return String.valueOf(animal.getNumberOfEatenGrass());
    }

    public String getKidsNumber(){
        return String.valueOf(animal.getNumberOfChilds());
    }

    public String getAge(int day){
        if(animal.getDayOfDeath() == 0){
        int age = day - animal.getDayOfBirth();
        return String.valueOf(age);
        }else{
            return String.valueOf(animal.getDayOfDeath() - animal.getDayOfBirth());
        }
    }

    public String getDeathDate(){
        return String.valueOf(animal.getDayOfDeath());
    }

    public String getGenom(){
        return String.valueOf(animal.getGenom());
    }

    public String getActiveGenomPart(){
        return String.valueOf(animal.getGenom().getActiveGenomeIndex());
    }
}
