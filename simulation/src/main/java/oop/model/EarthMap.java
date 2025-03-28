package oop.model;

public class EarthMap extends AbstractWorldMap {
/**The left and right edges of the map are looped (if the animal goes beyond the left edge, it will appear on the right
 * - and if it goes beyond the right, it will appear on the left); the upper and lower edges of the map are the poles -
 * you cannot enter them (if the animal tries to go beyond these edges of the map, it remains in the field it was on and
 * its direction changes to the opposite)
 */
public EarthMap(int width, int height) {
        super(width, height);
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getY() >= 0 && position.getY() < height;
    }

    /** Move on the EarthMap: animals cannot cross up and down border, crossing left or right border leads to
     * place on the opposite side of the map*/
    @Override
    public boolean move(Animal animal, int energy) {
        if (!super.move(animal, energy)) {animal.setOppositeDirection();}
        return true; // always, because boolean is needed only in AbstractWorldMap
    }
}
