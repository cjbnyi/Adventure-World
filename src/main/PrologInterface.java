package src.main;

import java.io.FileReader;
import java.io.IOException;
import src.gui.GUIController;

public class PrologInterface {

    /**
     * Encodes full map information given a FileReader to the chosen map .txt file.
     */
    public void encodeFullMapInformation(FileReader fileReader) {

        int character;

        int currRow = 0;
        int currColumn = 0;
        int numRows, numColumns;

        int xHome = 0;
        int yHome = 0;
        char[][] mapInfo = new char[Map.MAX_ROWS][Map.MAX_COLUMNS];

        // Extracts information from the map one character at a time via fileReader.
        do {
            try {
                character = fileReader.read();
            } catch (IOException e) {
                e.printStackTrace(); // TODO (optional): Replace with more robust logging.
                character = -1;
            }

            if (Map.isValidMapCellChar(character)) {
                if (character == Map.HOME_CELL) {
                    xHome = currRow;
                    yHome = currColumn;
                }
                mapInfo[currRow][currColumn++] = (char) character;
            }
            else if (character == '\n') {
                currRow++;
                currColumn = 0;
            }
        } while (character != -1);

        numRows = currRow + 1;
        numColumns = currColumn;
        Map.initializeInstance(numRows, numColumns, mapInfo);
        Agent.initializeInstance(new Coordinates(xHome, yHome));
        GUIController guiController = new GUIController();

        // For debugging purposes
        Map.printMapInformation();
        Agent.printAgentInformation();
    }

    // TODO
    /**
     * Queries the knowledge base for new information given a player move.
     */
    public void queryNewInformation() {

    }

    // TODO
    /**
     * Updates the agent given new information.
     */
    public void updateAgent() {

    }

    // TODO
    /**
     * Updates the knowledge base given new information.
     */
    public static void updateKnowledgeBase() {

    }

    // TODO: Feel free to add/remove/modify methods here as you deem appropriate.
}