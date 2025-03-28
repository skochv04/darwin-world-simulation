package oop;

public record SimulationOptions (
    /**
     * Start amount of grass on the map
     */
    int grassStart,
    /**
     * Amount of grass growing every day
     */
    int grassPerDay,
    /**
     * Start amount of animals on the map
     * */
    int animalsStart,
    /**
     * Animals genome size
     * */
    int genomeSize,
    /**
     * Start amount of energy for each animal
     */
    int energyStart,
    /**
     * Amount of energy got after consumption
     */
    int energyConsumption,
    /**
     * Amount of energy required to take part in reproduction
     */
    int energyRequired,
    /**
     * Amount of energy reduce in reproduction
     */
    int energyReproduction,
    /**
     * Minimal amount of descendant mutations
     */
    int minMutation,
    /**
     * Maximum amount of descendant mutations
     */
    int maxMutation,
    /**
     * Whether or not daily statistics should be saved to a .csv file
     */
    boolean saveToCSV
){}
