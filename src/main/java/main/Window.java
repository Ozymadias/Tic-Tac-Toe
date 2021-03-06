package main;

import game.Game;
import game.GameWithComputerAsFirstPlayer;
import game.GameWithComputerAsSecondPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener, Observer {
    private JButton[][] buttons = new JButton[3][3];
    private JMenuBar menuBar = new JMenuBar();

    private JMenu newGame = new JMenu("New game");
    private JMenu onePlayer = new JMenu("1 Player game");

    private JMenuItem twoPlayers = new JMenuItem("2 Players game");
    private JMenuItem firstPlayer = new JMenuItem("Play as first player");
    private JMenuItem secondPlayer = new JMenuItem("Play as second player");

    private JLabel infoAboutGame;
    private Game game;
    String winner = "";
    boolean isGameOver = false;
    boolean isDraw = false;

    public Window() {
        setSize(400, 400);
        setTitle("Tic Tac Toe");
        setLayout(null);

        buttons[0][0] = new JButton();
        buttons[0][0].setBounds(100, 100, 50, 50);
        add(buttons[0][0]);
        buttons[0][0].addActionListener(this);

        buttons[0][1] = new JButton();
        buttons[0][1].setBounds(150, 100, 50, 50);
        add(buttons[0][1]);
        buttons[0][1].addActionListener(this);

        buttons[0][2] = new JButton();
        buttons[0][2].setBounds(200, 100, 50, 50);
        add(buttons[0][2]);
        buttons[0][2].addActionListener(this);


        buttons[1][0] = new JButton();
        buttons[1][0].setBounds(100, 150, 50, 50);
        add(buttons[1][0]);
        buttons[1][0].addActionListener(this);

        buttons[1][1] = new JButton();
        buttons[1][1].setBounds(150, 150, 50, 50);
        add(buttons[1][1]);
        buttons[1][1].addActionListener(this);

        buttons[1][2] = new JButton();
        buttons[1][2].setBounds(200, 150, 50, 50);
        add(buttons[1][2]);
        buttons[1][2].addActionListener(this);


        buttons[2][0] = new JButton();
        buttons[2][0].setBounds(100, 200, 50, 50);
        add(buttons[2][0]);
        buttons[2][0].addActionListener(this);

        buttons[2][1] = new JButton();
        buttons[2][1].setBounds(150, 200, 50, 50);
        add(buttons[2][1]);
        buttons[2][1].addActionListener(this);

        buttons[2][2] = new JButton();
        buttons[2][2].setBounds(200, 200, 50, 50);
        add(buttons[2][2]);
        buttons[2][2].addActionListener(this);

        twoPlayers.addActionListener(this);
        firstPlayer.addActionListener(this);
        secondPlayer.addActionListener(this);

        setJMenuBar(menuBar);
        menuBar.add(newGame);
        onePlayer.add(firstPlayer);
        onePlayer.add(secondPlayer);
        newGame.add(onePlayer);
        newGame.add(twoPlayers);

        infoAboutGame = new JLabel("Two player game. X begins.");
        infoAboutGame.setBounds(100, 50, 300, 40);
        add(infoAboutGame);

        game = new Game(3, 3);
        game.register(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JButton) {
            JButton button = (JButton) source;
            infoAboutGame.setText("");

            Cell cell = getCell(button);
            if (!isGameOver) {
                game.makeMove(cell);
                if (isGameOver) {
                    String text = String.format("Game over. The winner is %s", winner);
                    infoAboutGame.setText(text);
                }
                if (isDraw)
                    infoAboutGame.setText("Game over. There is no winner");
            }
        } else if (source instanceof JMenuItem) {
            cleanBoard();
            isGameOver = false;
            isDraw = false;
            
            if (source != twoPlayers) {
                if (source == firstPlayer) {
                    infoAboutGame.setText("Game with computer. You begin.");
                    this.game = new GameWithComputerAsFirstPlayer(3, 3);
                    game.register(this);
                }
                else {
                    infoAboutGame.setText("Game with computer. Your turn.");
                    this.game = new GameWithComputerAsSecondPlayer(3, 3);
                    game.register(this);
                    game.computerMakesMove();
                }
            }
            else {
                infoAboutGame.setText("Two player game. X begins.");
                this.game = new Game(3, 3);
                game.register(this);
            }
        }
    }

    private Cell getCell(JButton button) {
        Cell cell = new Cell(-1, -1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (button.equals(buttons[i][j])) {
                    cell = new Cell(i, j);
                }
            }
        }
        return cell;
    }

    private void cleanBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    @Override
    public void update(Cell cell, String sign) {
        int row = cell.getX();
        int col = cell.getY();
        buttons[row][col].setText(sign);
    }

    @Override
    public void notifyAboutGameEnd(String sign) {
        winner = sign;
        isGameOver = true;
    }

    @Override
    public void notifyAboutDraw() {
        isDraw = true;
    }
}
