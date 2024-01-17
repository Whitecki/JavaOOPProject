package agh.ics.oop.model.animal;

import java.util.ArrayList;

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
}
