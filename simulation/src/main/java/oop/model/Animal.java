package oop.model;

import oop.model.util.AnimalStatistics;
import oop.model.util.MutationConfiguration;

import java.util.Random;

import static oop.model.MoveDirection.geneToDirection;

public class Animal implements WorldElement {
    private final Genome genome;
    private final AnimalStatistics statistics = new AnimalStatistics();
    private Vector2d position;
    private int direction;
    private int energy;
    private int activeIndex;

    /**
     * Constructor for start animals
     */
    public Animal(Vector2d position, Genome genome, int energy) {
        this.position = position;
        this.genome = genome;
        this.energy = energy;
        Random random = new Random();
        this.direction = random.nextInt(MoveDirection.values().length);
        this.activeIndex = random.nextInt(genome.getGenomeSize());
    }

    /**
     * Constructor for animals obtained as a result of reproduction
     */

    public Animal(Vector2d position, Animal parent1, Animal parent2, int energy, MutationConfiguration mutationModifier, int minMutation, int maxMutation) {
        this.position = position;
        this.energy = energy;
        Random random = new Random();
        this.direction = random.nextInt(MoveDirection.values().length);

        // Find random start side for future genome cross
        String side = (random.nextInt(2) == 0) ? "LEFT" : "RIGHT";

        // Find percent for more hungry parent: it will be useful for future genome cross
        double totalEnergy = parent1.getEnergy() + parent2.getEnergy();
        double strongerPercent = 0;

        // Set parents due to their energy supply, left parent will represent the parent with more energy supply
        this.statistics.setParentLeft(parent1.getEnergy() > parent2.getEnergy() ? parent1 : parent2);
        this.statistics.setParentRight(parent1.getEnergy() > parent2.getEnergy() ? parent2 : parent1);

        // Calculate percent of stronger animal supply to find a position to combine parents genomes
        strongerPercent = statistics.getParentLeft().getEnergy() / (totalEnergy / 100) / 100;

        // Generete genome as a cross of parent`s genome, using side and percent
        this.genome = new Genome(statistics.getParentLeft().getGenome(), statistics.getParentRight().getGenome(), side, strongerPercent, mutationModifier, minMutation, maxMutation);
        this.activeIndex = random.nextInt(genome.getGenomeSize());
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    @Override
    public String toString() {
        return switch (direction) {
            case 0 -> "^ " + getEnergy();
            case 1 -> "^> " + getEnergy();
            case 2 -> "> " + getEnergy();
            case 3 -> "v> " + getEnergy();
            case 4 -> "v " + getEnergy();
            case 5 -> "<v " + getEnergy();
            case 6 -> "< " + getEnergy();
            case 7 -> "<^ " + getEnergy();
            default -> "";
        };
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public AnimalStatistics getStatistics() {
        return statistics;
    }

    public Genome getGenome() {
        return genome;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public boolean move(MoveValidator map) {
        activeIndex = (activeIndex + 1) % genome.getGenomeSize();
        this.direction = (this.direction + this.genome.getGenome().get(activeIndex)) % MoveDirection.values().length;

        if (map.canMoveTo(this.position.add(geneToDirection(this.direction).toUnitVector()))) {
            this.position = this.position.modAdd(geneToDirection(this.direction).toUnitVector(), map.getWidth());
            return true;
        }
        return false;
    }

    public void reduceEnergy(int n) {
        this.energy -= n;
    }

    public void increaseEnergy(int n) {
        this.energy += n;
    }

    public void addAge() {
        this.statistics.addAge();
    }

    public void setDeathDay(int day) {
        this.statistics.setDeathDay(day);
    }

    public void increaseEaten() {
        this.statistics.increaseEaten();
    }

    public void increaseChildren() {
        this.statistics.increaseChildren();
    }

    public int getChildrenAmount() {
        return this.statistics.getChildrenAmount();
    }

    public void setOppositeDirection() {
        this.direction = genome.opposite(this.direction);
    }

    public void teleport(int height, int width) {
        Random random = new Random();
        this.position = new Vector2d(random.nextInt(width), random.nextInt(height));
    }

    public int getDirection() {
        return direction;
    }
}
