package src.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Controller {

    private final String MAP_DIRECTORY = "resources/maps/";

    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void start() throws FileNotFoundException {
        Scanner inputScanner = new Scanner(System.in);
        String mapName;
        String filePath;
        FileReader fileReader;

        System.out.print("Enter map name: ");
        mapName = inputScanner.nextLine();
        filePath = MAP_DIRECTORY + mapName + ".txt";
        System.out.println("Filepath: " + filePath); // debug

        File file = new File(filePath);
        if (!file.exists()) {
            // TODO: Nonexistent file case handling.
            System.out.println("\nFile nonexistent."); // debug
            return;
        }
        System.out.println("\nFile exists."); // debug

        fileReader = new FileReader(file);
        model.encodeFullMapInformation(fileReader);
    }
}