package application;
import java.util.Random;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * The main application entry point, which initializes and sets up the main interface, menu bar, and interactions with {@link RobotArena} and {@link RobotCanvas}.
 * 
 * @author [SHEN FANGJIE]
 * @version 1.0
 */

    public class Main extends Application {
    private RobotArena arena;
    private RobotCanvas robotCanvas;
    private Stage primaryStage;
 
/**
 * Launch the main interface of the application
 * 
 * @param primaryStage   Main application window
 */
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        int sizeX = 20;
        int sizeY = 20;
        int numRobots = 5;
        arena = new RobotArena(sizeX, sizeY);
        arena.addRandomRobots(numRobots);
        robotCanvas = new RobotCanvas(arena, sizeX * 30, sizeY * 30);

        // Create the main layout
        BorderPane root = new BorderPane();
        root.setCenter(robotCanvas);

        // Create and add the menu bar
        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        // Create a TextArea for displaying robot positions
        TextArea positionInfo = new TextArea();
        positionInfo.setEditable(false);  
        positionInfo.setPrefWidth(200);   
        positionInfo.setWrapText(true);   

        // Add the TextArea to the right side of the BorderPane
        root.setRight(positionInfo);

        // Initialize the position info display
        updatePositionInfo(positionInfo);

        // Set up the update callback in RobotArena
        arena.setOnUpdate(() -> Platform.runLater(() -> {
            robotCanvas.updateCanvas();
            updatePositionInfo(positionInfo);
        }));
        
        Scene scene = new Scene(root, sizeX * 30 + 220, sizeY * 30);  
        primaryStage.setScene(scene);
        primaryStage.setTitle("Robot Arena");
        primaryStage.show();
    }

