package oop.model;

import java.util.*;

public class SimulationStatistics {
    private final AbstractWorldMap map;
    private int animalCount = 0;
    private int grassCount = 0;
    private int deadAnimalCount = 0;
    private int totalDaysAlive = 0;
    private int deadAnimalChildrenCount = 0;
    private final Map<Genome, Integer> genomeCount = new HashMap<>();

    public SimulationStatistics(AbstractWorldMap map) {
        this.map = map;
    }

    public int getAnimalCount() {
        return animalCount;
    }

    public int getGrassCount() {
        return grassCount;
    }

    public double getAverageChildren() {
        int totalChildren = map.animals.values().stream()
                .flatMap(List::stream)
                .mapToInt(animal -> animal.getStatistics().getChildrenAmount())
                .sum();


        return (deadAnimalChildrenCount + totalChildren) / (double) (deadAnimalCount + animalCount);
    }

    public int freeFieldCount() {
        return map.getWidth() * map.getHeight() - animalCount - grassCount;
    }

    public void onAnimalCreated(Animal animal) {
        Genome genome = animal.getGenome();

        if (genomeCount.containsKey(genome)) {
            genomeCount.put(genome, genomeCount.get(genome) + 1);
        } else {
            genomeCount.put(genome, 1);
        }
    }

    public void onAnimalDeath(Animal animal) {
        totalDaysAlive += animal.getStatistics().getDaysAlive();
        deadAnimalCount += 1;
        deadAnimalChildrenCount += animal.getStatistics().getChildrenAmount();
    }

    public double averageEnergy() {
        int totalEnergy = map.animals.values().stream()
                .flatMap(List::stream)
                .mapToInt(Animal::getEnergy)
                .sum();

        return totalEnergy / (double) animalCount;
    }

    public void onSimulationStep() {
        animalCount = map.animals.values().stream()
                .mapToInt(List::size)
                .sum();
        grassCount = map.grass.size();
    }

    public double averageLifespan() {
        if (totalDaysAlive == 0 && deadAnimalCount == 0) {
            return 0;
        }

        return totalDaysAlive / (double) deadAnimalCount;
    }

    public List<Map.Entry<Genome, Integer>> mostPopularGenomes() {
        List<Map.Entry<Genome, Integer>> genomeEntryList = new ArrayList<>(genomeCount.entrySet());
        genomeEntryList.sort(Map.Entry.<Genome, Integer>comparingByValue().reversed());
        return genomeEntryList;
    }
}
