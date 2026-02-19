package application;

import javafx.scene.paint.Color;

/**
 * The abstract Robot class serves as a base for all robot types, providing common properties and methods.
 * It defines the basic structure and behavior that all robots should have, including movement and reset functionality.
 *
 * @author [SHEN FANGJIE]
 * @version 1.0
 */
    public abstract class Robot {

    /**
     * A unique identifier for each robot instance.
     */
    protected int id;

    /**
     * A static counter to generate unique IDs for each new robot.
     */
    protected static int nextId = 0;

    /**
     * The current X coordinate of the robot.
     */
    protected int x;

    /**
     * The current Y coordinate of the robot.
     */
    protected int y;

    /**
     * The current direction the robot is facing.
     */
    protected Direction direction;

    /**
     * The initial X coordinate of the robot, used for resetting.
     */
    protected int initialX;

    /**
     * The initial Y coordinate of the robot, used for resetting.
     */
    protected int initialY;

    /**
     * The initial direction of the robot, used for resetting.
     */
    protected Direction initialDirection;

    /**
     * The color of the robot, used for visual representation.
     */
    protected Color color;  // 添加颜色属性

    /**
     * Constructs a new Robot object with specified starting position, direction, and color.
     *
     * @param x        the initial X coordinate of the robot
     * @param y        the initial Y coordinate of the robot
     * @param direction the initial direction of the robot
     * @param color    the color of the robot
     */
    public Robot(int x, int y, Direction direction, Color color) {
        this.id = nextId++;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.initialX = x;
        this.initialY = y;
        this.initialDirection = direction;
        this.color = color;  // 初始化颜色
    }

    /**
     * Gets the unique identifier of the robot.
     *
     * @return the ID of the robot
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the current X coordinate of the robot.
     *
     * @return the X coordinate of the robot
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X coordinate of the robot.
     *
     * @param x the new X coordinate of the robot
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the current Y coordinate of the robot.
     *
     * @return the Y coordinate of the robot
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y coordinate of the robot.
     *
     * @param y the new Y coordinate of the robot
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the current direction the robot is facing.
     *
     * @return the direction of the robot
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction the robot is facing.
     *
     * @param direction the new direction of the robot
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets the color of the robot.
     *
     * @return the color of the robot
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the robot.
     *
     * @param color the new color of the robot
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Moves the robot. This method must be implemented by subclasses to define specific movement logic.
     */
    public abstract void move();

    /**
     * Resets the robot to its initial state, including position and direction.
     */
    public void reset() {
        this.x = initialX;
        this.y = initialY;
        this.direction = initialDirection;
    }
}