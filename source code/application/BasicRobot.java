package application;

import javafx.scene.paint.Color;
import java.util.Random;

/**
 * The BasicRobot class represents a simple robot with basic movement logic.
 * This robot moves in the direction it is facing and, if it hits a boundary,
 * it will randomly change its direction to stay within the boundaries.
 *
 * @author SHEN FANGJIE]
 * @version 1.0
 * @see Robot
 */
    public class BasicRobot extends Robot {

    /**
     * Constructs a new BasicRobot object with specified starting position, direction, and color.
     * The BasicRobot uses blue color by default.
     * Inherits from the abstract class robot
     * 
     * @param x        the initial X coordinate of the robot
     * @param y        the initial Y coordinate of the robot
     * @param direction the initial direction of the robot
     */
    public BasicRobot(int x, int y, Direction direction) {
        super(x, y, direction, Color.BLUE);  // BasicRobot uses blue color
    }

    /**
     * Moves the robot one step forward in its current direction.
     * If moving would cause the robot to go out of bounds, it will randomly adjust its direction
     * and be constrained within the boundary.
     */
    @Override
    public void move() {
        // Implement basic movement logic
        switch (getDirection()) {
            case UP:
                setY(getY() - 1);
                break;
            case DOWN:
                setY(getY() + 1);
                break;
            case LEFT:
                setX(getX() - 1);
                break;
            case RIGHT:
                setX(getX() + 1);
                break;
        }

        // Ensure the robot does not move out of bounds
        if (getX() < 1 || getX() >= 17 || getY() < 1 || getY() >= 17) {
            setDirection(Direction.random());
            setX(Math.max(3, Math.min(getX(), 17))); // Set X to be within [3, 15]
            setY(Math.max(3, Math.min(getY(), 17))); // Set Y to be within [3, 15]
        }
    }

    /**
     * Resets the state of the robot to its initial state.
     * This method calls the reset method from the parent class {@link Robot}.
     */
    @Override
    public void reset() {
        super.reset();
    }

    /**
     * Creates a new BasicRobot object with random position and direction.
     *
     * @param maxX the maximum X coordinate (exclusive), determining the possible maximum X position of the robot
     * @param maxY the maximum Y coordinate (exclusive), determining the possible maximum Y position of the robot
     * @return a newly created BasicRobot object
     */
    public static BasicRobot createRandom(int maxX, int maxY) {
        int x = new Random().nextInt(maxX);
        int y = new Random().nextInt(maxY);
        Direction direction = Direction.values()[new Random().nextInt(Direction.values().length)];
        return new BasicRobot(x, y, direction);
    }
}