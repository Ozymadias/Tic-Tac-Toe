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
        int currentRow = cell.getX();
        int column = cell.getY();
        int howMany = 1 + howManyOnLeft(currentRow, column) + howManyOnRight(currentRow, column);

        return howMany >= winningNumber;
    }

    int howManyOnLeft(int currentRow, int column) {
        int howMany = 0;
        currentRow -= 1;
        while (currentRow >= 0 && board[currentRow][column] == board[currentRow][column]) {
            howMany += 1;
            currentRow -= 1;
        }
        return howMany;
    }

    int howManyOnRight(int location, int column) {
        int currentLocation = location + 1;
        int howMany = 0;
        while (currentLocation < numberOfColumns && board[currentLocation][column] == board[location][column]) {
            howMany += 1;
            currentLocation += 1;
        }
        return howMany;
    }

    boolean doesColumnWin(Cell cell) {
        int row = cell.getX();
        int currentColumn = cell.getY();
        int howMany = 1 + howManyOnUp(row, currentColumn) + howManyOnDown(row, currentColumn);

        return howMany >= winningNumber;
    }


    int howManyOnUp(int row, int currentColumn) {
        int howMany = 0;
        currentColumn -= 1;
        while (currentColumn >= 0 && board[row][currentColumn] == board[row][currentColumn]) {
            howMany += 1;
            currentColumn -= 1;
        }
        return howMany;
    }

    int howManyOnDown(int row, int currentColumn) {
        currentColumn += 1;
        int howMany = 0;
        while (currentColumn < numberOfRows && board[row][currentColumn] == board[row][currentColumn]) {
            howMany += 1;
            currentColumn += 1;
        }
        return howMany;
    }

    boolean doesDiagonalWin(Cell cell) {
        int row = cell.getX();
        int column = cell.getY();
        int howMany = 1 + howManyUpLeft(row, column) + howManyDownRight(row, column);

        return howMany >= winningNumber;
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

    boolean doesAntiDiagonalWin(Cell cell) {
        int row = cell.getX();
        int column = cell.getY();
        int howMany = 1 + howManyDownLeft(row, column) + howManyUpRight(row, column);

        return howMany >= winningNumber;
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

    public void register(Observer observer) {
        observers.add(observer);
    }

    public void computerMakesMove() {
    }
}
