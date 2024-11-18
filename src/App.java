package src;

import javax.swing.*;

public class App
{
    // Size dimensions of board of game
    static int boardWidth = 360;
    static int boardHeight = 640;

    public static void main(String[] args) throws Exception
    {
        JFrame frame = new JFrame("Flappy Bird");

        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);

        frame.setLocationRelativeTo(null);  // Board will be centered of the screen

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // When the window is closed, jvm stops

        frame.setVisible(true);
    }
}
