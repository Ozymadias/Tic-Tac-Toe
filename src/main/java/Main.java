import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int playerNumber = 0;
        Board board = new Board();
        System.out.println(board);
        System.out.println("X begin the game");

        do {
            if(makeMove(board, playerNumber))
                playerNumber = (playerNumber + 1) % 2;
        } while (!board.isGameOver());

        playerNumber = (playerNumber + 1) % 2;
        
        if (board.didAnyoneWin())
            System.out.println("Game over and the winner is the player number " + (playerNumber + 1));
        else
            System.out.println("Game over and there is no winner");
    }

    private static boolean makeMove(Board board, int playerNumber) {
        boolean didMoveSucceed = false;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Pick row");
        int row = Integer.parseInt(scanner.nextLine());
        System.out.println("Pick column");
        int column = Integer.parseInt(scanner.nextLine());
        try{
            board.putSignInCell(row - 1, column - 1, playerNumber);
            didMoveSucceed = true;
        }
        catch (Board.ForbiddenOperation a){
            System.out.println("You can't put your sign here. There is another sign already. Try again!");
        }
        System.out.println(board);
        return didMoveSucceed;
    }
}
