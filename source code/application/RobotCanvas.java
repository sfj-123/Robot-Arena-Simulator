package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * The RobotCanvas class represents a graphical canvas for visualizing robots and obstacles in a RobotArena.
 * It provides methods to start and stop the movement of robots, as well as to update and draw the current state of the arena.
 *
 * @author [SHEN FANGJIE]
 * @version 1.0
 */
   public class RobotCanvas extends Canvas {

    /**
     * The RobotArena instance that this canvas is associated with.
     */
    private RobotArena arena;

    /**
     * A Timeline object used to animate the movement of robots on the canvas.
     */
    private Timeline timeline;

    /**
     * Constructs a new RobotCanvas object with specified dimensions and associates it with a RobotArena.
     *
     * @param arena  the RobotArena to visualize
     * @param width  the width of the canvas
     * @param height the height of the canvas
     */
    public RobotCanvas(RobotArena arena, double width, double height) {
        super(width, height);
        this.arena = arena;
        drawRobotsAndObstacles();
    }

    /**
     * Starts the animation that moves the robots in the arena at regular intervals.
     * If the animation is already running, this method does nothing.
     */
    public void startMovement() {
        if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            arena.moveRobots();  // 调用 RobotArena 的 moveRobots 方法
            drawRobotsAndObstacles();  // 重新绘制画布
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Stops the animation that moves the robots in the arena.
     */
    public void stopMovement() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Updates the canvas by redrawing all robots and obstacles.
     */
    public void updateCanvas() {
        drawRobotsAndObstacles();
    }

    /**
     * Draws all robots and obstacles on the canvas.
     */
    private void drawRobotsAndObstacles() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        double cellSize = getWidth() / arena.getSizeX();

        // Draw all obstacles
        for (Obstacle obstacle : arena.getObstacles()) {
            gc.setFill(obstacle.getColor());  // 设置填充颜色为障碍物的颜色
            gc.fillRect(obstacle.getX() * cellSize, obstacle.getY() * cellSize, cellSize, cellSize);

            // Optional: Draw a border to distinguish obstacles
            gc.setStroke(Color.BLACK);
            gc.strokeRect(obstacle.getX() * cellSize, obstacle.getY() * cellSize, cellSize, cellSize);
        }

        // Draw all robots, using their color properties
        for (Robot robot : arena.getRobots()) {
            double x = robot.getX() * cellSize;
            double y = robot.getY() * cellSize;

            if (robot instanceof AdvancedRobot) {
                // Draw AdvancedRobot with wheels
                drawAdvancedRobot(gc, x, y, cellSize, ((AdvancedRobot) robot).getColor());
            } else if (robot instanceof SensorRobot) {
                // Draw SensorRobot with antennas
                drawSensorRobot(gc, x, y, cellSize, ((SensorRobot) robot).getColor());
            } else {
                // Draw BasicRobot as a simple square
                drawBasicRobot(gc, x, y, cellSize, robot.getColor());
            }

            // Draw robot ID
            gc.setFill(Color.BLACK);
            gc.fillText("ID: " + robot.getId(), x + 5, y + 15);
        }
    }

    /**
     * Draws a BasicRobot as a simple square.
     *
     * @param gc       the GraphicsContext to draw on
     * @param x        the X coordinate of the top-left corner of the robot
     * @param y        the Y coordinate of the top-left corner of the robot
     * @param cellSize the size of each cell on the grid
     * @param color    the color of the robot
     */
    private void drawBasicRobot(GraphicsContext gc, double x, double y, double cellSize, Color color) {
        gc.setFill(color);  // 设置填充颜色为机器人的颜色
        gc.fillRect(x, y, cellSize, cellSize);

        // Optional: Draw a border to distinguish robots
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, cellSize, cellSize);
        
        // Define the radius of the wheels
        double wheelRadius = cellSize / 8;

        // Calculate the offset for placing wheels outside the square
        double wheelOffset = wheelRadius * 2;  // The diameter of the wheel

        // Draw four wheels at the corners of the square, outside the square
        gc.setFill(Color.GRAY);  // Set the wheel color to gray


        // Bottom-left wheel (outside)
        gc.fillOval(x - wheelOffset, y + cellSize, wheelRadius * 2, wheelRadius * 2);

        // Bottom-right wheel (outside)
        gc.fillOval(x + cellSize, y + cellSize, wheelRadius * 2, wheelRadius * 2);
    }

    /**
     * Draws an AdvancedRobot as a square with four wheels outside the square.
     *
     * @param gc       the GraphicsContext to draw on
     * @param x        the X coordinate of the top-left corner of the robot
     * @param y        the Y coordinate of the top-left corner of the robot
     * @param cellSize the size of each cell on the grid
     * @param color    the color of the robot
     */
    private void drawAdvancedRobot(GraphicsContext gc, double x, double y, double cellSize, Color color) {
        // Draw the main body of the AdvancedRobot as a square
        gc.setFill(color);  // 设置填充颜色为机器人的颜色
        gc.fillRect(x, y, cellSize, cellSize);

        // Optional: Draw a border to distinguish robots
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, cellSize, cellSize);

        // Define the radius of the wheels
        double wheelRadius = cellSize / 8;

        // Calculate the offset for placing wheels outside the square
        double wheelOffset = wheelRadius * 2;  // The diameter of the wheel

        // Draw four wheels at the corners of the square, outside the square
        gc.setFill(Color.GRAY);  // Set the wheel color to gray

        // Top-left wheel (outside)
        gc.fillOval(x - wheelOffset, y - wheelOffset, wheelRadius * 2, wheelRadius * 2);

        // Top-right wheel (outside)
        gc.fillOval(x + cellSize, y - wheelOffset, wheelRadius * 2, wheelRadius * 2);

        // Bottom-left wheel (outside)
        gc.fillOval(x - wheelOffset, y + cellSize, wheelRadius * 2, wheelRadius * 2);

        // Bottom-right wheel (outside)
        gc.fillOval(x + cellSize, y + cellSize, wheelRadius * 2, wheelRadius * 2);
    }

    /**
     * Draws a SensorRobot as a square with two antennas and four wheels at the corners.
     *
     * @param gc       the GraphicsContext to draw on
     * @param x        the X coordinate of the top-left corner of the robot
     * @param y        the Y coordinate of the top-left corner of the robot
     * @param cellSize the size of each cell on the grid
     * @param color    the color of the robot
     */
    private void drawSensorRobot(GraphicsContext gc, double x, double y, double cellSize, Color color) {
        // Draw the main body of the SensorRobot as a square
        gc.setFill(color);  // 设置填充颜色为机器人的颜色
        gc.fillRect(x, y, cellSize, cellSize);

        // Optional: Draw a border to distinguish robots
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, cellSize, cellSize);

        // Define the length of the antennas
        double antennaLength = cellSize / 4;

        // Draw two antennas from the top-left and bottom-right corners
        gc.setStroke(Color.RED);  // Set the antenna color to red
        gc.setLineWidth(2);  // Set the thickness of the antenna lines

        // Top-left antenna
        gc.strokeLine(x, y, x - antennaLength, y - antennaLength);

        // Top-right antenna (new position)
        gc.strokeLine(x + cellSize, y, x + cellSize + antennaLength, y - antennaLength);


        // Define the radius of the wheels
        double wheelRadius = cellSize / 8;

        // Calculate the offset for placing wheels outside the square
        double wheelOffset = wheelRadius * 2;  // The diameter of the wheel

        // Draw four wheels at the corners of the square, outside the square
        gc.setFill(Color.GRAY);  // Set the wheel color to gray

    

        // Bottom-left wheel (outside)
        gc.fillOval(x - wheelOffset, y + cellSize, wheelRadius * 2, wheelRadius * 2);

        // Bottom-right wheel (outside)
        gc.fillOval(x + cellSize, y + cellSize, wheelRadius * 2, wheelRadius * 2);
    }
}