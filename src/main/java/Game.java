import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {
    private List<Observer> observers = new ArrayList<Observer>();
    private int numberOfRows;
    private int numberOfColumns;
    private int[][] board;
    private int winningNumber = 3;
    private int currentPlayer = 0;
    boolean isGameOver = false;

    public Game(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.board = new int[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++)
            Arrays.fill(board[i], -1);
    }

    public void makeMove(Cell cell) {
        int row = cell.getX();
        int column = cell.getY();
        if (isEmpty(board[row][column]) && !isGameOver) {
            board[row][column] = currentPlayer;
            updateAll(cell, currentPlayer);
            if (doesAnyoneWinAfter(cell)) {
                notifyAllAboutGameEnd(currentPlayer);
                cleanBoard();
            } else
                currentPlayer = (currentPlayer + 1) % 2;
        }
    }

    private boolean isEmpty(int i) {
        return (-1) == i;
    }

    private void updateAll(Cell cell, int currentPlayer) {
        String sign = getSign(currentPlayer);
        for (Observer observer : observers) {
            observer.update(cell, sign);
        }
    }

    private String getSign(int currentPlayer) {
        if (currentPlayer == 0)
            return "X";
        else
            return "O";
    }

    public boolean doesAnyoneWinAfter(Cell cell) {
        isGameOver = (doesRowWin(cell) || doesColumnWin(cell));
        return isGameOver;
    }

    private void notifyAllAboutGameEnd(int currentPlayer) {
        String sign = getSign(currentPlayer);
        for (Observer observer : observers) {
            observer.notifyAboutGameEnd(sign);
        }
    }

    private void cleanBoard() {
        currentPlayer = 0;
        isGameOver = false;
        for (int i = 0; i < numberOfRows; i++)
            Arrays.fill(board[i], -1);
    }

    private boolean doesRowWin(Cell cell) {
        int location = cell.getX();
        int column = cell.getY();
        int howMany = 1 + howManyOnLeft(location, column) + howManyOnRight(location, column);

        return howMany >= winningNumber;
    }

    private int howManyOnLeft(int location, int column) {
        int howMany = 0;
        int currentLocation = location - 1;
        while (currentLocation >= 0 && board[currentLocation][column] == board[location][column]) {
            howMany += 1;
            currentLocation -= 1;
        }
        return howMany;
    }

    private int howManyOnRight(int location, int column) {
        int currentLocation = location + 1;
        int howMany = 0;
        while (currentLocation < numberOfColumns && board[currentLocation][column] == board[location][column]) {
            howMany += 1;
            currentLocation += 1;
        }
        return howMany;
    }

    private boolean doesColumnWin(Cell cell) {
        int row = cell.getX();
        int location = cell.getY();
        int howMany = 1;
        int currentLocation = location - 1;

        while (currentLocation >= 0 && board[row][currentLocation] == board[row][location]) {
            howMany += 1;
            currentLocation -= 1;
        }

        currentLocation = location + 1;
        while (currentLocation < numberOfRows && board[row][currentLocation] == board[row][location]) {
            howMany += 1;
            currentLocation += 1;
        }

        return howMany >= winningNumber;
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    public void makeComputerMove() {
        boolean doesItSucceed = false;
        while (!doesItSucceed) {
            int column = generateRandom(numberOfColumns);
            int row = generateRandom(numberOfRows);
            Cell cell = new Cell(row, column);

            makeMove(cell);
        }
    }

    private int generateRandom(int max) {
        Random generator = new Random();
        return generator.nextInt(max);
    }
}
