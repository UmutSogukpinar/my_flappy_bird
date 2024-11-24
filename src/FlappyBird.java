package src;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.Objects;

import static src.App.boardHeight;
import static src.App.boardWidth;
import static src.PipePair.pipeWidth;

public class FlappyBird extends JPanel implements ActionListener, KeyListener, MouseListener
{
    //GAME attributes
    Bird bird;
    PipePair[] pipePairArray;
    PipePair pipePair;
    // holding to initial position for restart
    PipePair[] initialPipePairArray;
    PipePair initialPipePair;
    // scoreboard
    double score = 0;

    //GAME constants
    static boolean isGameOver = false;
    static final int frameXLimit = -2 * pipeWidth;
    static final int frameYUpperLimit = -(boardHeight / 4);

    static final int initialBirdPosition_y = boardHeight / 2;
    static final int gravity = 1;
    static final int birdSpeed = 12;
    static final int minBirdSpeed = birdSpeed * 15;

    static final int pipeSpeed = 4;
    static final int pipeNumber = 3;
    static final int gapBetweenPipes = (boardWidth * 2) / 3;
    static final int backToLastOne = pipeNumber * gapBetweenPipes;

    static final int scoreboardPosition_x = 10;
    static final int scoreboardPosition_y = 35;

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
        pipePairArray = new PipePair[pipeNumber];
        initialPipePairArray = new PipePair[pipeNumber];

        for (int i = 0; i < pipeNumber; i++)
        {
            final int firstPipePosition = boardWidth + gapBetweenPipes * (i + 1);
            // pipe pairs initialization
            pipePair = new PipePair(upperPipeImage, bottomPipeImage, firstPipePosition);
            initialPipePair = new PipePair(pipePair);

            // pipe pairs added to array
            pipePairArray[i] = pipePair;

            // initial pairs array added
            initialPipePairArray[i] = initialPipePair;
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
        updateScore();
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
        for (PipePair pipePair: pipePairArray)
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

        graphics.setColor(Color.white);

        graphics.setFont(new Font("Arial", Font.PLAIN, 32));
        if (isGameOver)
            graphics.drawString("Game Over: " + (int) score, scoreboardPosition_x, scoreboardPosition_y);
        else
            graphics.drawString(String.valueOf((int) score), scoreboardPosition_x, scoreboardPosition_y);

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
        for (PipePair pipePair : pipePairArray) {
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
        for (PipePair pipePair: pipePairArray)
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

    private void updateScore()
    {
        for(PipePair pipePair: pipePairArray)
        {
            if (Math.abs(pipePair.upperPipe.position.x_axis - bird.position.x_axis) <= pipeSpeed)
                score += 0.5;
        }
    }

    //keyboard movements
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            if (isGameOver)
            {
                //restart game by resetting conditions
                bird.position.y_axis = initialBirdPosition_y;
                bird.velocity.y_axis = 0;
                score = 0;
                restartPipePairs();
                isGameOver = false;
                gameLoop.start();
            }
        }
    }

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
        if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3)
            bird.velocity.y_axis -= birdSpeed;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3)
        {
            if (isGameOver)
                restartGame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    //restart game by resetting conditions
    private void restartGame()
    {
        bird.position.y_axis = initialBirdPosition_y;
        bird.velocity.y_axis = 0;
        score = 0;
        restartPipePairs();
        isGameOver = false;
        gameLoop.start();
    }

    private void restartPipePairs()
    {
        for (int i = 0; i < pipeNumber; i++)
        {
            restartSinglePipe(pipePairArray[i].upperPipe, initialPipePairArray[i].upperPipe);
            restartSinglePipe(pipePairArray[i].lowerPipe, initialPipePairArray[i].lowerPipe);
        }
    }

    private void restartSinglePipe(PipePair.Pipe oldPipe, PipePair.Pipe newPipe)
    {
        oldPipe.position.y_axis = newPipe.position.y_axis;
        oldPipe.position.x_axis = newPipe.position.x_axis;
        oldPipe.size.height = newPipe.size.height;
        oldPipe.size.width = newPipe.size.width;
    }
}
