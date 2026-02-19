package application;

import java.io.*;
import java.util.List;

/**
 * The ConfigManager class provides methods for saving and loading the state of a RobotArena to and from a file.
 * It uses Java's object serialization to serialize and deserialize the RobotArena object.
 *
 * @author [SHEN FANGJIE]
 * @version 1.0
 */
    public class ConfigManager {

    /**
     * Saves the state of a RobotArena to a specified file path.
     * The RobotArena object is serialized and written to the file using an ObjectOutputStream.
     *
     * @param arena    the RobotArena object to be saved
     * @param filePath the file path where the RobotArena should be saved
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public static void saveConfig(RobotArena arena, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(arena);
        }
    }

    /**
     * Loads the state of a RobotArena from a specified file path.
     * The file is read using an ObjectInputStream, and the RobotArena object is deserialized.
     *
     * @param filePath the file path from which to load the RobotArena
     * @return the deserialized RobotArena object
     * @throws IOException if an I/O error occurs while reading from the file
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    public static RobotArena loadConfig(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (RobotArena) ois.readObject();
        }
    }
}