package application;

import javafx.scene.paint.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * The SensorRobot class represents a robot with advanced movement logic that can detect and avoid obstacles.
 * It extends the {@link Robot} class and uses a set of predefined obstacle coordinates to navigate safely within the grid.
 * The robot will attempt to move in its current direction, but if it encounters an obstacle or boundary, it will randomly
 * change direction until it finds a clear path or exhausts all attempts.
 *
 * @author [Your Name]
 * @version 1.0
 * @see Robot
 */
    public class SensorRobot extends Robot {

    /**
     * A set of points representing the coordinates of obstacles that the SensorRobot must avoid.
     */
    private static final Set<Point> obstacles = new HashSet<>();

    static {
        // Initialize the set of obstacles with predefined coordinates
        obstacles.add(new Point(5, 5));
        obstacles.add(new Point(3, 10));
        obstacles.add(new Point(8, 8));
    }

    /**
     * Constructs a new SensorRobot object with specified starting position, direction, and color.
     * The SensorRobot uses yellow color by default.
     *
     * @param x        the initial X coordinate of the robot
     * @param y        the initial Y coordinate of the robot
     * @param direction the initial direction of the robot
     */
    public SensorRobot(int x, int y, Direction direction) {
        super(x, y, direction, Color.YELLOW);  // SensorRobot uses yellow color
    }

    /**
     * Moves the robot one step forward in its current direction.
     * If moving would cause the robot to encounter an obstacle or go out of bounds, it will randomly adjust its direction
     * and attempt to move again. If all directions are blocked, the robot remains stationary.
     */
    @Override
    public void move() {
        // Implement advanced movement logic
        if (new Random().nextBoolean()) {
            setDirection(Direction.values()[new Random().nextInt(Direction.values().length)]);
        }

        // Attempt to move
        boolean moved = false;
        int maxAttempts = 4;  // Maximum number of attempts to avoid infinite loops
        int attempts = 0;

        while (!moved && attempts < maxAttempts) {
            // Calculate the next position
            int nextX = getX();
            int nextY = getY();

            switch (getDirection()) {
                case UP:
                    nextY = getY() - 1;
                    break;
                case DOWN:
                    nextY = getY() + 1;
                    break;
                case LEFT:
                    nextX = getX() - 1;
                    break;
                case RIGHT:
                    nextX = getX() + 1;
                    break;
            }

            // Check if the next position is within bounds and not an obstacle
            if (nextX >= 2 && nextX < 17 && nextY >= 2 && nextY < 17 && !obstacles.contains(new Point(nextX, nextY))) {
                setX(nextX);
                setY(nextY);
                moved = true;
            } else {
                // If an obstacle or boundary is encountered, randomly change direction
                setDirection(Direction.random());
                attempts++;
            }
        }

        // If all directions are blocked, remain stationary
        if (!moved) {
            System.out.println("SensorRobot at (" + getX() + ", " + getY() + ") is blocked and cannot move.");
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
     * Creates a new SensorRobot object with random position and direction.
     *
     * @param maxX the maximum X coordinate (exclusive), determining the possible maximum X position of the robot
     * @param maxY the maximum Y coordinate (exclusive), determining the possible maximum Y position of the robot
     * @return a newly created SensorRobot object
     */
    public static SensorRobot createRandom(int maxX, int maxY) {
        int x = new Random().nextInt(maxX);
        int y = new Random().nextInt(maxY);
        Direction direction = Direction.values()[new Random().nextInt(Direction.values().length)];
        return new SensorRobot(x, y, direction);
    }

    /**
     * A helper class used to represent a point on the grid with X and Y coordinates.
     * This class is used to check for obstacles and ensure that two points with the same coordinates are considered equal.
     */
    private static class Point {
        /**
         * The X coordinate of the point.
         */
        private final int x;

        /**
         * The Y coordinate of the point.
         */
        private final int y;

        /**
         * Constructs a new Point object with specified coordinates.
         *
         * @param x the X coordinate of the point
         * @param y the Y coordinate of the point
         */
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Checks if this point is equal to another object.
         *
         * @param o the object to compare with
         * @return true if the objects are equal, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        /**
         * Returns a hash code value for the point.
         *
         * @return the hash code value
         */
        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }
}