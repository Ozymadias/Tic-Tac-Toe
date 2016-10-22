import java.util.Scanner;

public class ConsoleUI {

    public static void main(String[] args) {

        Board board = new Board();

        boolean isChoiceProper = false;
        System.out.println("How many players?");
        while (!isChoiceProper) {
            int numberOfPlayers = scanner();

            if (numberOfPlayers == 2 || numberOfPlayers == 1) {
                playWith(numberOfPlayers, board);
                isChoiceProper = true;
            }
            else
                System.out.println("Wrong number! Try again!");
        }
    }

    private static void playWith(int numberOfPlayers, Board board) {
        int playerNumber = 1;
        int computerNumber = -1;

        if (numberOfPlayers == 1) {
            System.out.println("Which player do you want to be? If player first one pick 1, if second one pick 2.");
            computerNumber = 3 - getProperNumber() - 1;
        }

        System.out.println(board);
        System.out.println("X begin the game");

        do {
            playerNumber = (playerNumber + 1) % 2;
            if (playerNumber == computerNumber)
                board.makeComputerMove(playerNumber);
            else
                makeMove(board, playerNumber);
            System.out.println(board);
        } while (!board.isGameOver());

        if (board.didAnyoneWin())
            System.out.println("Game over and the winner is the player number " + (playerNumber + 1));
        else
            System.out.println("Game over and there is no winner");
    }

    private static int getProperNumber() {
        boolean isChoiceProper = false;
        int humanNumber = 0;
        while (!isChoiceProper) {
            humanNumber = scanner();

            if (humanNumber == 2 || humanNumber == 1) {
                isChoiceProper = true;
            }
            else
                System.out.println("Wrong number! Try again!");
        }
        return humanNumber;
    }

    private static int scanner() {
        Scanner scanner = new Scanner(System.in);
        return Integer.parseInt(scanner.nextLine());
    }

    private static void makeMove(Board board, int playerNumber) {
        boolean didMoveSucceed = false;

        while (!didMoveSucceed) {
            Cell cell = getCell();
            try {
                board.putSignInCell(cell.getX() - 1, cell.getY() - 1, playerNumber);
                didMoveSucceed = true;
            } catch (Board.ForbiddenOperation a) {
                System.out.println("You can't put your sign here. There is another sign already. Try again!");
            }
        }
    }

    private static Cell getCell() {
        System.out.println("Pick row");
        int row = scanner();
        System.out.println("Pick column");
        int column = scanner();
        return new Cell(row, column);
    }
}