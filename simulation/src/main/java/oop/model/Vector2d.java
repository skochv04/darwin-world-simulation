package oop.model;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y +")";
    }

    public boolean precedes(Vector2d other){
        return (this.x <= other.x && this.y <= other.y);
    }

    public boolean follows(Vector2d other){
        return (this.x >= other.x && this.y >= other.y);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }
    /** Special add, used in EarthMap to be placed on the other side of map*/
    public Vector2d modAdd(Vector2d other, int width){
        return new Vector2d(((this.x + other.x)  + width) % width, this.y + other.y);
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max (this.x, other.x), Math.max(this.y , other.y));
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min (this.x, other.x), Math.min(this.y , other.y));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Vector2d)) return false;
        Vector2d that = (Vector2d) other;
        return (that.x == this.x && that.y == this.y);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