/**
 * Update the text area to show the robot's current location.
 *
 * @param positionInfo    A text area that displays robot location information
 */       

    private void updatePositionInfo(TextArea positionInfo) {
        positionInfo.clear();  
        StringBuilder sb = new StringBuilder();
        for (Robot robot : arena.getRobots()) {
            sb.append("Robot ID: ").append(robot.getId())
              .append(", Position: (").append(robot.getX()).append(", ").append(robot.getY())
              .append("), Direction: ").append(robot.getDirection())
              .append("\n");
        }
        positionInfo.setText(sb.toString());
    }

    /**
     * Creates and returns a menu bar with file, edit, run, and help menus.
     *
     * @return      A menu bar that contains all menu items
     */ 
    
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem newArenaItem = new MenuItem("New Arena");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem exitItem = new MenuItem("Exit");

        newArenaItem.setOnAction(e -> createNewArena());
        openItem.setOnAction(e -> openArena());
        saveItem.setOnAction(e -> saveArena());
        exitItem.setOnAction(e -> primaryStage.close());

        fileMenu.getItems().addAll(newArenaItem, openItem, saveItem, exitItem);

        // Edit Menu
        Menu editMenu = new Menu("Edit");
        MenuItem addRobotItem = new MenuItem("Add Robot");
        MenuItem removeRobotItem = new MenuItem("Remove Robot");
        MenuItem clearArenaItem = new MenuItem("Clear Arena");
        MenuItem addObstacleItem1 = new MenuItem("Add Obstacle1");  
        MenuItem addObstacleItem2 = new MenuItem("Add Obstacle2"); 
        MenuItem addObstacleItem3 = new MenuItem("Add Obstacle3"); 
        
        
        
        addRobotItem.setOnAction(e -> addRobot());
        removeRobotItem.setOnAction(e -> removeRobot());
        clearArenaItem.setOnAction(e -> clearArena());
        addObstacleItem1.setOnAction(e -> addFixedObstacle1());
        addObstacleItem2.setOnAction(e -> addFixedObstacle2());
        addObstacleItem3.setOnAction(e -> addFixedObstacle3());
        
        
        editMenu.getItems().addAll(addRobotItem, removeRobotItem, clearArenaItem, addObstacleItem1,addObstacleItem2,addObstacleItem3);

        // Run Menu
        Menu runMenu = new Menu("Run");
        MenuItem startSimulationItem = new MenuItem("Start Simulation");
        MenuItem pauseSimulationItem = new MenuItem("Pause Simulation");
        MenuItem stopSimulationItem = new MenuItem("Stop Simulation");

        startSimulationItem.setOnAction(e -> robotCanvas.startMovement());
        pauseSimulationItem.setOnAction(e -> robotCanvas.stopMovement());
        stopSimulationItem.setOnAction(e -> {
            robotCanvas.stopMovement();
            resetRobots();
        });

        runMenu.getItems().addAll(startSimulationItem, pauseSimulationItem, stopSimulationItem);

        // Help Menu
        Menu helpMenu = new Menu("Help");
        MenuItem instructionsItem = new MenuItem("Instructions");
        MenuItem aboutItem = new MenuItem("About");

        instructionsItem.setOnAction(e -> showInstructions());
        aboutItem.setOnAction(e -> showAboutDialog());

        helpMenu.getItems().addAll(instructionsItem, aboutItem);

        // Add all menus to the menu bar
        menuBar.getMenus().addAll(fileMenu, editMenu, runMenu, helpMenu);

        return menuBar;
    }

    /**
     * Prompts the user to confirm whether to create a new arena and to clear the current arena content.
     */
    
    private void createNewArena() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Create New Arena");
        alert.setHeaderText("Do you want to create a new arena?");
        alert.setContentText("This will clear the current arena.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int sizeX = 10;
            int sizeY = 10;
            int numRobots = 5;
            arena = new RobotArena(sizeX, sizeY);
            arena.addRandomRobots(numRobots);
            robotCanvas.updateCanvas();
        }
    }


    /**
     * Allows the user to open a saved arena profile.
     */
    
    private void openArena() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Arena Configuration");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                arena = ConfigManager.loadConfig(file.getAbsolutePath());
                robotCanvas.updateCanvas();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load arena configuration.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    
    /**
     * Allows users to save the current arena configuration to a file.
     */  
    
    private void saveArena() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Arena Configuration");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                ConfigManager.saveConfig(arena, file.getAbsolutePath());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to save arena configuration.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    /**
     * A dialog box is displayed allowing the user to select the type of robot to be added and place it randomly in the arena.
     */  
    
    private void addRobot() {
        String[] robotTypes = {"BasicRobot", "AdvancedRobot", "SensorRobot"};
        ChoiceDialog<String> dialog = new ChoiceDialog<>("BasicRobot", robotTypes);
        dialog.setTitle("Add Robot");
        dialog.setHeaderText("Choose the type of robot to add:");
        dialog.setContentText("Select:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            int x = (int) (Math.random() * arena.getSizeX());
            int y = (int) (Math.random() * arena.getSizeY());
            Direction direction = Direction.values()[new Random().nextInt(Direction.values().length)];

            Robot newRobot;
            switch (result.get()) {
                case "AdvancedRobot":
                    newRobot = AdvancedRobot.createRandom(arena.getSizeX(), arena.getSizeY());
                    break;
                case "SensorRobot":
                    newRobot = SensorRobot.createRandom(arena.getSizeX(), arena.getSizeY());  // 添加 SensorRobot 的创建逻辑
                    break;
                default:
                    newRobot = BasicRobot.createRandom(arena.getSizeX(), arena.getSizeY());  // 使用基础的 BasicRobot 类
                    break;
            }

            try {
                arena.addRobot(newRobot);
                Platform.runLater(() -> {
                    robotCanvas.updateCanvas();
                });
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Robot added successfully.");
                successAlert.setContentText("New robot ID: " + newRobot.getId());
                successAlert.showAndWait();
            } catch (IllegalArgumentException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Failed to add robot.");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }

    /**
     * Remove robots from the arena by their number and provide success or warning messages.
     */
    
    private void removeRobot() {
        // Implement logic to remove a selected robot
        // For simplicity, we'll remove the first robot in the list
        if (!arena.getRobots().isEmpty()) {
            Robot removedRobot = arena.getRobots().remove(0);
            Platform.runLater(() -> {
                robotCanvas.updateCanvas();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Robot removed successfully.");
                successAlert.setContentText("Removed robot ID: " + removedRobot.getId());
                successAlert.showAndWait();
            });
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setTitle("Warning");
            errorAlert.setHeaderText("No robots to remove.");
            errorAlert.setContentText("The arena is empty.");
            errorAlert.showAndWait();
        }
    }

    /**
     * Clear all droids in the arena.
     */  
    
    private void clearArena() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Arena");
        alert.setHeaderText("Do you want to clear all robots from the arena?");
        alert.setContentText("This will remove all robots.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            arena.clear();
            robotCanvas.updateCanvas();
        }
    }

    /**
     * Add obstacles to the arena.
     * @param fixedX Fixed position X coordinates
     * @param fixedY Fixed position Y coordinates
     */  
    
    private void addFixedObstacle1() {
        
        int fixedX = 5;
        int fixedY = 5;
        
       
        
        try {
            // Create a new obstacle and add it to the arena
            Obstacle newObstacle = new Obstacle(fixedX, fixedY);
            arena.addObstacle(newObstacle);
          
            // Update canvas
            Platform.runLater(() -> {
                robotCanvas.updateCanvas();
            });

            // Provide success tips
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Obstacle added successfully.");
            successAlert.setContentText("New obstacle at: (" + fixedX + ", " + fixedY + ")");
            successAlert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Handle cases of invalid locations
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Failed to add obstacle.");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }
    
    /**
     * Add obstacles to the arena.
     * @param fixedX Fixed position X coordinates
     * @param fixedY Fixed position Y coordinates
     */  
      
    
    private void addFixedObstacle2() {
       
        int fixedX = 3;
        int fixedY = 10;
        
       
        
        try {
            // Create a new obstacle and add it to the arena
            Obstacle newObstacle = new Obstacle(fixedX, fixedY);
            arena.addObstacle(newObstacle);
          
            // Update canvas
            Platform.runLater(() -> {
                robotCanvas.updateCanvas();
            });

            // Provide success tips
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Obstacle added successfully.");
            successAlert.setContentText("New obstacle at: (" + fixedX + ", " + fixedY + ")");
            successAlert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Handle cases of invalid locations
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Failed to add obstacle.");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }
    
    /**
     * Add obstacles to the arena.
     * @param fixedX Fixed position X coordinates
     * @param fixedY Fixed position Y coordinates
     */  
    
    
    private void addFixedObstacle3() {
        
        int fixedX = 8;
        int fixedY = 8;
        
       
        
        try {
            // Create a new obstacle and add it to the arena
            Obstacle newObstacle = new Obstacle(fixedX, fixedY);
            arena.addObstacle(newObstacle);
          
            // Update canvas
            Platform.runLater(() -> {
                robotCanvas.updateCanvas();
            });

            // Provide success tips
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Obstacle added successfully.");
            successAlert.setContentText("New obstacle at: (" + fixedX + ", " + fixedY + ")");
            successAlert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Handle cases of invalid locations
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Failed to add obstacle.");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }
    
    
    /**
     * Resets the position and status of all robots in the arena.
     */
    
    private void resetRobots() {
        arena.resetRobots();
        robotCanvas.updateCanvas();
    }

    /**
     * Display instructions for use.
     */
    
    private void showInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("How to Use the Robot Arena Simulator");

        // Use GridPane to wrap content text for better control over layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create a Label to display the content text and set the font size
        Label contentLabel = new Label("""
            1. Use the 'File' menu to create a new arena, open or save an existing configuration.
            2. Use the 'Edit' menu to add or remove robots, or clear the arena.
            3. Use the 'Run' menu to start, pause, or stop the simulation.
            4. Use the 'Help' menu to view instructions or information about the application.
            """);
        contentLabel.setFont(new Font(14));  
        grid.add(contentLabel, 0, 0);

        // Adds custom content to the DialogPane of Alert
        alert.getDialogPane().setContent(grid);

        // Sets the minimum width and height of the dialog box
        alert.getDialogPane().setMinWidth(600);  
        alert.getDialogPane().setMinHeight(400);  

        alert.showAndWait();
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Robot Arena Simulator");

        // Use GridPane to wrap content text for better control over layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create a Label to display the content text and set the font size
        Label contentLabel = new Label("""
            Robot Arena Simulator v1.0
            Developed by [SHEN FANGJIE  31808395]
            Copyright © 2024

            This application simulates a robotic arena where multiple types of robots can be added, moved, and interacted with.
            """);
        contentLabel.setFont(new Font(14));  
        grid.add(contentLabel, 0, 0);

        // Adds custom content to the DialogPane of Alert
        alert.getDialogPane().setContent(grid);

        // Sets the minimum width and height of the dialog box
        alert.getDialogPane().setMinWidth(600);  
        alert.getDialogPane().setMinHeight(400);  

        alert.showAndWait();
    }

    
    /**
     * Entry point to the application.
     *
     * @param args command line parameter
     */
    
    public static void main(String[] args) {
        launch(args);
    }
}
