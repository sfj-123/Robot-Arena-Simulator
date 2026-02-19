package application;

import java.util.Random;

/**
 * The Direction enum represents the possible directions a robot can face and move.
 * Each direction is associated with a change in X and Y coordinates (deltaX, deltaY) that defines
 * how the position changes when moving in that direction. It also provides a method to randomly
 * select a direction.
 *
 * @author [SHEN FANGJIE]
 * @version 1.0
 */
    public enum Direction {

    /**
     * Represents the UP direction, which decreases the Y coordinate by 1.
     */
    UP(0, -1),

    /**
     * Represents the DOWN direction, which increases the Y coordinate by 1.
     */
    DOWN(0, 1),

    /**
     * Represents the LEFT direction, which decreases the X coordinate by 1.
     */
    LEFT(-1, 0),

    /**
     * Represents the RIGHT direction, which increases the X coordinate by 1.
     */
    RIGHT(1, 0);

    /**
     * The change in the X coordinate when moving in this direction.
     */
    private final int deltaX;

    /**
     * The change in the Y coordinate when moving in this direction.
     */
    private final int deltaY;

    /**
     * Constructs a new Direction enum constant with specified changes in X and Y coordinates.
     *
     * @param deltaX the change in the X coordinate
     * @param deltaY the change in the Y coordinate
     */
    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Gets the change in the X coordinate for this direction.
     *
     * @return the change in the X coordinate
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Gets the change in the Y coordinate for this direction.
     *
     * @return the change in the Y coordinate
     */
    public int getDeltaY() {
        return deltaY;
    }

    /**
     * Returns a random direction from the available directions.
     *
     * @return a randomly selected Direction enum constant
     */
    public static Direction random() {
        return values()[new Random().nextInt(values().length)];
    }
}