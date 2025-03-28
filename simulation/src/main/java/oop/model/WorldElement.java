package oop.model;
public interface WorldElement{

    /**
     * Represents object as a string on the map
     */
    String toString();

    /**
     * Returns position of an object
     * @return position of an object
     */
    Vector2d getPosition();
    /**
     * Check if object is at the given position
     * @param position The position on a map
     * @return True if object is at the given position
     */
    boolean isAt(Vector2d position);
}
