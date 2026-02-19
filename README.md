# Robot-Arena-Simulator
This project is a graphical robot arena simulator (Robot Arena Simulator) developed based on JavaFX. The system utilizes the object-oriented programming (OOP) concept to achieve dynamic interaction among multiple robots and various obstacles within the arena. 

---

# Robot Arena Simulator

A graphical user interface (GUI) based simulation environment developed using **JavaFX** and **Object-Oriented Programming (OOP)** principles. This project simulates the dynamic interactions between multiple autonomous robots and environmental obstacles in a virtual arena.

## 📖 Overview

This simulator facilitates the interaction of various robot types and objects within a customizable arena. It demonstrates core software engineering concepts, including inheritance, abstract classes, and modular design, to create a robust and extensible simulation platform.

## ✨ Key Features

* 
**GUI Interface**: Built with JavaFX, featuring professional menu bars, toolbars, and a real-time information panel.


* 
**Dynamic Animation**: High-quality animation for robot movement and environmental interaction.


* 
**Autonomous Navigation**: Robots utilize sensors (bump, whisker, or beam) to navigate and avoid obstacles.


* 
**File Management**: Support for creating new arenas, saving current configurations, and loading existing simulation files.


* 
**Real-time Monitoring**: A status panel displays the ID, position, and next-move direction for every robot in the arena.



## 🤖 Robot Types

The system defines an abstract `Robot` class with three specialized subclasses:

1. 
**BasicRobot (Blue)**: Performs standard movement and randomly changes direction upon hitting boundaries.


2. 
**AdvancedRobot (Red)**: Possesses the unique ability to detect and "swallow" (destroy) BasicRobots upon contact.


3. 
**SensorRobot (Yellow)**: Equipped with specialized sensors to detect and autonomously navigate around static obstacles.



## 🏗️ Technical Architecture

### OOP Design & Class Hierarchy

The project employs a clear class hierarchy to manage system complexity:

* 
**`Robot` (Abstract Class)**: Defines fundamental properties (position, color, direction) and behaviors for all robotic entities.


* 
**`RobotArena`**: The core manager responsible for tracking all robots and obstacles within the simulation space.


* 
**`RobotCanvas`**: Handles the rendering and visual updates of the arena.


* 
**`ConfigManager`**: Manages file I/O operations for saving and loading arena setups.


* 
**`Obstacle`**: Represents static environmental items that robots must navigate around.



### Design Diagrams

*(Note: Refer to the original report for the following figures)*

* Fig. 9: Hierarchy for the abstract class `Robot`.


* Fig. 10: Hierarchy diagram for system-wide classes (Main, Arena, Canvas, etc.).



## 🖼️ Interface Showcase

The simulation environment is controlled through four primary menus:

* 
**File**: New Arena, Open, Save, and Exit.


* 
**Edit**: Add/Remove Robots, Clear Arena, and Add specialized obstacles.


* 
**Run**: Start, Pause, and Stop the simulation.


* 
**Help**: Access instructions and project "About" information.



## 🚀 Future Enhancements

* Integration of **Machine Learning** algorithms for optimized autonomous decision-making.


* Addition of advanced **physics engines** to simulate realistic friction and collision effects.


* Support for **Multi-user control** to enhance interactive experiences.


