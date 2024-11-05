package src.main;

import java.util.Scanner;
import org.jpl7.*;

public class PrologInterface {

    public PrologInterface() {

    }

    // TODO
    /**
     * Gets both full and known map information.
     */


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