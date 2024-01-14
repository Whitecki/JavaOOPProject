package agh.ics.oop.simulation;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomUniqueValues {
    public static Set<Integer> generateRandomUniqueValues(int min, int max, int count) {
        if (max - min + 1 < count) {
            throw new IllegalArgumentException("Zakres jest zbyt mały do wygenerowania wymaganej liczby unikalnych wartości.");
        }

        Random random = new Random();
        Set<Integer> uniqueValues = new HashSet<>();

        while (uniqueValues.size() < count) {
            int randomValue = random.nextInt(max - min + 1) + min;
            uniqueValues.add(randomValue);
        }

        return uniqueValues;
    }

    public static void main(String[] args) {
        Set<Integer> randomValues = generateRandomUniqueValues(1, 100, 10); // Przykład: generuj 10 unikalnych wartości z przedziału 1-100
        System.out.println(randomValues);
    }
}
