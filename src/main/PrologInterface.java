package src.main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import org.jpl7.Query;

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
            } else if (character == '\n') {
                currRow++;
                currColumn = 0;
            }
        } while (character != -1);

        numRows = currRow + 1;
        numColumns = currColumn;
        Map.initializeInstance(numRows, numColumns, mapInfo);
        Agent.initializeInstance(new Coordinates(xHome, yHome));

        // For debugging purposes
        Map.printMapInformation();
        Agent.printAgentInformation();

        // Implement into the knowledge_base.pl
        updateKnowledgeBase();
    }

    // TODO
    /**
     * Queries the knowledge base for new information given a player move.
     * Returns an integer representing what type block is in the given row-col
     * location
     */

    // not sure how this should be implemented because there is a possibility that a
    // block is both a breeze and a gold
    // possible solution would be for the function to accept a string of what to
    // query
    public void queryNewInformation(int curRow, int curCol) {
        Query checkForPit = new Query("is_pit", curRow, curCol);
        Query checkForBreeze = new Query("has_breeze", curRow, curCol);
        Query checkForGold = new Query("is_gold", curRow, curCol);
        Query checkForGlitter = new Query("has_glitter", curRow, curCol); // tbh di ko gets point ng glitter since same
                                                                          // location lang naman sa gold
        Query checkForHome = new Query("is_home", curRow, curCol);
        Query checkIfSafe = new Query("is_safe", curRow, curCol);

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

    /*
     * CURRENT IMPLEMENTATION:
     * Finds home, gold, and pits then adds to the knowledge_base.pl
     */
    public static void updateKnowledgeBase(char[][] mapInfo, int numRows, int numColum) {
        FileWriter writer = new FileWriter("knowledge_base.pl", false);
        writer.write("");

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCol; j++) {
                switch (mapInfo[i][j]) {
                    // currently uses 1-based index in knowledge base
                    // update if necessary
                    case 'P':
                        writer.write("pit(%d, %d).", i + 1, j + 1);
                        break;
                    case 'G':
                        writer.write("gold(%d, %d).", i + 1, j + 1);
                        break;
                    case 'H':
                        writer.write("home(%d, %d).", i + 1, j + 1);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    // TODO: Feel free to add/remove/modify methods here as you deem appropriate.
}