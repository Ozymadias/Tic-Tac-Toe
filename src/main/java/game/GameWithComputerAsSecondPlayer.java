package game;

import main.Cell;

import java.util.Random;

public class GameWithComputerAsSecondPlayer extends Game {
    public GameWithComputerAsSecondPlayer(int numberOfRows, int numberOfColumns) {
        super(numberOfRows, numberOfColumns);
    }

    @Override
    public void makeMove(Cell cell) {
        int row = cell.getX();
        int column = cell.getY();
        if (isEmpty(board[row][column])) {
            super.makeMove(cell);
            if (!isGameOver)
                computerMakesMove();
        }
    }

    public void computerMakesMove() {
        Cell cell;
        boolean doesItSucceed = false;
        while (!doesItSucceed) {
            int column = generateRandom(numberOfColumns);
            int row = generateRandom(numberOfRows);
            cell = new Cell(row, column);

            row = cell.getX();
            column = cell.getY();
            if (board[row][column] == -1) {
                super.makeMove(cell);
                doesItSucceed = true;
            }
        }
    }

    private int generateRandom(int max) {
        Random generator = new Random();
        return generator.nextInt(max);
    }
}
