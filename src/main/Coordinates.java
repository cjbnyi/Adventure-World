package src.main;

/**
 * Represents a pair of coordinates on a 2d space.
 * Implemented as a record class.
 * @param x - x coordinate
 * @param y - y coordinate
 */
public record Coordinates(int x, int y) {

    public Coordinates {
        if (!isValidCoordinates(x, y)) {
            int numRows = Map.getInstance().getNumRows();
            int numColumns = Map.getInstance().getNumColumns();
            throw new IllegalArgumentException("Invalid coordinates! x must be between 0 and " + (numRows - 1) +
                                               ", y must be between 0 and " + (numColumns - 1) + ".");
        }
    }

    // UTILITY FUNCTIONS
    public static boolean isValidCoordinates(int x, int y) {
        int numRows = Map.getInstance().getNumRows();
        int numColumns = Map.getInstance().getNumColumns();

        boolean isValidXCoordinate = (x >= 0 && x <= numRows - 1);
        boolean isValidYCoordinate = (y >= 0 && y <= numColumns - 1);

        return isValidXCoordinate && isValidYCoordinate;
    }

    public void printCoordinates() {
        System.out.printf("Coordinates: (%d, %d)", x, y);
    }
}
