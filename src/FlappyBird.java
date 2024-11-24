package src;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.sql.Array;
import java.util.Objects;

import static src.App.boardHeight;
import static src.App.boardWidth;
import static src.PipePair.pipeWidth;

public class FlappyBird extends JPanel implements ActionListener, KeyListener, MouseListener
{
    //GAME attributes
    Bird bird;
    PipePair[] pipePairQueue;
    PipePair pipePair;
    // coming soon ==>>> int score;

    //GAME constants
    static boolean isGameOver = false;
    static final int frameXLimit = -2 * pipeWidth;
    static final int frameYUpperLimit = -(boardHeight / 4);

    static final int gravity = 1;
    static final int birdSpeed = 12;
    static final int minBirdSpeed = birdSpeed * 15;

    static final int pipeSpeed = 4;
    static final int pipeNumber = 3;
    static final int gapBetweenPipes = (boardWidth * 2) / 3;
    static final int backToLastOne = pipeNumber * gapBetweenPipes;

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
        addMouseListener(this);

        // bird initialization
        bird = new Bird(birdImage);
        // pipe pair queue initialization
        pipePairQueue = new PipePair[pipeNumber];

        for (int i = 0; i < pipeNumber; i++)
        {
            final int firstPipePosition = boardWidth + gapBetweenPipes * (i + 1);
            // pipe pairs initialization
            pipePair = new PipePair(upperPipeImage, bottomPipeImage, firstPipePosition);

            // pipe pairs added to array
            pipePairQueue[i] = pipePair;
        }

        // game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        gameMovements();
        repaint();
        if (isGameOver)
            gameLoop.stop();
    }

    // drawing attribute
    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        drawObjectsAndBackground(graphics);
    }

    // total movements
    private void gameMovements()
    {
        birdMove();
        allPipeMoves();
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

        // drawing each pipe pair
        for (PipePair pipePair: pipePairQueue)
        {
            graphics.drawImage(pipePair.upperPipe.image,
                    pipePair.upperPipe.position.x_axis, pipePair.upperPipe.position.y_axis,
                    pipePair.upperPipe.size.width, pipePair.upperPipe.size.height,
                    null);
            graphics.drawImage(pipePair.lowerPipe.image,
                    pipePair.lowerPipe.position.x_axis, pipePair.lowerPipe.position.y_axis,
                    pipePair.lowerPipe.size.width, pipePair.lowerPipe.size.height,
                    null);
        }
    }

    //bird movement
    private void birdMove()
    {
        bird.velocity.y_axis += gravity;
        // checking whether velocity reach maximum or not and limiting maximum velocity
        if (bird.velocity.y_axis >= 0)
            bird.position.y_axis += Math.min(bird.velocity.y_axis, minBirdSpeed);
        else
            bird.position.y_axis += Math.max(bird.velocity.y_axis, -minBirdSpeed);

        bird.position.y_axis = Math.max(bird.position.y_axis, frameYUpperLimit);
        if (isBirdUnderTheFrame() || isCollided())
            isGameOver = true;
    }

    private boolean isBirdUnderTheFrame()
    {
        return (bird.position.y_axis >= boardHeight - bird.size.height);
    }

    private boolean isCollided()
    {
        // Bird rectangle
        Rectangle birdRect = new Rectangle(
                bird.position.x_axis,
                bird.position.y_axis,
                bird.size.width,
                bird.size.height
        );

        // Check collision with each pipe pair
        for (PipePair pipePair : pipePairQueue) {
            // Upper pipe rectangle
            Rectangle upperPipeRect = new Rectangle(
                    pipePair.upperPipe.position.x_axis,
                    pipePair.upperPipe.position.y_axis,
                    pipePair.upperPipe.size.width,
                    pipePair.upperPipe.size.height
            );

            // Lower pipe rectangle
            Rectangle lowerPipeRect = new Rectangle(
                    pipePair.lowerPipe.position.x_axis,
                    pipePair.lowerPipe.position.y_axis,
                    pipePair.lowerPipe.size.width,
                    pipePair.lowerPipe.size.height
            );

            // Check for collision
            if (birdRect.intersects(upperPipeRect) || birdRect.intersects(lowerPipeRect))
                return true; // Collision detected
        }
        return false; // No collision
    }

    private void allPipeMoves()
    {
        for (PipePair pipePair: pipePairQueue)
            eachPairPipeMove(pipePair);
    }

    private void eachPairPipeMove(PipePair pipePair)
    {

        pipePair.upperPipe.position.x_axis -= pipeSpeed;
        pipePair.lowerPipe.position.x_axis -= pipeSpeed;

        if (pipePair.upperPipe.position.x_axis <= frameXLimit)
        {
            // first pipe in queue goes to bottom of the queue
            pipePair.updatePipePositions(backToLastOne);

            // updating pipes' length with each new turn
            pipePair.updatePipesLengths();
        }
    }

    //keyboard movements
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            bird.velocity.y_axis -= birdSpeed;
    }

    // mouse movements
    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
            bird.velocity.y_axis -= birdSpeed;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
