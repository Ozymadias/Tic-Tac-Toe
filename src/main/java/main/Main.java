package main;

import game.Game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(3, 3);

        Window window = new Window(game);
        game.register(window);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
