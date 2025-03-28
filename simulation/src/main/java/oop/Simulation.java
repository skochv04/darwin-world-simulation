package oop;

import oop.model.*;
import oop.model.util.CSVWriter;
import oop.model.util.MutationConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Simulation implements Runnable {
    private UUID id;
    private final AbstractWorldMap map;
    private List<Animal> animals = new ArrayList<>();
    private final SimulationOptions options;
    private final SimulationStatistics statistics;
    private final MutationConfiguration mutationModifier;
    private boolean isPaused = false;
    private CSVWriter csvWriter;

    public Simulation(AbstractWorldMap map, MutationConfiguration mutationModifier, SimulationOptions options) {
        this.id = UUID.randomUUID();
        this.map = map;
        this.mutationModifier = mutationModifier;
        this.statistics = new SimulationStatistics(map);
        this.options = options;

        // Create an instance of the CSV writer
        if (options.saveToCSV()) {
            this.csvWriter = new CSVWriter(this.id, this.statistics);
        }

        // Register new map listener
        MapChangeListener listener = new ConsoleMapDisplay();
        map.subscribe(listener);

        // Generate grass
        map.generateGrass(options.grassStart());

        // Generate animals
        generateAnimals();

        // Place animals at their initial positions
        animals.forEach(map::place);
    }

    public SimulationStatistics getStatistics() {
        return statistics;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void switchPause() {
        isPaused = !isPaused;
    }

    public void generateAnimals(){
        Random random = new Random();
        for (int i = 0; i < options.animalsStart(); i++){
            animals.add(new Animal(new Vector2d(random.nextInt(map.getWidth()), random.nextInt(map.getHeight())), new Genome(options.genomeSize()), options.energyStart()));
        }
    }

    public void run() {
        int day = 0;
        while (true){
            // Remove died animals
            int finalDay = day;
            animals.removeIf(animal -> {
                boolean isDead = map.removeDied(animal, finalDay);

                if (isDead) {
                    statistics.onAnimalDeath(animal);
                }

                return isDead;
            });

            // Move all animals according to their directions
            animals.forEach(animal -> map.move(animal, options.energyReproduction()));

            // Consumption of plants where animals have entered the fields
            map.consumption(options.energyConsumption());

            // Reproduction of fully fed animals in the same field
            animals = map.reproduction(animals, options.energyRequired(), options.energyReproduction(), mutationModifier, statistics, options.minMutation(), options.maxMutation());

            // Growing new plants
            map.growGrass(options.grassPerDay());

            // Notify subscribers
            map.notifySubscribers();

            // Update simulation statistics
            statistics.onSimulationStep();

            // Append daily statistics to the file if such option was selected
            if (options.saveToCSV()) {
                csvWriter.write(day);
            }

            // Wait 1.25s for better readability
            try {
                Thread.sleep(1250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            day++;
            while (isPaused) {
                try {
                    Thread.sleep(100); // Чекаємо 100 мілісекунд перед наступною перевіркою
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
