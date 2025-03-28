package oop.model;

public class HellPortalMap extends AbstractWorldMap{
    /**If an animal goes beyond the edge of the map, it goes to a magical portal; its energy decreases by a certain amount
     * (the same as in the case of offspring generation), and then it is teleported to a new, random free place on the map
     */
    public HellPortalMap(int width, int height) {
        super(width, height);
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getY() >= 0 && position.getY() < height && position.getX() >= 0 && position.getY() < width;
    }

    /** Move on the HellPortalMap: animals cannot cross up any border, crossing it leads to entry HellPortal*/
    @Override
    public boolean move(Animal animal, int energy) {
        Vector2d prevPosition = animal.getPosition();
        if (!super.move(animal, energy)) {
            animal.reduceEnergy(energy);
            animals.get(prevPosition).remove(animal);
            animal.teleport(height, width);
            place(animal);
        }
        return true; // always, because boolean is needed only in AbstractWorldMap
    }
}
