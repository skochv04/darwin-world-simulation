package oop.model;

import java.util.List;
//import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     * @return Throws exception if the animal wasn't placed. The animal cannot be placed if the move is not valid.
     */
    void place(Animal animal);

    /**
     * Moves an animal (if it is present on the map) according to its genome.
     * If the move is not possible, this method has no effect.
     */
    boolean move(Animal animal, int energy);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return a grass at a given position.
     *
     * @param position The position of the grass.
     * @return object or null if the position is not occupied.
     */
    WorldElement grassAt(Vector2d position);

    /**
     * Return list of animals present at a given position.
     *
     * @param position The position of an animal.
     * @return object or null if the position is not occupied.
     */
    List<Animal> animalsAt(Vector2d position);

    /**
     * Return a collection of all objects on the map

     @return collection of object on the map
     */
    List<WorldElement>  getElements();

//    public UUID getId();

    void subscribe(MapChangeListener listener);
}
