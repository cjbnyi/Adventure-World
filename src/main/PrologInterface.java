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
    public PrologInterface() {
        String plPath = "src/main/adventure_world.pl";
        Query consultQuery = new Query("consult", new Term[] { new Atom(plPath) });

        consultQuery.hasSolution();
    }

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

        // For debugging purposes
        Map.printMapInformation();
        assertInitialKnowledge();
        isHome();
        isUngrabbedGold();
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
        hasBreeze();
        isPit();
        hasGlitter();
        isHome();
        isUngrabbedGold();
    }

    // TODO
    /**
     * Updates the knowledge base given new information.
     */
    public static void updateKnowledgeBase() {

    }

    public void initializePL() {
        Map map = Map.getInstance();
        int numRows = map.getNumRows();
        int numColumns = map.getNumColumns();
        String filePath = "src/main/adventure_world.pl";

        // Define the content to write to the Prolog file
        String prologContent = ":- dynamic grab/2.\n" +
                "\n" +
                "% Sensors\n" +
                "is_safe(X, Y) :-\n" +
                "    \\+ pit(X, Y).\n" +
                "\n" +
                "is_home(X, Y) :-\n" +
                "    \\+ gold(X, Y),\n" +
                "    \\+ breeze(X, Y),\n" +
                "    is_safe(X, Y).\n" +
                "\n" +
                "is_pit(X, Y) :-\n" +
                "    pit(X, Y).\n" +
                "\n" +
                "has_breeze(X1, Y1) :-\n" +
                "    SizeX = " + numRows + ",\n" +
                "    SizeY = " + numColumns + ",\n" +
                "    ( Y1 < SizeY, Up is Y1 - 1,    pit(X1, Up)\n" +
                "    ; Y1 > 0,     Down is Y1 + 1,  pit(X1, Down)\n" +
                "    ; X1 < SizeX, Left is X1 - 1,  pit(Left, Y1)\n" +
                "    ; X1 > 0,     Right is X1 + 1, pit(Right, Y1)\n" +
                "    ).\n" +
                "\n" +
                "has_glitter(X1, Y1) :-\n" +
                "    gold(X1, Y1).\n" +
                "\n" +
                "ungrabbed_gold(X1, Y1) :-\n" +
                "    gold(X1, Y1),\n" +
                "    \\+ grab(X1, Y1).\n" +
                "\n" +
                "% Goals\n" +
                "is_gold(X1, Y1) :-\n" +
                "    has_glitter(X1, Y1).\n" +
                "\n" +
                "% Unclassified\n" +
                "distance(X1, Y1, X2, Y2, D) :-\n" +
                "    abs(X1 - X2) + abs(Y1 - Y2) =:= D.\n" +
                "\n" +
                "adjacent(X1, Y1, X2, Y2) :-\n" +
                "    distance(X1, Y1, X2, Y2, 1).\n" +
                "\n" +
                "is_safe_tile(X, Y) :-\n" +
                "    is_safe(X, Y).";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(prologContent);
            System.out.println("Prolog file written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
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

    // TODO: Feel free to add/remove/modify methods here as you deem appropriate.
}