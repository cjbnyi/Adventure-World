package src.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a map having static environment information.
 * Employs the Singleton design pattern.
 */
public class Map {

    public static final int MAX_ROWS = 6;
    public static final int MAX_COLUMNS = 6;
    public static final char PLAIN_CELL = '.';
    public static final char HOME_CELL = 'H';
    public static final char PIT_CELL = 'P';
    public static final char GOLD_CELL = 'G';

    private static Map instance = null;

    private final int numRows;
    private final int numColumns;
    private final char[][] mapInfo;

    private Map(int numRows, int numColumns, char[][] mapInfo) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.mapInfo = mapInfo;
    }

    public static void initializeInstance(int numRows, int numColumns, char[][] mapInfo) {
        if (instance != null) {
            throw new IllegalStateException("Map has already been initialized." +
                    "Retrieve it through getInstance().");
        }
        instance = new Map(numRows, numColumns, mapInfo);
    }

    public static Map getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Map has not yet been initialized." +
                    "Initialize it through initializeInstance().");
        }
        return instance;
    }

    // GETTERS
    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public char[][] getMapInfo() {
        return mapInfo;
    }

    // UTILITY FUNCTIONS
    public static boolean isValidMapCellChar(int character) {
        return character == PLAIN_CELL || character == HOME_CELL || character == PIT_CELL || character == GOLD_CELL;
    }

    public static void printMapInformation() {
        Map map = getInstance();
        int numRows = map.getNumRows();
        int numColumns = map.getNumColumns();

        System.out.println("\n[Printing map information]");
        System.out.println("Rows = " + numRows + " | Columns = " + numColumns);
        System.out.println("Map:");
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.out.print(map.getMapInfo()[i][j]);
            }
            System.out.println();
        }
    }
}
