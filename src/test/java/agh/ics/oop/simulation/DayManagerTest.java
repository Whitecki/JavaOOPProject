package agh.ics.oop.simulation;

import agh.ics.oop.model.animal.Animal;
import agh.ics.oop.model.animal.Genome;
import agh.ics.oop.model.animal.MoveDirection;
import agh.ics.oop.model.animal.Vector2D;
import agh.ics.oop.model.behavior.AllAnimalBehaviors;
import agh.ics.oop.model.edge.AllEdges;
import agh.ics.oop.model.edge.GlobeEdgeBehavior;
import agh.ics.oop.model.growth.GrowthTypes;
import agh.ics.oop.model.map.Map;
import agh.ics.oop.model.mutation.AllMutations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static agh.ics.oop.model.growth.GrowthTypes.EquatorialGrowth;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DayManagerTest {
    @Mock
    private Map map;
    @Mock
    private ConfigurationData configurationData;
    @Mock
    private PriorityBreedingFeedingMap priorityBreedingFeedingMap;

    private DayManager dayManager;

    @BeforeEach
    void setUp() {
        configurationData = new ConfigurationData(10,10, AllEdges.GlobeEdgeBehavior,10,10,1,EquatorialGrowth,2,2,3,1,1,2, AllMutations.RandomMutation,2, AllAnimalBehaviors.FullPredestination);
        map = new Map(GrowthTypes.NearToGrassGrowth, AllEdges.GlobeEdgeBehavior,0,0,configurationData.mapWidth(),configurationData.mapHeight());
        dayManager = new DayManager(map, configurationData,1);
    }


//    Sprawdź, czy usuwa zwierzęta z energią równą 0.
//    Sprawdź, czy nie usuwa zwierzęta z energią większą niż 0.
//    Sprawdź, czy metoda radzi sobie z pustą listą zwierząt.
@Test
void removeDeathAnimals_ShouldRemoveAnimalsWithZeroEnergy() {
    // Przygotowanie
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
        List<Animal> animals = new ArrayList<>();

        HashMap<Vector2D, List<Animal>> animalMap = new HashMap<>();
        animalMap.put(new Vector2D(0, 0), animals);

        when(map.getAnimalHashMap()).thenReturn(animalMap);

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
        // Ustaw testowe zwierzęta i ich początkowe pozycje
        // Wywołaj metodę
        dayManager.rotateAndMoveAnimals();
        // Sprawdź, czy zwierzęta zmieniły swoją pozycję
        // ... asercje ...
    }
    @Test
//    Sprawdź, czy energia zwierzęcia rośnie po zjedzeniu trawy.
//    Sprawdź, czy trawa jest usuwana z mapy po zjedzeniu.
//    Sprawdź, czy metoda radzi sobie z sytuacją, gdy na danym polu nie ma trawy.
//    Sprawdź, czy metoda radzi sobie z sytuacją, gdy na danym polu nie ma zwierząt.
    void testKeepCalmAndEatGrass() {
        // Ustaw zwierzęta i trawę
        // Wywołaj metodę
        dayManager.keepCalmAndEatGrass();
        // Sprawdź, czy trawa została zjedzona i czy energia zwierząt wzrosła
        // ... asercje ...
    }
    @Test
//    Sprawdź, czy zwierzęta na odpowiednich pozycjach są wybierane do rozmnażania.
//    Sprawdź, czy rozmnażanie odbywa się prawidłowo.
//    Sprawdź, czy rozmnażanie nie odbywa się, gdy są niewystarczające warunki (np. brak energii).
//    Sprawdź, czy nowe zwierzęta są dodawane do mapy.
    void testKeepCalmAndReproduce() {
        // Ustaw zwierzęta gotowe do rozmnażania
        // Wywołaj metodę
        dayManager.keepCalmAndReproduce();
        // Sprawdź, czy doszło do rozmnażania
        // ... asercje ...
    }
    @Test
//    Sprawdź, czy rośliny są dodawane do mapy zgodnie ze strategią wzrostu.
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
