package oop.model;

import oop.model.*;
import oop.model.util.MutationConfiguration;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void testAnimalInitialization() {
        Vector2d position = new Vector2d(2, 3);
        Genome genome = new Genome(15);
        int energy = 50;

        Animal animal = new Animal(position, genome, energy);

        assertEquals(position, animal.getPosition());
        assertEquals(genome, animal.getGenome());
        assertEquals(energy, animal.getEnergy());
        assertNotNull(animal.getStatistics());
        assertTrue(animal.getActiveIndex() >= 0 && animal.getActiveIndex() < genome.getGenomeSize());
    }

    @Test
    void testReproduction() {
        Vector2d position = new Vector2d(2, 3);
        Genome parent1Genome = new Genome(10);
        Genome parent2Genome = new Genome(10);
        Animal parent1 = new Animal(position, parent1Genome, 80);
        Animal parent2 = new Animal(position, parent2Genome, 60);
        MutationConfiguration mutationModifier = MutationConfiguration.COMPLETE_RANDOMNESS;
        int minMutation = 1;
        int maxMutation = 3;

        Animal child = new Animal(position, parent1, parent2, 100, mutationModifier, minMutation, maxMutation);

        assertNotNull(child.getGenome());
        assertNotEquals(parent1.getGenome(), child.getGenome());
        assertNotEquals(parent2.getGenome(), child.getGenome());
        assertTrue(child.getActiveIndex() >= 0 && child.getActiveIndex() < child.getGenome().getGenomeSize());
    }

    @Test
    void testMove() {
        Vector2d position = new Vector2d(2, 3);
        Genome genome = new Genome(10);
        int energy = 50;
        Animal animal = new Animal(position, genome, energy);
        AbstractWorldMap map = new EarthMap(5, 5);
        Vector2d initialPosition = animal.getPosition();
        int initialDirection = animal.getDirection();
        int initialEnergy = animal.getEnergy();

        boolean moved = animal.move(map);

        if (moved) {
            assertNotEquals(initialPosition, animal.getPosition());
            assertNotEquals(initialDirection, animal.getDirection());
            assertTrue(initialEnergy >= animal.getEnergy());
        } else {
            assertEquals(initialPosition, animal.getPosition());
            assertEquals(initialDirection, animal.getDirection());
            assertEquals(initialEnergy, animal.getEnergy());
        }
    }
}