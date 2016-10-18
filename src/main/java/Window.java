import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private JMenuBar menuBar = new JMenuBar();;
    private JMenu newGame = new JMenu("New Game");
    private JMenu onePlayer = new JMenu("1 Player Game");;
    private JMenuItem twoPlayers = new JMenuItem("2 Players Game");
    private JMenuItem firstPlayer = new JMenuItem("Play as first player");
    private JMenuItem secondPlayer = new JMenuItem("Play as second player");;
    private JLabel gameOver;

    public Window(){
        setSize(400, 400);
        setTitle("Tic Tac Toe");
        setLayout(null);

        buttons[0][0] = new JButton();
        buttons[0][0].setBounds(100,100,50,50);
        add(buttons[0][0]);
        buttons[0][0].addActionListener(this);

        buttons[0][1] = new JButton();
        buttons[0][1].setBounds(150,100,50,50);
        add(buttons[0][1]);
        buttons[0][1].addActionListener(this);
        
        buttons[0][2] = new JButton();
        buttons[0][2].setBounds(200,100,50,50);
        add(buttons[0][2]);
        buttons[0][2].addActionListener(this);

        
        buttons[1][0] = new JButton();
        buttons[1][0].setBounds(100,150,50,50);
        add(buttons[1][0]);
        buttons[1][0].addActionListener(this);
        
        buttons[1][1] = new JButton();
        buttons[1][1].setBounds(150,150,50,50);
        add(buttons[1][1]);
        buttons[1][1].addActionListener(this);
        
        buttons[1][2] = new JButton();
        buttons[1][2].setBounds(200,150,50,50);
        add(buttons[1][2]);
        buttons[1][2].addActionListener(this);

        
        buttons[2][0] = new JButton();
        buttons[2][0].setBounds(100,200,50,50);
        add(buttons[2][0]);
        buttons[2][0].addActionListener(this);
        
        buttons[2][1] = new JButton();
        buttons[2][1].setBounds(150,200,50,50);
        add(buttons[2][1]);
        buttons[2][1].addActionListener(this);
        
        buttons[2][2] = new JButton();
        buttons[2][2].setBounds(200,200,50,50);
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

        gameOver = new JLabel("");
        gameOver.setBounds(100, 50, 150, 40);
        add(gameOver);
    }

    public static void main(String[] args) {
        Game.create();
        Window window = new Window();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        Cell cell = new Cell(-1, -1);

        if (source instanceof JButton) {
            JButton button = (JButton) source;

            for(int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++){
                    if (button.equals(buttons[i][j])){
                        cell = new Cell(i, j);
                    }
                }
            }

            if (button.getText().equals("")) {
                if (Game.getCurrentPlayer() == 0)
                    button.setText("X");
                else
                    button.setText("O");
            }

            Game.makeMove(cell);
            if (Game.doesAnyoneWinAfter(cell))
                gameOver.setText("Game over and the winner is player number " + Game.currentPlayer);
        }
        else if(source instanceof JMenuItem)
        {
            cleanBoard();
            if (source == twoPlayers)
                ;
        }
    }

    private void cleanBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

}
