package agh.ics.oop.simulation;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Genome;
import agh.ics.oop.model.animal.MoveDirection;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.behavior.AllAnimalBehaviors;
import agh.ics.oop.model.edge.AllEdges;
import agh.ics.oop.model.growth.GrowthTypes;
import agh.ics.oop.model.map.Grass;
import agh.ics.oop.model.map.Map;
import agh.ics.oop.model.map.PriorityBreedingFeedingMap;
import agh.ics.oop.model.mutation.AllMutations;
import org.junit.jupiter.api.Test;

import java.util.*;

import static agh.ics.oop.model.growth.GrowthTypes.EquatorialGrowth;
import static org.junit.jupiter.api.Assertions.*;

class DayManagerTest {

    private Map map;

    private ConfigurationData configurationData;

    private PriorityBreedingFeedingMap priorityBreedingFeedingMap;

    private DayManager dayManager;



//    Sprawdź, czy usuwa zwierzęta z energią równą 0.
//    Sprawdź, czy nie usuwa zwierzęta z energią większą niż 0.
//    Sprawdź, czy metoda radzi sobie z pustą listą zwierząt.
@Test
void removeDeathAnimals_ShouldRemoveAnimalsWithZeroEnergy() {
    // Przygotowanie
    configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
    map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
    dayManager = new DayManager(map, configurationData,1);

    Animal deadAnimal = new Animal(new Vector2D(1,2),0, MoveDirection.NORTH,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.EAST)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
    Animal liveAnimal = new Animal(new Vector2D(1,2),10, MoveDirection.NORTH,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.EAST)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
    map.addAnimal(deadAnimal.getPosition(),deadAnimal);
    map.addAnimal(liveAnimal.getPosition(),liveAnimal);

    // Działanie
    dayManager.removeDeathAnimals();
    // Weryfikacja
    List<Animal> nwm = map.getAnimalsAt(new Vector2D(1,2));
    assertTrue(map.getAnimalsAt(new Vector2D(1,2)).contains(liveAnimal));
    assertFalse(map.getAnimalsAt(new Vector2D(1,2)).contains(deadAnimal));
}

    @Test
    void removeDeathAnimals_ShouldNotRemoveAnimalsWithNonZeroEnergy() {
        // Przygotowanie
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        Animal liveAnimal = new Animal(new Vector2D(1,2),10, MoveDirection.NORTH,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.EAST)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        map.addAnimal(liveAnimal.getPosition(),liveAnimal);

        // Działanie
        dayManager.removeDeathAnimals();

        // Weryfikacja
        assertTrue(map.getAnimalsAt(new Vector2D(1,2)).contains(liveAnimal));
    }

