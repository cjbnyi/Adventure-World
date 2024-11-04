import java.util.ArrayList;
import java.util.Arrays;

public class Driver {
    private static int row = 3;
    private static int col = 4;
    private static int gold = 0;

    private static ArrayList<Integer> goldPositions = new ArrayList<Integer>(Arrays.asList(7, 20));
    private static ArrayList<Integer> breezePositions = new ArrayList<Integer>(Arrays.asList(0, 10, 6, 15, 17, 21, 11, 8, 18, 12, 14, 21, 23, 17));
    private static ArrayList<Integer> goldAndBreezePositions = new ArrayList<Integer>(Arrays.asList(2));
    private static ArrayList<Integer> pitPositions = new ArrayList<Integer>(Arrays.asList(16, 22, 5, 13));

    public static void main(String[] args) {
        new GUIController(new GUI(row, col, goldPositions, breezePositions, goldAndBreezePositions, pitPositions));
    } 

    public int getRow() {
        return Driver.row;
    }

    public int getCol() {
        return Driver.col;
    }

    public void setRow(int row) {
        Driver.row = row;
    }

    public void setCol(int col) {
        Driver.col = col;
    }

    public int getGold() {
        return gold;
    }

    public void addGold() {
        Driver.gold++;
    }
}