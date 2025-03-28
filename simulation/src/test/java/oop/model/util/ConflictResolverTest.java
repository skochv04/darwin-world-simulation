package oop.model.util;
import oop.model.Animal;
import oop.model.Genome;
import oop.model.Vector2d;
import oop.model.util.ConflictResolver;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConflictResolverTest {

    @Test
    void resolveConflict_shouldReturnWinnerBasedOnEnergy() {
        List<Animal> animals = new ArrayList<>();
        Animal animal1 = new Animal(new Vector2d(1, 2), new Genome(4), 3);
        Animal animal2 = new Animal(new Vector2d(2, 4),  new Genome(4), 4);
        animals.add(animal1);
        animals.add(animal2);

        Animal winner = ConflictResolver.resolveConflict(animals, 0);

        assertEquals(animal2, winner);
    }

    @Test
    void resolveConflict_shouldReturnWinnerBasedOnChildrenAmount() {
        List<Animal> animals = new ArrayList<>();
        Animal animal1 = new Animal(new Vector2d(1, 2), new Genome(4), 3);
        Animal animal2 = new Animal(new Vector2d(2, 4),  new Genome(4), 5);
        animals.add(animal1);
        animals.add(animal2);

        Animal winner = ConflictResolver.resolveConflict(animals, 0);

        assertEquals(animal2, winner);
    }

    @Test
    void resolveConflict_shouldReturnRandomWinnerOnTie() {
        List<Animal> animals = new ArrayList<>();
        Animal animal1 = new Animal(new Vector2d(1, 2), new Genome(4), 3);
        Animal animal2 = new Animal(new Vector2d(2, 4),  new Genome(4), 5);
        animals.add(animal1);
        animals.add(animal2);

        Animal winner = ConflictResolver.resolveConflict(animals, 0);

        assertTrue(animals.contains(winner));
    }
}