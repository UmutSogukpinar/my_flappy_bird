package src;

import javax.swing.*;

public class App
{
    // Size dimensions of board of game
    final static int boardWidth = 360;
    final static int boardHeight = 640;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Flappy Bird");

        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);  // Board will be centered of the screen
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // When the window is closed, jvm stops

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack(); // pack() method adjusts the window size to fit all of these components properly
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}
