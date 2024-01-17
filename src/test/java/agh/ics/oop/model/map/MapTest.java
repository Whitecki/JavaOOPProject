package agh.ics.oop.model.map;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Genome;
import agh.ics.oop.model.animal.MoveDirection;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.behavior.AllAnimalBehaviors;
import agh.ics.oop.model.edge.AllEdges;
import agh.ics.oop.model.growth.GrowthTypes;
import agh.ics.oop.model.mutation.AllMutations;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void addingNewAnimalToMap(){
        Vector2D newVector = new Vector2D(1,2);
        Animal animal = new Animal(newVector,0, MoveDirection.NORTH,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.EAST)),0), AllMutations.RandomMutation, AllAnimalBehaviors.FullPredestination);
        Map map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,10,10);

        map.addAnimal(animal.getPosition(),animal);

        List<Animal> spr = map.getAnimalHashMap().get(animal.getPosition());
        List<Animal> spr2 = map.getAnimalsAt(animal.getPosition());
        List<Animal> spr3 = map.getAnimalsAt(new Vector2D(1,2));
        List<Animal> spr4 = map.getAnimalsAt(newVector);
        assertEquals(spr.size(),1);
        assertTrue(spr.contains(animal));
    }

}