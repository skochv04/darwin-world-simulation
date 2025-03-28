package oop;

import oop.model.*;
import oop.model.util.MutationConfiguration;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class World {
    private final static int WIDTH = 15;
    private final static int HEIGHT = 20;
    private final static int GENOME_LENGTH = 7;

    private final static int START_ENERGY = 30;

    public static void main(String[] args) throws IllegalArgumentException {
        try {
            AbstractWorldMap map;

            Scanner scanner = new Scanner(System.in);

            // Map choice
            System.out.println("Mapa");
            System.out.println("1 - kula ziemska");
            System.out.println("2 - piekielny portal");
            System.out.print("Wybór mapy: ");
            if (scanner.hasNextInt()) {
                int in = scanner.nextInt();
                map = switch (in) {
                    case 1 -> new EarthMap(WIDTH, HEIGHT);
                    case 2 -> new HellPortalMap(WIDTH, HEIGHT);
                    default -> throw new IllegalArgumentException("Invalid input: " + in);
                };
            }

            System.out.println();

            // Gene mutation choice
            System.out.println("Mutacja");
            System.out.println("1 - pełna losowość");
            System.out.println("2 - podmianka");
            System.out.print("Wybór sposobu mutacji: ");

            MutationConfiguration mutationModifier = MutationConfiguration.COMPLETE_RANDOMNESS;
            if (scanner.hasNextInt()) {
                int in = scanner.nextInt();
                mutationModifier = switch (in) {
                    case 1 -> MutationConfiguration.COMPLETE_RANDOMNESS;
                    case 2 -> MutationConfiguration.WITH_RANDOM_SWAPS;
                    default -> throw new IllegalArgumentException("Invalid input: " + in);
                };
            }

            Random random = new Random();

            List<Animal> animals = List.of(
                    new Animal(new Vector2d(random.nextInt(WIDTH), random.nextInt(HEIGHT)), new Genome(GENOME_LENGTH), START_ENERGY),
                    new Animal(new Vector2d(random.nextInt(WIDTH), random.nextInt(HEIGHT)), new Genome(GENOME_LENGTH), START_ENERGY),
                    new Animal(new Vector2d(random.nextInt(WIDTH), random.nextInt(HEIGHT)), new Genome(GENOME_LENGTH), START_ENERGY)
            );
            SimulationOptions options = new SimulationOptions(0, 0, 0, 0, 0,0, 0, 12,10 ,10, false);
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
