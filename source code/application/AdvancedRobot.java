package application;

import javafx.scene.paint.Color;
import java.util.Random;

/**
 * The AdvancedRobot class represents a robot with advanced movement logic.
 * This robot can randomly change its direction and move within a given area.
 * The AdvancedRobot class represents a robot with advanced motion logic that can detect and absorb BasicRobots
 * It extends the {@link Robot} class
 * The robot will try to move in the current direction, but if it encounters an obstacle or boundary, it will move randomly Change direction until you find a clear path or exhaust all attempts.
 * to remain within the boundaries.
 *
 * @author [SHEN FANGJIE]
 * @version 1.0
 * @see Robot
 */
    public class AdvancedRobot extends Robot {

    /**
     * Constructs a new AdvancedRobot object with specified starting position, direction, and color.
     * Inherits from the abstract class robot
     *
     * @param x        the initial X coordinate of the robot
     * @param y        the initial Y coordinate of the robot
     * @param direction the initial direction of the robot
     */
    public AdvancedRobot(int x, int y, Direction direction) {
        super(x, y, direction, Color.RED);  // AdvancedRobot uses red color
    }

    /**
     * Moves the robot. Each time this method is called, there is a 50% chance the robot will
     * randomly change its direction, then it moves one step forward in the current direction.
     * If moving would cause the robot to go out of bounds, it will randomly adjust its direction
     * and be constrained within the boundary.
     */
    @Override
    public void move() {
        // Implement advanced movement logic
        if (new Random().nextBoolean()) {
            setDirection(Direction.values()[new Random().nextInt(Direction.values().length)]);
        }

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
     * Resets the state of the robot to its initial state. The specific behavior is defined by the parent class {@link Robot}.
     */
    @Override
    public void reset() {
        super.reset();
    }

    /**
     * Creates a new AdvancedRobot object with random position and direction.
     *
     * @param maxX the maximum X coordinate (exclusive), determining the possible maximum X position of the robot
     * @param maxY the maximum Y coordinate (exclusive), determining the possible maximum Y position of the robot
     * @return a newly created AdvancedRobot object
     */
    public static AdvancedRobot createRandom(int maxX, int maxY) {
        int x = new Random().nextInt(maxX);
        int y = new Random().nextInt(maxY);
        Direction direction = Direction.values()[new Random().nextInt(Direction.values().length)];
        return new AdvancedRobot(x, y, direction);
    }
}
