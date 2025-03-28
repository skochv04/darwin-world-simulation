package oop.model;

import oop.model.util.MutationConfiguration;

public class GenomeTest {

    public static void main(String[] args) {
        testConstructor();
        testCrossGenomes();
        testRandomMutation();
        testReplaceMutation();
    }

    private static void testConstructor() {
        System.out.println("Testing Constructor...");

        int genomeSize = 10;
        Genome genome = new Genome(genomeSize);

        // Ensure the genome size is correct
        assert genome.getGenomeSize() == genomeSize;

        // Ensure all values are within the valid range
        for (int gene : genome.getGenome()) {
            assert gene >= 0 && gene < MoveDirection.values().length;
        }

        System.out.println("Constructor test passed.");
    }

    private static void testCrossGenomes() {
        System.out.println("Testing Cross Genomes...");

        int genomeSize = 10;
        Genome parent1 = new Genome(genomeSize);
        Genome parent2 = new Genome(genomeSize);

        double strongerPercent = 0.7;
        MutationConfiguration mutationModifier = MutationConfiguration.COMPLETE_RANDOMNESS;
        int minMutation = 1;
        int maxMutation = 3;

        Genome child = new Genome(parent1, parent2, "LEFT", strongerPercent, mutationModifier, minMutation, maxMutation);

        // Ensure the child's genome size is correct
        assert child.getGenomeSize() == genomeSize;

        System.out.println("Cross Genomes test passed.");
    }

    private static void testRandomMutation() {
        System.out.println("Testing Random Mutation...");

        int genomeSize = 10;
        Genome genome = new Genome(genomeSize);

        int mutationAmount = 3;
        genome.randomMutation(mutationAmount);

        System.out.println("Random Mutation test passed.");
    }

    private static void testReplaceMutation() {
        System.out.println("Testing Replace Mutation...");

        int genomeSize = 10;
        Genome genome = new Genome(genomeSize);

        int mutationAmount = 3;
        genome.replaceMutation(mutationAmount);

        System.out.println("Replace Mutation test passed.");
    }
}