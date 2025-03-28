package oop.model.util;

public enum MutationConfiguration {
    COMPLETE_RANDOMNESS(1, "1 - Complete randomness"),
    WITH_RANDOM_SWAPS(2, "2 - With random swaps");

    private final int id;

    private final String description;

    MutationConfiguration(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
