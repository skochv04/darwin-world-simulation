package oop.model.util;

public enum MapConfiguration {
    EARTH_MAP(1, "1 - EarthMap"),
    HELL_PORTAL_MAP(2, "2 - HellPortalMap");

    private final int id;

    private final String description;

    MapConfiguration(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
