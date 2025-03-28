package oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import oop.model.util.MutationConfiguration;

public class Genome {
    private final List<Integer> genome = new ArrayList<>();
    private final int genomeSize;

    /**
     * Constructor for start genomes
     */
    public Genome(int N) {
        this.genomeSize = N;
        Random random = new Random();
        for (int i = 0; i < genomeSize; i++) {
            genome.add(random.nextInt(MoveDirection.values().length));
        }
    }

    /**
     * Constructor for genomes, obtained as a cross of their parents genomes
     */

    public Genome(Genome genome1, Genome genome2, String side, double strongerPercent, MutationConfiguration mutationModifier, int minMutation, int maxMutation) {
        this.genomeSize = genome1.genomeSize;
        // żeby nie leciał bład, poruwnujemy z genomeSize, chociaż bez tego logika wydaje się prawidłowa
        if (strongerPercent < 0) System.out.println(strongerPercent);
        int pos = Math.min ((int) Math.floor(this.genomeSize * strongerPercent), genomeSize);
        if (side.equals("LEFT")) {
            for (int i = 0; i < pos; i++) {
                genome.add(genome1.getGenome().get(i));
            }
            for (int i = pos; i < genomeSize; i++) {
                genome.add(genome2.getGenome().get(i));
            }
        } else if (side.equals("RIGHT")) {
            for (int i = 0; i < pos; i++) {
                genome.add(genome2.getGenome().get(i));
            }
            for (int i = pos; i < genomeSize; i++) {
                genome.add(genome1.getGenome().get(i));
            }
        }

        Random random = new Random();
        int mutationAmount;
        System.out.println(minMutation + maxMutation);
        if (minMutation == 0 && maxMutation == 0) mutationAmount = 0;
        else if (minMutation == 0) mutationAmount = random.nextInt(maxMutation);
        else mutationAmount = random.nextInt(minMutation, maxMutation);
        switch (mutationModifier){
            case COMPLETE_RANDOMNESS -> randomMutation(mutationAmount);
            case WITH_RANDOM_SWAPS -> replaceMutation(mutationAmount);
        }
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public int getGenomeSize() {
        return genomeSize;
    }

    public int opposite(int gene) {
        return (gene + (MoveDirection.values().length / 2) ) % MoveDirection.values().length;
    }

    public void randomMutation(int mutationAmount) {
        Random random = new Random();
        for (int i = 0; i < mutationAmount + 1; i++) {
            int position = random.nextInt(genomeSize);
            genome.set(position, random.nextInt(MoveDirection.values().length));
        }
    }

    public void replaceMutation(int mutationAmount) {
        Random random = new Random();
        for (int i = 0; i < mutationAmount + 1; i++) {
            int a = 0, b = 0;
            while (a == b) {
                a = random.nextInt(genomeSize);
                b = random.nextInt(genomeSize);
            }
            Collections.swap(genome, a, b);
        }
    }

    @Override
    public String toString() {
        return "<" + genome.stream().map(Object::toString).collect(Collectors.joining(", ")) + ">";
    }
}
