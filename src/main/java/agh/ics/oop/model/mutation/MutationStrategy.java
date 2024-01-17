package agh.ics.oop.model.mutation;
import agh.ics.oop.model.animal.Genome;
public interface MutationStrategy {
    void mutate(Genome genome, int indexOfGenToMutate);
}
