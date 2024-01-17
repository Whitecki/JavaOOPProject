package agh.ics.oop.model.animal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static agh.ics.oop.model.animal.MoveDirection.*;

public class Genome {
    private final ArrayList<MoveDirection> genome;
    private int activeGenomeIndex;
    public Genome(ArrayList<MoveDirection> genome, int activeGenomeIndex) {
        this.genome = genome;
        this.activeGenomeIndex = activeGenomeIndex;
    }

    public ArrayList<MoveDirection> getGenome() {
        return genome;
    }

    public MoveDirection getActiveGen() {
        return genome.get(activeGenomeIndex);
    }

    public int getActiveGenomeIndex() {
        return activeGenomeIndex;
    }

    public void setActiveGenomeToNextIndex(){activeGenomeIndex = (activeGenomeIndex+1)%genome.size();}

    public void setActiveGenomeIndex(int activeGenomeIndex) {
        if (activeGenomeIndex < 0 || activeGenomeIndex >= genome.size()) {
            throw new IllegalArgumentException("Index out of genome boundary");
        }
        this.activeGenomeIndex = activeGenomeIndex;
    }

    public int length() {
        return genome.size();
    }

    public void setActiveGenNewDirection(MoveDirection newDirection) {
        genome.set(activeGenomeIndex, newDirection);
    }

    public void setGenNewDirection (MoveDirection moveDirection, int idx){
        genome.set(idx,moveDirection);
    }

    public ArrayList<Integer> genomeToInt() {
        ArrayList<Integer> intGenome = new ArrayList<>();
        for(MoveDirection gen : genome){
            switch (gen) {
                case NORTH -> intGenome.add(0);
                case NORTH_EAST -> intGenome.add(1);
                case EAST -> intGenome.add(2);
                case SOUTH_EAST -> intGenome.add(3);
                case SOUTH -> intGenome.add(4);
                case SOUTH_WEST -> intGenome.add(5);
                case WEST -> intGenome.add(6);
                case NORTH_WEST -> intGenome.add(7);
            };
        }
        return intGenome;
    }
    public String toString(){
        ArrayList<Integer> intGenome = genomeToInt();
        return intGenome.toString();
    }
}
