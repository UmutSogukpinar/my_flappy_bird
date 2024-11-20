package src;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import static src.App.boardHeight;
import static src.App.boardWidth;

public class FlappyBird extends JPanel implements ActionListener, KeyListener
{
    //GAME attributes
    Bird bird;
    Pipe pipe1_bottom;
    Pipe pipe1_top;
    // coming soon ==>>> int score;

    //GAME constants
    static final int gravity = 1;
    static final int gap = 20;  // the gap between upper and lower pipe

    //GAME logic
    Timer gameLoop;

    // loading game images
    Image backgroundImage = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("./images/flappybirdbg.png"))).getImage();
    Image birdImage = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("./images/flappybird.png"))).getImage();
    Image upperPipeImage = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("./images/toppipe.png"))).getImage();
    Image bottomPipeImage = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("./images/bottompipe.png"))).getImage();


    public FlappyBird()
    {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        setFocusable(true);
        addKeyListener(this);

        // bird initialization
        bird = new Bird(birdImage);

        // game timer
        gameLoop = new Timer(1000/50, this);
        gameLoop.start();
    }

    void drawObjectsAndBackground(Graphics graphics)
    {
        //draw background image
        graphics.drawImage(backgroundImage,
                0, 0,
                boardWidth, boardHeight, null);

        // draw the bird image
        graphics.drawImage(birdImage,
                bird.position.x_axis, bird.position.y_axis,
                bird.size.width, bird.size.height, null);
    }

    // drawing attribute
    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        drawObjectsAndBackground(graphics);
    }

    //keyboard movements
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            bird.velocity.y_axis -= 9;
        }
    }

    //bird movement
    public void birdMove()
    {
        bird.velocity.y_axis += gravity;
        bird.position.y_axis += bird.velocity.y_axis;
        bird.position.y_axis = Math.max(bird.position.y_axis, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        birdMove();
        repaint();
    }
}
