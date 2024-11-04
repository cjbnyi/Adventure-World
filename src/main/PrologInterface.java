package src.main;

import java.util.Scanner;
import org.jpl7.*;

public class PrologInterface {

    public static final int NUM_ROWS = 5;
    public static final int NUM_COLUMNS = 5;

    public PrologInterface() {

    }

    // TODO
    /**
     * Encodes full map information.
     */
    public void encodeFullMapInformation(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }
    }

    // TODO
    /**
     * Updates known map information.
     */
    public void updateKnownMapInformation() {

    }

    // TODO
    /**
     * Adds a fact to the knowledge base.
     */
    public static void addFact(String fact) {

    }
}