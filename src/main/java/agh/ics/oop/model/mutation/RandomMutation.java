package agh.ics.oop.model.mutation;

import agh.ics.oop.model.animal.Genome;
import agh.ics.oop.model.animal.MoveDirection;

import java.util.Random;

public class RandomMutation implements MutationStrategy {
    @Override
    public void mutate(Genome genome, int indexOfGenToMutate) {
        Random random = new Random();
        genome.setGenNewDirection(genome.getGenome().get(indexOfGenToMutate).randomOtherThanThis(),indexOfGenToMutate);
    }
}
