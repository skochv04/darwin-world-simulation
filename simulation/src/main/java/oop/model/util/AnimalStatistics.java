package oop.model.util;

import oop.model.Animal;

public class AnimalStatistics {
    /**
     * Amount of
     */
    private int childrenAmount = 0;
    private int descendantAmount = 0;
    private int daysAlive = 0;
    private int plantsEaten = 0;
    private int deathDay = 0;
    /**
     * Represents parent with more energy supply
     */
    private Animal parentLeft = null;
    /**
     * Represents parent with less energy supply
     */
    private Animal parentRight = null;

    public int getChildrenAmount() {
        return childrenAmount;
    }
    public int getDaysAlive() {
        return daysAlive;
    }
    public int getDeathDay() {
        return deathDay;
    }
    public int getPlantsEaten() {
        return plantsEaten;
    }
    public void addAge() {
        daysAlive ++;
    }
    public void increaseChildren() {
        childrenAmount ++;
    }
    public void increaseEaten() {
        plantsEaten ++;
    }
    public void setDeathDay(int day) {
        this.deathDay = day;
    }

    public void setParentLeft(Animal parent) {
        this.parentLeft = parent;
    }
    public void setParentRight(Animal parent) {
        this.parentRight = parent;
    }

    public Animal getParentLeft() {
        return parentLeft;
    }

    public Animal getParentRight() {
        return parentRight;
    }
}
