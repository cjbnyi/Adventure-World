package src.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Controller {

    private final String MAP_DIRECTORY = "resources/maps/";

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void start() throws FileNotFoundException {
        Scanner inputScanner = new Scanner(System.in);
        String mapName;
        String filePath;
        Scanner fileScanner;

        System.out.print("Enter map name: ");
        mapName = inputScanner.nextLine();
        filePath = MAP_DIRECTORY + mapName + ".txt";
        System.out.println("Filepath: " + filePath); // debug

        File file = new File(filePath);
        if (!file.exists()) {
            // TODO: Nonexistent file case handling.
            System.out.println("File nonexistent."); // debug
            return;
        }

        System.out.println("File exists."); // debug
        fileScanner = new Scanner(file);
        model.encodeFullMapInformation(fileScanner);
    }
}