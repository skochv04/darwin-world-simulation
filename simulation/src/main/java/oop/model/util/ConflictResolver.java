package oop.model.util;

import oop.model.Animal;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ConflictResolver {
    /**
     * Resolves conflict of consumption or reproduction.
     *
     * @param animalsOnPosition list of animals on some position
     * @param iteration         cycle of choosing a winner, means "choose a winner on this position"
     */
    public static Animal resolveConflict(List<Animal> animalsOnPosition, int iteration) {
        animalsOnPosition.sort(Comparator.comparing(Animal::getEnergy).reversed());
        if (animalsOnPosition.get(iteration).getEnergy() != animalsOnPosition.get(iteration + 1).getEnergy())
            return animalsOnPosition.get(iteration);
        animalsOnPosition.sort(Comparator.comparing(Animal::getChildrenAmount).reversed());
        if (animalsOnPosition.get(iteration).getChildrenAmount() != animalsOnPosition.get(iteration + 1).getChildrenAmount())
            return animalsOnPosition.get(iteration);
        else {
            Random random = new Random();
            int index = random.nextInt(animalsOnPosition.size());
            return animalsOnPosition.get(index);
        }
    }
}
