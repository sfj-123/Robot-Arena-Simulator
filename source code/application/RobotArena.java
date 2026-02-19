package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Platform;

/**
 * The RobotArena class represents a simulation environment for managing and controlling multiple robots.
 * It maintains a grid of specified dimensions, a list of robots, and a list of obstacles within the arena.
 * The class provides methods to add robots, move them, detect collisions, and reset their states.
 *
 * @author [SHEN FANGJIE]
 * @version 1.0
 */
    public class RobotArena {

    /**
     * The width of the arena grid.
     */
    private int sizeX;

    /**
     * The height of the arena grid.
     */
    private int sizeY;

    /**
     * A list of robots in the arena.
     */
    private List<Robot> robots;

    /**
     * A list of obstacles in the arena.
     */
    private List<Obstacle> obstacles;  // 新增障碍物列表

    /**
     * A callback function that is invoked when the arena state changes, typically used to update the UI.
     */
    private Runnable onUpdateCallback;

    /**
     * Constructs a new RobotArena object with specified dimensions.
     *
     * @param sizeX the width of the arena grid
     * @param sizeY the height of the arena grid
     */
    public RobotArena(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.robots = new ArrayList<>();
        this.obstacles = new ArrayList<>();  // 初始化障碍物列表
    }

    /**
     * Adds a robot to the arena if its initial position is within the bounds of the grid.
     *
     * @param robot the robot to be added
     * @throws IllegalArgumentException if the robot's initial position is out of bounds
     */
    public void addRobot(Robot robot) {
        if (robot.getX() >= 0 && robot.getX() < sizeX && robot.getY() >= 0 && robot.getY() < sizeY) {
            robots.add(robot);
        } else {
            throw new IllegalArgumentException("Robot position is out of bounds.");
        }
    }

    /**
     * Adds a specified number of random robots to the arena.
     * Each robot's type is chosen randomly from the available types.
     *
     * @param count the number of robots to add
     */
    public void addRandomRobots(int count) {
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            RobotType type = RobotType.values()[random.nextInt(RobotType.values().length)];
            switch (type) {
                case BASIC:
                    robots.add(BasicRobot.createRandom(sizeX, sizeY));
                    break;
                case ADVANCED:
                    robots.add(AdvancedRobot.createRandom(sizeX, sizeY));
                    break;
                case SENSOR:
                    robots.add(SensorRobot.createRandom(sizeX, sizeY));
                    break;
                default:
                    throw new IllegalStateException("Unexpected robot type: " + type);
            }
        }
    }

    /**
     * Clears all robots and obstacles from the arena.
     */
    public void clear() {
        robots.clear();
        obstacles.clear();  // 清除所有障碍物
    }

    /**
     * Resets the state of all robots in the arena.
     */
    public void resetRobots() {
        for (Robot robot : robots) {
            robot.reset();  // 假设每个机器人有一个 reset() 方法
        }
    }

    /**
     * Moves all robots in the arena, handling obstacle and robot-robot collisions.
     */
    public void moveRobots() {
        // 移动所有机器人
        for (Robot robot : robots) {
            robot.move();
            // 检查是否与障碍物发生碰撞
            if (isCollidingWithObstacle(robot)) {
                robot.setDirection(Direction.random());  // 随机改变方向
                robot.move();  // 再次尝试移动
            }
        }

        // 检测并处理机器人之间的碰撞
        detectCollisions();
    }

    /**
     * Checks if a robot is colliding with any obstacle in the arena.
     *
     * @param robot the robot to check for collision
     * @return true if the robot is colliding with an obstacle, false otherwise
     */
    private boolean isCollidingWithObstacle(Robot robot) {
        for (Obstacle obstacle : obstacles) {
            if (robot.getX() == obstacle.getX() && robot.getY() == obstacle.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Detects and handles collisions between robots.
     * Specifically, it checks for collisions between AdvancedRobots and BasicRobots,
     * where AdvancedRobots can destroy BasicRobots upon collision.
     */
    private void detectCollisions() {
        List<Robot> toRemove = new ArrayList<>();

        // 遍历所有机器人，检查是否有 AdvancedRobot 和 BasicRobot 相遇
        for (int i = 0; i < robots.size(); i++) {
            Robot robot1 = robots.get(i);
            if (robot1 instanceof AdvancedRobot) {
                for (int j = i + 1; j < robots.size(); j++) {
                    Robot robot2 = robots.get(j);
                    if (robot2 instanceof BasicRobot && robot1.getX() == robot2.getX() && robot1.getY() == robot2.getY()) {
                        // AdvancedRobot 消灭 BasicRobot
                        toRemove.add(robot2);
                        System.out.println("AdvancedRobot ID: " + robot1.getId() + " has destroyed BasicRobot ID: " + robot2.getId());
                        break;
                    }
                }
            }
        }

        // 移除被消灭的 BasicRobot
        robots.removeAll(toRemove);

        // 确保在 UI 线程上更新视图
        Platform.runLater(() -> {
            updateView();
        });
    }

    /**
     * Updates the view by invoking the provided callback function.
     */
    private void updateView() {
        // 这里可以通知 RobotCanvas 重新绘制
        // 例如，通过回调或事件机制
        if (onUpdateCallback != null) {
            onUpdateCallback.run();
        }
    }

    /**
     * Gets the list of robots in the arena.
     *
     * @return the list of robots
     */
    public List<Robot> getRobots() {
        return robots;
    }

    /**
     * Gets the list of obstacles in the arena.
     *
     * @return the list of obstacles
     */
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Gets the width of the arena grid.
     *
     * @return the width of the arena grid
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Gets the height of the arena grid.
     *
     * @return the height of the arena grid
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Sets the callback function to be invoked when the arena state changes.
     *
     * @param callback the callback function
     */
    public void setOnUpdate(Runnable callback) {
        this.onUpdateCallback = callback;
    }

    /**
     * Adds an obstacle to the arena if its position is within the bounds of the grid.
     *
     * @param obstacle the obstacle to be added
     * @throws IllegalArgumentException if the obstacle's position is out of bounds
     */
    public void addObstacle(Obstacle obstacle) {
        if (obstacle.getX() >= 0 && obstacle.getX() < sizeX && obstacle.getY() >= 0 && obstacle.getY() < sizeY) {
            obstacles.add(obstacle);
        } else {
            throw new IllegalArgumentException("Obstacle position is out of bounds.");
        }
    }

    /**
     * An enum defining the types of robots that can be added to the arena.
     */
    public enum RobotType {
        /**
         * Represents a basic robot with simple movement logic.
         */
        BASIC,

        /**
         * Represents an advanced robot with more complex behavior, such as the ability to destroy other robots.
         */
        ADVANCED,

        /**
         * Represents a sensor robot that can detect and avoid obstacles.
         */
        SENSOR
    }
}