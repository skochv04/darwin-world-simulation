package oop.model;

import oop.model.util.ConflictResolver;
import oop.model.util.MapVisualizer;
import oop.model.util.MutationConfiguration;

import java.util.*;

/**
 * Common functionality for all types of maps
 */
public abstract class AbstractWorldMap implements WorldMap {
    protected final int width;
    protected final int height;
    protected final Map<Vector2d, Grass> grass = new HashMap<>();
    private final List<MapChangeListener> listeners = new ArrayList<>();
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();

    private int[] freeOfGrass;
    private int freeFields;

    public AbstractWorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        freeOfGrass = new int[height];
        Arrays.setAll(freeOfGrass, i-> width);
        freeFields = width * height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(new Vector2d(0, 0), new Vector2d(width, height));
    }

    @Override
    public void place(Animal animal) {
        Vector2d position = animal.getPosition();

        if (canMoveTo(position)) {
            List<Animal> animalList = animals.get(position);

            if (animalList == null) {
                animalList = new ArrayList<>();
                animalList.add(animal);
                animals.put(position, animalList);
            } else {
                animalList.add(animal);
            }
        }
    }

    public Grass grassAt(Vector2d position) {
        return grass.get(position);
    }

    public List<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = new ArrayList<>();
        for (Vector2d key : animals.keySet()) elements.addAll(animals.get(key));
        for (Vector2d key : grass.keySet()) elements.add(grass.get(key));
        return elements;
    }

    public Map<Vector2d, List<Animal>> getAnimals() {
        return animals;
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        return grassAt(position) != null || (animalsAt(position) != null && !animalsAt(position).isEmpty());
    }

    @Override
    public boolean move(Animal animal, int energy) {
        Vector2d prevPosition = animal.getPosition();

        if (animal.move(this)) {
            animals.get(prevPosition).remove(animal);
            place(animal);
            return true;
        }
        return false;
    }

    public void generateGrass(int grassCount) {
//        int grassCount = (int) Math.floor(0.33 * width * height);
        Random random = new Random();

        for (int i = 0; i < grassCount; i++) {
            Vector2d position = new Vector2d(random.nextInt(width), random.nextInt(height));

            while (grassAt(position) != null) {
                position = new Vector2d(random.nextInt(width), random.nextInt(height));
            }

            grass.put(position, new Grass(position));
            freeOfGrass[position.getY()]--;
            freeFields--;
        }
    }

    public void subscribe(MapChangeListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(MapChangeListener listener) {
        listeners.remove(listener);
    }

    public void notifySubscribers() {
        listeners.forEach(listener -> listener.mapChanged(this));
    }

    /**
     * If animal died then remove it, if not then reduce energy and increase age because the new day started.
     */
    public boolean removeDied(Animal animal, int day) {
        if (animal.getEnergy() <= 0) {
            animals.get(animal.getPosition()).remove(animal);
            animal.setDeathDay(day);
            return true;
        } else {
            animal.reduceEnergy(1);
            animal.addAge();
            return false;
        }
    }

    public void consumption(int energyConsumption) {
        for (Vector2d key : animals.keySet()) {
            List<Animal> animalsOnPosition = animals.get(key);
            if (grass.get(key) != null && !animalsOnPosition.isEmpty()) {
                Animal animal = animalsOnPosition.get(0);
                if (animalsOnPosition.size() > 1) animal = ConflictResolver.resolveConflict(animalsOnPosition, 0);
                animal.increaseEnergy(energyConsumption);
                animal.increaseEaten();
                grass.remove(key);
                freeOfGrass[key.getY()]++;
                freeFields++;
            }
        }
    }

    public List<Animal> reproduction(List<Animal> animalsList, int energyRequired, int energyReproduction, MutationConfiguration mutationModifier, SimulationStatistics statistics, int minMutation, int maxMutation) {
        for (Vector2d key : animals.keySet()) {
            List<Animal> animalsOnPosition = animals.get(key);
            List<Animal> fedAnimals = animalsOnPosition.stream()
                    .filter(animal -> animal.getEnergy() >= energyRequired)
                    .toList();
            if (fedAnimals.size() > 1) {
                Animal parent1 = fedAnimals.get(0);
                Animal parent2 = fedAnimals.get(1);
                if (fedAnimals.size() > 2) {
                    parent1 = ConflictResolver.resolveConflict(animalsOnPosition, 0);
                    parent2 = ConflictResolver.resolveConflict(animalsOnPosition, 1);
                }
                parent1.reduceEnergy(energyReproduction);
                parent2.reduceEnergy(energyReproduction);
                Animal descendant = new Animal(key, parent1, parent2, energyReproduction * 2, mutationModifier, minMutation, maxMutation);
                statistics.onAnimalCreated(descendant);
                parent1.increaseChildren();
                parent2.increaseChildren();
                animalsList.add(descendant);
            }
        }
        return animalsList;
    }

    private Vector2d getGrassPosition(boolean equator) {
        Random random = new Random();
        int x, y;
        if (equator) {
            do {
                x = random.nextInt(width);
                y = (int) (0.4 * height + random.nextDouble() * 0.2 * height);
            } while (grassAt(new Vector2d(x, y)) != null && freeOfGrass[y] != 0);
        } else {
            do {
                x = random.nextInt(width);
                y = Math.random() >= 0.5 ? random.nextInt((int) (0.4 * height)) : (int) (0.6 * height + random.nextDouble() * 0.4 * height);
            } while (grassAt(new Vector2d(x, y)) != null && freeFields != 0);
        }

        return new Vector2d(x, y);
    }

    public void growGrass(int grassCount) {
        for (int i = 0; i < grassCount; i++) {
            Vector2d position = getGrassPosition(Math.random() < 0.8);
            if (grassAt(position) == null) {
                grass.put(position, new Grass(position));
                freeOfGrass[position.getY()]--;
                freeFields--;
            }
        }
    }
}