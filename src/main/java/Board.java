import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.*;

public class Board {
    String[][] cells = create();
    int numberOfRows = 3;
    int numberOfColumns = 3;
    Integer[] columns = {0, 0, 0};
    Integer[] rows = {0, 0, 0};
    Integer[] diagonals = {0, 0};

    private String[][] create() {
        String[][] array = new String[3][3];
        for (int i = 0; i <3; i++) {
            Arrays.fill(array[i], " ");
        }
        return array;
    }

    public void putSignInCell(int row, int column, int playerNumber) {
        if (this.cells[row][column].equals(" "))
            if (playerNumber == 0) {
                this.cells[row][column] = "X";
                updateScores(row, column, 1);
            }
            else {
                this.cells[row][column] = "O";
                updateScores(row, column, -1);
            }
        else
            throw new ForbiddenOperation();
    }

    private void updateScores(int row, int column, int i) {
        columns[row] += i;
        rows[column] += i;
        if (column == row)
            diagonals[0] += i;
        if (column + row == 2)
            diagonals[1] += i;
    }

    @Override
    public String toString() {
        String boardView = "";
        for(int i = 0; i < numberOfColumns; i++) {
            for(int j = 0; j < numberOfRows; j++)
                boardView += this.cells[i][j] + "|";
            boardView += "\n------\n";
        }
        return boardView;
    }

    public boolean isGameOver() {
        return (isBoardFull() || didAnyoneWin());
    }

    public boolean didAnyoneWin() {
        return (isThereWinningColumn() || isThereWinningRow() || isThereWinningDiagonal());
    }

    private boolean isThereWinningDiagonal() {
        return (abs(diagonals[0]) == 3 || abs(diagonals[1]) == 3);
    }

    private boolean isThereWinningRow() {
        return (abs(rows[0]) == 3 || abs(rows[1]) == 3 || abs(rows[2]) == 3);
    }

    private boolean isThereWinningColumn() {
        return (abs(columns[0]) == 3 || abs(columns[1]) == 3 || abs(columns[2]) == 3);
    }

    private boolean isBoardFull() {
        boolean isBoardFull = true;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                if(cells[i][j].equals(" "))
                    isBoardFull = false;
            }
        }
        return isBoardFull;
    }

    public void makeComputerMove(int playerNumber) {
        boolean doesItSucceed = false;

        while (!doesItSucceed) {
            int column = generateRandom();
            int row = generateRandom();
            try {
                putSignInCell(row, column, playerNumber);
                doesItSucceed = true;
            } catch (Board.ForbiddenOperation a){}
        }
    }

    private int generateRandom() {
        Random generator = new Random();
        return generator.nextInt(3);
    }

    public class ForbiddenOperation extends RuntimeException {
    }
}