        @Test
        void removeDeathAnimals_ShouldHandleEmptyListOfAnimals() {
            // Przygotowanie
            configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
            map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
            dayManager = new DayManager(map, configurationData,1);

            // Działanie
            dayManager.removeDeathAnimals();

            // Działanie i Weryfikacja
            assertDoesNotThrow(() -> dayManager.removeDeathAnimals());
        }
    @Test
//    Sprawdź, czy zwierzęta są prawidłowo obracane.
//    Sprawdź, czy zwierzęta są prawidłowo przesuwane.
//    Sprawdź, czy zachowanie na krawędziach mapy jest obsługiwane poprawnie.
//    Sprawdź, czy pozycje zwierząt są aktualizowane w mapie.
//    Sprawdź, czy zwierzęta są dodawane do priorityBreedingFeedingMap.
    void testRotateAndMoveAnimals() {
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        Animal animal = new Animal(new Vector2D(1,2),12, MoveDirection.NORTH_EAST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        Animal animal1 = new Animal(new Vector2D(3,2),10, MoveDirection.NORTH_WEST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        map.addAnimal(animal.getPosition(),animal);
        map.addAnimal(animal1.getPosition(),animal1);

        Vector2D vector2D = animal.getPosition();
        Vector2D vector2D1 = animal1.getPosition();
        dayManager.rotateAndMoveAnimals();
        Vector2D vector2D11 = animal.getPosition();
        Vector2D vector2D12 = animal1.getPosition();
        dayManager.rotateAndMoveAnimals();

        List<Animal> nwm = map.getAnimalsAt(new Vector2D(3,3));
        Vector2D vector2D3 = animal.getPosition();
        Vector2D vector2D121 = animal1.getPosition();
        List<Animal> nwm1 = map.getAnimalsAt(animal.getPosition());
        boolean isAnimal1InCorrectPlace = map.getAnimalsAt(new Vector2D(1,4)).contains(animal1);
        boolean isAnimalInCorrectPlace = map.getAnimalsAt(new Vector2D(3,4)).contains(animal);
        assertTrue(isAnimalInCorrectPlace);
        assertTrue(isAnimal1InCorrectPlace);
    }
    @Test
//    Sprawdź, czy energia zwierzęcia rośnie po zjedzeniu trawy.
    void testKeepCalmAndEatGrass_IsEnergyGrowingAfterEatenGrass() {
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        Animal animal = new Animal(new Vector2D(1,2),32, MoveDirection.NORTH_EAST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        Animal animal1 = new Animal(new Vector2D(3,2),30, MoveDirection.NORTH_WEST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        map.addAnimal(animal.getPosition(),animal);
        map.addAnimal(animal1.getPosition(),animal1);
        map.addGrass(new Vector2D(2,3));
        dayManager.rotateAndMoveAnimals();

        dayManager.keepCalmAndEatGrass();

        int expectedEnergyAnimal = 41;
        int expectedEnergyAnimal1 = 29;
        assertEquals(expectedEnergyAnimal1,animal1.getEnergy());
        assertEquals(expectedEnergyAnimal,animal.getEnergy());
    }
//    Sprawdź, czy trawa jest usuwana z mapy po zjedzeniu.
    @Test
    void testKeepCalmAndEatGrass_IsGrassRemovedAfterEating() {
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        Animal animal = new Animal(new Vector2D(1,2),32, MoveDirection.NORTH_EAST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        Animal animal1 = new Animal(new Vector2D(3,2),30, MoveDirection.NORTH_WEST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        map.addAnimal(animal.getPosition(),animal);
        map.addAnimal(animal1.getPosition(),animal1);
        map.addGrass(new Vector2D(2,3));
        dayManager.rotateAndMoveAnimals();

        dayManager.keepCalmAndEatGrass();

        boolean actual = map.isGrassAt(new Vector2D(2,3));
        assertFalse(actual);
    }
//    Sprawdź, czy metoda radzi sobie z sytuacją, gdy na danym polu nie ma trawy.
@Test
    void testKeepCalmAndEatGrass_NoGrassToEat() {
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        Animal animal = new Animal(new Vector2D(1,2),32, MoveDirection.NORTH_EAST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        Animal animal1 = new Animal(new Vector2D(3,2),30, MoveDirection.NORTH_WEST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        map.addAnimal(animal.getPosition(),animal);
        map.addAnimal(animal1.getPosition(),animal1);
        dayManager.rotateAndMoveAnimals();

        dayManager.keepCalmAndEatGrass();

        boolean actual = map.isGrassAt(new Vector2D(2,3));
        assertFalse(actual);
    }
//    Sprawdź, czy metoda radzi sobie z sytuacją, gdy na danym polu nie ma zwierząt.
    @Test
    void testKeepCalmAndEatGrass_NoAnimalsAtPositionOfGrass() {
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        map.addGrass(new Vector2D(2,3));

        dayManager.keepCalmAndEatGrass();

        boolean actual = map.isGrassAt(new Vector2D(2,3));
        assertTrue(actual);
    }

//    Sprawdź, czy zwierzęta na odpowiednich pozycjach są wybierane odpowiednie zwierzęta do rozmnażania do rozmnażania.
    @Test
    void testKeepCalmAndReproduce_CorrectAnimalSelectionForBreeding() {
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        Animal animal = new Animal(new Vector2D(1,2),32, MoveDirection.NORTH_EAST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        Animal animal1 = new Animal(new Vector2D(3,2),30, MoveDirection.NORTH_WEST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        Animal animal2 = new Animal(new Vector2D(2,2),27, MoveDirection.NORTH,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        map.addAnimal(animal.getPosition(),animal);
        map.addAnimal(animal1.getPosition(),animal1);
        map.addAnimal(animal2.getPosition(),animal2);

        dayManager.rotateAndMoveAnimals();
        dayManager.keepCalmAndReproduce();

        int actualCount = animal2.getEnergy();
        assertEquals(26,actualCount);
        int actualCount3 = animal.getEnergy();
        assertEquals(30,actualCount3);
        int actualCount2 = animal1.getEnergy();
        assertEquals(28,actualCount2);
    }
    //    Sprawdź, czy rozmnażanie nie odbywa się, gdy są niewystarczające warunki (np. brak energii).
    @Test
    void testKeepCalmAndReproduce_NoBreedingUnderInsufficientConditions() {
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,40,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        Animal animal = new Animal(new Vector2D(1,2),32, MoveDirection.NORTH_EAST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        Animal animal1 = new Animal(new Vector2D(3,2),30, MoveDirection.NORTH_WEST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        Animal animal2 = new Animal(new Vector2D(2,2),27, MoveDirection.NORTH,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        map.addAnimal(animal.getPosition(),animal);
        map.addAnimal(animal1.getPosition(),animal1);
        map.addAnimal(animal2.getPosition(),animal2);

        dayManager.rotateAndMoveAnimals();
        dayManager.keepCalmAndReproduce();

        int actualCount = animal2.getEnergy();
        assertEquals(26,actualCount);
        int actualCount3 = animal.getEnergy();
        assertEquals(31,actualCount3);
        int actualCount2 = animal1.getEnergy();
        assertEquals(29,actualCount2);
    }
    //    Sprawdź, czy nowe zwierzęta są dodawane do mapy.
    @Test
    void testKeepCalmAndReproduce_AdditionOfNewAnimalsToMap() {
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        Animal animal = new Animal(new Vector2D(1,2),32, MoveDirection.NORTH_EAST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        Animal animal1 = new Animal(new Vector2D(3,2),30, MoveDirection.NORTH_WEST,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        Animal animal2 = new Animal(new Vector2D(2,2),27, MoveDirection.NORTH,new Genome(new ArrayList<>(Arrays.asList(MoveDirection.NORTH,MoveDirection.NORTH)),0),configurationData.allMutations(),configurationData.allAnimalBehaviors());
        map.addAnimal(animal.getPosition(),animal);
        map.addAnimal(animal1.getPosition(),animal1);
        map.addAnimal(animal2.getPosition(),animal2);

        dayManager.rotateAndMoveAnimals();
        dayManager.keepCalmAndReproduce();

        int actual = map.getAnimalsAt(new Vector2D(2,3)).size();
        assertEquals(4,actual);
    }
//    Sprawdź, czy rośliny są dodawane do mapy zgodnie ze strategią wzrostu.
    @Test
    void testNewPlantDontGrowOnOther_NearToGrassGrowth(){
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,120,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        map.addGrass(new Vector2D(5,5));
        map.addGrass(new Vector2D(1,6));

        dayManager.newPlantsGrowth();

        int numberOfGrass = map.getGrassHashMap().values().size();
        assertEquals(121,numberOfGrass);
    }

    @Test
    void testNewPlantDontGrowOnOther_EquatorialGrowth(){
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,120,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(EquatorialGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
        map.addGrass(new Vector2D(5,5));
        map.addGrass(new Vector2D(1,6));

        dayManager.newPlantsGrowth();

        int numberOfGrass = map.getGrassHashMap().values().size();
        assertEquals(121,numberOfGrass);
    }
//    Sprawdź, czy rośliny nie rosną w miejscach, gdzie już są.
//    Sprawdź zachowanie w przypadku pustej mapy.
    void testNewPlantsGrowth() {
        // Ustaw środowisko testowe
        // Wywołaj metodę
        dayManager.newPlantsGrowth();
        // Sprawdź, czy nowe rośliny pojawiły się na mapie
        // ... asercje ...
    }

}
