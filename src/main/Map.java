package src.main;

/**
 * Represents a map having static environment information.
 * Employs the Singleton design pattern.
 */
public class Map {

    private static Map instance = null;

    private final int numRows;
    private final int numColumns;
    private final char[][] mapInfo;

    private Map(int numRows, int numColumns, char[][] mapInfo) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.mapInfo = mapInfo;
    }

    public static Map initializeInstance(int numRows, int numColumns, char[][] mapInfo) {
        if (instance != null) {
            throw new IllegalStateException("Map has already been initialized." +
                    "Retrieve it through getInstance().");
        }
        instance = new Map(numRows, numColumns, mapInfo);
        return instance;
    }

    public static Map getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Map has not yet been initialized." +
                    "Initialize it through initializeInstance().");
        }
        return instance;
    }
}
