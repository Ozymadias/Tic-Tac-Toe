import java.util.Arrays;

public class Board {
    String[][] cells = create();
    int numberOfRows = 3;
    int numberOfColumns = 3;
    Integer[] columns = {0, 0, 0};
    Integer[] rows = {0, 0, 0};
    Integer[] diagonals = {0, 0};

    private String[][] create() {
        String[][] array = new String[3][3];
        for (int i = 0; i<3; i++) {
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
        if (column + row == 4)
            diagonals[1] += i;
    }

    @Override
    public String toString() {
        String boardView = "";
        for(int i = 0; i< numberOfColumns; i++) {
            for(int k = 0; k< numberOfRows; k++) {
                    boardView = boardView.concat(this.cells[i][k]);
                boardView = boardView.concat("|");
            }
            boardView = boardView.concat("\n");
            boardView = boardView.concat("-----");
            boardView = boardView.concat("\n");
        }
        return boardView;
    }

    public boolean isGameOver() {
        return (isBoardFull() | didAnyoneWin());
    }

    public boolean didAnyoneWin() {
        return (isThereWinningColumn() | isThereWinningRow() | isThereWinningDiagonal());
    }

    private boolean isThereWinningDiagonal() {
        return (diagonals[0].equals(3) | diagonals[0].equals(-3) | diagonals[1].equals(3) | diagonals[1].equals(-3));
    }

    private boolean isThereWinningRow() {
        return (rows[0].equals(3) | rows[0].equals(-3) | rows[1].equals(3) | rows[1].equals(-3) | rows[2].equals(3) | rows[2].equals(-3));
    }

    private boolean isBoardFull() {
        boolean isBoardFull = true;
        for(String[] column : cells) {
            for (String cell : column){
                if(cell.equals(" "))
                    isBoardFull = false;
            }
        }
        return isBoardFull;
    }

    public boolean isThereWinningColumn() {
        return (columns[0].equals(3) | columns[0].equals(-3) | columns[1].equals(3) | columns[1].equals(-3) | columns[2].equals(3) | columns[2].equals(-3));
    }



    public class ForbiddenOperation extends RuntimeException {
    }
}
