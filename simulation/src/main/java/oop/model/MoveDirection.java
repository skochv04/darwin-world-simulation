package oop.model;

public enum MoveDirection {
    NORTH(0, "Północ", new Vector2d(0, 1)),
    NORTHEAST(1, "Północny wschód", new Vector2d(1, 1)),
    EAST(2, "Wschód", new Vector2d(1, 0)),
    SOUTHEAST(3, "Południowy wschód", new Vector2d(1, -1)),
    SOUTH(4, "Południe", new Vector2d(0, -1)),
    SOUTHWEST(5, "Półudniowy zachód", new Vector2d(-1, -1)),
    WEST(6, "Zachód", new Vector2d(-1, 0)),
    NORTHWEST(7, "Północny zachód", new Vector2d(-1, 1));

    private final int gene;
    private final String description;
    private final Vector2d unitVector;

    MoveDirection(int gene, String description, Vector2d unitVector) {
        this.gene = gene;
        this.description = description;
        this.unitVector = unitVector;
    }

    public static MoveDirection geneToDirection(int direction) {
        return switch (direction) {
            case 0 -> NORTH;
            case 1 -> NORTHEAST;
            case 2 -> EAST;
            case 3 -> SOUTHEAST;
            case 4 -> SOUTH;
            case 5 -> SOUTHWEST;
            case 6 -> WEST;
            case 7 -> NORTHWEST;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }

    public String toString() {
        return description;
    }

    public MoveDirection next() {
        int ordinal = this.ordinal();
        if (ordinal < MoveDirection.values().length - 1) return MoveDirection.values()[ordinal + 1];
        else return MoveDirection.values()[0];
    }

    public MoveDirection previous() {
        int ordinal = this.ordinal();
        if (ordinal > 0) return MoveDirection.values()[ordinal - 1];
        else return MoveDirection.values()[MoveDirection.values().length - 1];
    }

    public Vector2d toUnitVector() {
        return unitVector;
    }
}