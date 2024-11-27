package src.main;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jpl7.*;
import org.jpl7.Integer;

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

        Map.printMapInformation();  // For debugging purposes
        consultPL();
        isHome();
        isUngrabbedGold();
    }

    /**
     * Updates the agent given new information.
     */
    public void updateAgent() {
        hasBreeze();
        isPit();
        hasGlitter();
        isHome();
        isUngrabbedGold();
    }

    private void consultPL() {
        Query consultQuery = new Query("consult('src/main/adventure_world.pl')");

        if (consultQuery.hasSolution()) {
            System.out.println("\nSuccessfully consulted adventure_world.pl");

            assertInitialKnowledge();
        }
    }

    private void assertInitialKnowledge() {
        Map map = Map.getInstance();
        int numRows = map.getNumRows();
        int numColumns = map.getNumColumns();
        String queryString = "";
        Query query;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                char tile = map.getMapInfo()[i][j];
                if (tile == 'H') {
                    queryString = "assert(home(" + i + ", " + j + ")).\n";
                    query = new Query(queryString);
                    query.hasSolution();
                } else if (tile == 'G') {
                    queryString = "assert(gold(" + i + ", " + j + ")).\n";
                    query = new Query(queryString);
                    query.hasSolution();
                } else if (tile == 'P') {
                    queryString = "assert(pit(" + i + ", " + j + ")).\n";
                    query = new Query(queryString);
                    query.hasSolution();
                }
            }
        }

        // Assert facts
        Query assertWinner = new Query("assert(is_winner(" + (numRows - 1) +")).");
        Query assertRows = new Query("assert(numRows(" + numRows +")).");
        Query assertColumns = new Query("assert(numCols(" + numRows +")).");

        if (assertWinner.hasSolution() && assertRows.hasSolution() && assertColumns.hasSolution()) {
            System.out.println("Facts successfully asserted.\n");
        }

    }

    public static void retractOldKnowledge() {
        Query retractWinner = new Query("retract(is_winner(_)).");
        Query retractRows = new Query("retract(numRows(_)).");
        Query retractColumns = new Query("retract(numCols(_)).");
        Query retractGrab = new Query("retractall(grab(_)).");
        Query retractPit = new Query("retractall(pit(_)).");
        Query retractGold = new Query("retractall(gold(_)).");
        Query retractBreeze = new Query("retractall(breeze(_)).");

        retractWinner.hasSolution();
        retractRows.hasSolution();
        retractColumns.hasSolution();
        retractGrab.hasSolution();
        retractGold.hasSolution();
        retractPit.hasSolution();
        retractBreeze.hasSolution();
    }

    private void hasBreeze() {
        Agent agent = Agent.getInstance();
        int x = agent.getPlayerCoordinates().x();
        int y = agent.getPlayerCoordinates().y();

        String queryString = "has_breeze(" + x + ", " + y +")";
        Query query = new Query(queryString);

        if (query.hasSolution()) {
            agent.addKnownBreezeCoordinates(agent.getPlayerCoordinates());
        }
    }

    private void isPit() {
        Agent agent = Agent.getInstance();
        int x = agent.getPlayerCoordinates().x();
        int y = agent.getPlayerCoordinates().y();

        String queryString = "is_pit(" + x + ", " + y +")";
        Query query = new Query(queryString);

        if (query.hasSolution()) {
            agent.addKnownPitCoordinates(agent.getPlayerCoordinates());
        }
    }

    private void hasGlitter() {
        Agent agent = Agent.getInstance();
        int x = agent.getPlayerCoordinates().x();
        int y = agent.getPlayerCoordinates().y();

        String queryString = "has_glitter(" + x + ", " + y +")";
        Query query = new Query(queryString);

        if (query.hasSolution()) {
            agent.addKnownGoldCoordinates(agent.getPlayerCoordinates());
        }
    }

    private void isHome() {
        Agent agent = Agent.getInstance();
        int x = agent.getPlayerCoordinates().x();
        int y = agent.getPlayerCoordinates().y();

        String queryString = "home(" + x + ", " + y +")";
        Query query = new Query(queryString);

        agent.setIsHome(query.hasSolution());
    }

    private void isUngrabbedGold() {
        Agent agent = Agent.getInstance();
        int x = agent.getPlayerCoordinates().x();
        int y = agent.getPlayerCoordinates().y();

        String queryString = "ungrabbed_gold(" + x + ", " + y +")";
        Query query = new Query(queryString);

        agent.setIsUngrabbedGold(query.hasSolution());
    }

    public int getNumOfGold() {
        String queryString = "findall(_, grab(_, _), L), length(L, Count).";
        Query query = new Query(queryString);

        int numGold = 0;

        if (query.hasSolution()) {
            Term solution = query.oneSolution().get("Count");
            numGold = ((Integer) solution).intValue();
            System.out.println("Number of grabbed gold: " + numGold);
        } else {
            System.out.println("Query failed.");
        }
        return numGold;
    }

    public boolean isWinner() {
        Agent agent = Agent.getInstance();
        int x = getNumOfGold();

        String queryString = "is_winner(" + x + ")";
        Query query = new Query(queryString);

        return query.hasSolution();
    }

    public void assertGrabGold() {
        Agent agent = Agent.getInstance();
        int x = agent.getPlayerCoordinates().x();
        int y = agent.getPlayerCoordinates().y();

        String queryString = "assert(grab(" + x + ", " + y +"))";
        Query query = new Query(queryString);

        if (query.hasSolution()) {
            System.out.println("Success asserting");
        } else {
            System.out.println("Failed to assert");
        }
    }
}