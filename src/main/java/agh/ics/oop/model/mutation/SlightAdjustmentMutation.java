package agh.ics.oop.model.mutation;

import agh.ics.oop.model.animal.Genome;

import java.util.Random;

public class SlightAdjustmentMutation implements MutationStrategy{
    @Override
    public void mutate(Genome genome, int indexOfGenToMutate) {
        Random random = new Random();
        if (random.nextInt(2) == 1){
            genome.setActiveGenNewDirection(genome.getActiveGen().next());
        }
        else {genome.setActiveGenNewDirection(genome.getActiveGen().previous());}
    }
}
