package agh.ics.oop.model.mutation;

import agh.ics.oop.model.animal.Genome;

import java.util.Random;

public class RandomMutation implements MutationStrategy {
    @Override
    public void mutate(Genome genome, int indexOfGenToMutate) {
        genome.setGenNewDirection(genome.getGenome().get(indexOfGenToMutate).randomOtherThanThis(),indexOfGenToMutate);
    }
}
