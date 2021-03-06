package game;

import java.util.Random;
import main.Cell;

public class GameWithComputerAsFirstPlayer extends Game {

    public GameWithComputerAsFirstPlayer(int numberOfRows, int numberOfColumns) {
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
        boolean doesItSucceed = false;
        while (!doesItSucceed) {
            int column = generateRandom(numberOfColumns);
            int row = generateRandom(numberOfRows);
            Cell cell = new Cell(row, column);
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
