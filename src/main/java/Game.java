import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Game {
    static int numberOfRows = 3;
    static int numberOfColumns = 3;
    static int[][] board = new int[numberOfRows][numberOfColumns];
    static int winningNumber = 3;
    static int currentPlayer = 0;//?static
    ArrayList<Cell> diagonals = new ArrayList<Cell>();
    Optional<Integer> winner = Optional.ofNullable(null);

    public static void create() {
        for (int i = 0; i < numberOfRows; i++)
            Arrays.fill(board[i], -1);
    }

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void makeMove(Cell cell) {
        int row = cell.getX();
        int column = cell.getY();
        board[row][column] = currentPlayer;
        //doesAnyoneWinAfter(cell);
    }

    public static boolean doesAnyoneWinAfter(Cell cell) {
        boolean doesAnyoneWin = (doesRowWin(cell) || doesColumnWin(cell));
        //if (diagonals.contains(cell))

        return doesAnyoneWin;
    }

    private static boolean doesColumnWin(Cell cell) {
        int location = cell.getX();
        int column = cell.getY();
        int howMany = 1;
        int currentLocation = location - 1;

        while (currentLocation >= 0 && board[currentLocation][column] == board[location][column]) {
            howMany += 1;
            currentLocation -= 1;
        }

        currentLocation = location + 1;
        while (currentLocation < numberOfRows && board[currentLocation][column] == board[location][column]) {
            howMany += 1;
            currentLocation += 1;
        }

        if (howMany >= winningNumber)
            return true;
        else
            return false;
    }

    private static boolean doesRowWin(Cell cell) {
        int row = cell.getX();
        int location = cell.getY();
        int howMany = 1;
        int currentLocation = location - 1;

        while (currentLocation >= 0 && board[row][currentLocation] == board[row][location]) {
            howMany += 1;
            currentLocation -= 1;
        }

        currentLocation = location + 1;
        while (currentLocation < numberOfColumns && board[row][currentLocation] == board[row][location]) {
            howMany += 1;
            currentLocation += 1;
        }

        if (howMany >= winningNumber)
            return true;
        else
            return false;
    }
}
