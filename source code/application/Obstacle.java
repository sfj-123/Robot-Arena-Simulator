package application;

import javafx.scene.paint.Color;

/**
 * The Obstacle class represents an obstacle in the robot arena.
 * An obstacle has a fixed position on the grid and a color for visual representation.
 *
 * @author [SHEN FANGJIE]
 * @version 1.0
 */
    public class Obstacle {

    /**
     * The X coordinate of the obstacle's position on the grid.
     */
    private final int x;

    /**
     * The Y coordinate of the obstacle's position on the grid.
     */
    private final int y;

    /**
     * The color of the obstacle, used for visual representation.
     */
    private final Color color;

    /**
     * Constructs a new Obstacle object with specified position and default color (black).
     *
     * @param x the X coordinate of the obstacle
     * @param y the Y coordinate of the obstacle
     */
    public Obstacle(int x, int y) {
        this(x, y, Color.BLACK);  // Default color is black
    }

    /**
     * Constructs a new Obstacle object with specified position and color.
     *
     * @param x     the X coordinate of the obstacle
     * @param y     the Y coordinate of the obstacle
     * @param color the color of the obstacle
     */
    public Obstacle(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Gets the X coordinate of the obstacle.
     *
     * @return the X coordinate of the obstacle
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y coordinate of the obstacle.
     *
     * @return the Y coordinate of the obstacle
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the color of the obstacle.
     *
     * @return the color of the obstacle
     */
    public Color getColor() {
        return color;
    }
}