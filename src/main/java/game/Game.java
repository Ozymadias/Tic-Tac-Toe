package game;

import main.Cell;
import main.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    List<Observer> observers = new ArrayList<>();
    int numberOfRows;
    int numberOfColumns;
    int[][] board;
    int winningNumber = 3;
    int currentPlayer = 0;
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
            } else {
                currentPlayer = (currentPlayer + 1) % 2;
                if (isBoardFull()) {
                    notifyAllAboutDraw();
                }
            }
        }
    }

    boolean isEmpty(int i) {
        return (-1) == i;
    }

    void updateAll(Cell cell, int currentPlayer) {
        String sign = getSign(currentPlayer);
        for (Observer observer : observers) {
            observer.update(cell, sign);
        }
    }

    String getSign(int currentPlayer) {
        if (currentPlayer == 0)
            return "X";
        else
            return "O";
    }

    public boolean doesAnyoneWinAfter(Cell cell) {
        isGameOver = (doesRowWin(cell) || doesColumnWin(cell) || doesDiagonalWin(cell) || doesAntiDiagonalWin(cell));
        return isGameOver;
    }

    void notifyAllAboutGameEnd(int currentPlayer) {
        String sign = getSign(currentPlayer);
        for (Observer observer : observers) {
            observer.notifyAboutGameEnd(sign);
        }
    }

    boolean isBoardFull() {
        boolean isBoardFull = true;
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (board[i][j] == -1)
                    isBoardFull = false;
            }
        }
        return isBoardFull;
    }

    void notifyAllAboutDraw() {
        for (Observer observer : observers) {
            observer.notifyAboutDraw();
        }
    }

    boolean doesRowWin(Cell cell) {
        int row = cell.getX();
        int column = cell.getY();
        int howMany = 1 + howManyOnLeft(row, column) + howManyOnRight(row, column);

        return howMany >= winningNumber;
    }

    int howManyOnLeft(int row, int column) {
        int howMany = 0;
        int currentLocation = column - 1;
        while (currentLocation >= 0 && board[row][currentLocation] == board[row][column]) {
            howMany += 1;
            currentLocation -= 1;
        }
        return howMany;
    }

    int howManyOnRight(int row, int column) {
        int howMany = 0;
        int currentLocation = column + 1;
        while (currentLocation < numberOfColumns && board[row][currentLocation] == board[row][column]) {
            howMany += 1;
            currentLocation += 1;
        }
        return howMany;
    }

    boolean doesColumnWin(Cell cell) {
        int row = cell.getX();
        int column = cell.getY();
        int howMany = 1 + howManyOnUp(row, column) + howManyOnDown(row, column);

        return howMany >= winningNumber;
    }


    int howManyOnUp(int row, int column) {
        int howMany = 0;
        int currentLocation = row - 1;
        while (currentLocation >= 0 && board[currentLocation][column] == board[row][column]) {
            howMany += 1;
            currentLocation -= 1;
        }
        return howMany;
    }

    int howManyOnDown(int row, int column) {
        int howMany = 0;
        int currentLocation = row + 1;
        while (currentLocation < numberOfRows && board[currentLocation][column] == board[row][column]) {
            howMany += 1;
            currentLocation += 1;
        }
        return howMany;
    }

    boolean doesDiagonalWin(Cell cell) {
        int row = cell.getX();
        int column = cell.getY();
        int howMany = 1 + howManyUpLeft(row, column) + howManyDownRight(row, column);

        return howMany >= winningNumber;
    }

    int howManyUpLeft(int row, int column) {
        int howMany = 0;
        int currentRow = row - 1;
        int currentColumn = column - 1;
        while (currentRow >= 0 && currentColumn >= 0 && board[currentRow][currentColumn] == board[row][column]) {
            howMany += 1;
            currentRow -= 1;
            currentColumn -= 1;
        }
        return howMany;
    }

    int howManyDownRight(int row, int column) {
        int howMany = 0;
        int currentRow = row + 1;
        int currentColumn = column + 1;
        while (currentRow < numberOfRows && currentColumn < numberOfColumns && board[currentRow][currentColumn] == board[row][column]) {
            howMany += 1;
            currentRow += 1;
            currentColumn += 1;
        }
        return howMany;
    }

    boolean doesAntiDiagonalWin(Cell cell) {
        int row = cell.getX();
        int column = cell.getY();
        int howMany = 1 + howManyDownLeft(row, column) + howManyUpRight(row, column);

        return howMany >= winningNumber;
    }

    int howManyDownLeft(int row, int column) {
        int howMany = 0;
        int currentRow = row + 1;
        int currentColumn = column - 1;
        while (currentRow < numberOfRows && currentColumn >= 0 && board[currentRow][currentColumn] == board[row][column]) {
            howMany += 1;
            currentRow += 1;
            currentColumn -= 1;
        }
        return howMany;
    }

    int howManyUpRight(int row, int column) {
        int howMany = 0;
        int currentRow = row - 1;
        int currentColumn = column + 1;
        while (currentRow >= 0 && currentColumn <numberOfColumns && board[currentRow][currentColumn] == board[row][column]) {
            howMany += 1;
            currentRow -= 1;
            currentColumn += 1;
        }
        return howMany;
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    public void computerMakesMove() {
    }
}
