package src;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Objects;

import static src.App.boardHeight;
import static src.App.boardWidth;

public class FlappyBird extends JPanel implements ActionListener, KeyListener
{
    //GAME attributes
    Bird bird;
    PipePair pipePair_1;
    // coming soon ==>>> int score;

    //GAME constants
    static final int gravity = 1;
    static final int gap = boardHeight / 8;  // the gap between upper and lower pipe
    static final int pipeSpeed = 10;

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

        // pipe pairs initialization
        pipePair_1 = new PipePair(upperPipeImage, bottomPipeImage);

        // game timer
        gameLoop = new Timer(1000/40, this);
        gameLoop.start();
    }

    private void drawObjectsAndBackground(Graphics graphics)
    {
        //draw background image
        graphics.drawImage(backgroundImage,
                0, 0,
                boardWidth, boardHeight,
                null);

        // draw the bird image
        graphics.drawImage(birdImage,
                bird.position.x_axis, bird.position.y_axis,
                bird.size.width, bird.size.height,
                null);

        // draw pipe pair-1
        graphics.drawImage(pipePair_1.upperPipe.image,
                pipePair_1.upperPipe.position.x_axis, pipePair_1.upperPipe.position.y_axis,
                pipePair_1.upperPipe.size.width, pipePair_1.upperPipe.size.height,
                null);
        graphics.drawImage(pipePair_1.lowerPipe.image,
                pipePair_1.lowerPipe.position.x_axis, pipePair_1.lowerPipe.position.y_axis,
                pipePair_1.lowerPipe.size.width, pipePair_1.lowerPipe.size.height,
                null);
    }

    //bird movement
    private void birdMove()
    {
        bird.velocity.y_axis += gravity;
        bird.position.y_axis += bird.velocity.y_axis;
        bird.position.y_axis = Math.max(bird.position.y_axis, 0);
    }

    private void pipeMove()
    {
        pipePair_1.upperPipe.position.x_axis -= pipeSpeed;
        pipePair_1.lowerPipe.position.x_axis -= pipeSpeed;

        if (pipePair_1.upperPipe.position.x_axis <= -pipePair_1.upperPipe.size.width)
        {
            pipePair_1.upperPipe.position.x_axis = boardWidth;
            pipePair_1.lowerPipe.position.x_axis = boardWidth;

            pipePair_1.updatePipesLengths();
        }
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
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            bird.velocity.y_axis -= 9;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        birdMove();
        pipeMove();
        repaint();
    }
}
