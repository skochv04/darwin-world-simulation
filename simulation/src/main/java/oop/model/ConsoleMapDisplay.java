package oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int eventId = 0;

    @Override
    public void mapChanged(WorldMap map) {
        System.out.println("#" + eventId);
        System.out.println(map);
        eventId += 1;
    }
}
