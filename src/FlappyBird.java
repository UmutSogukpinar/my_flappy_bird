package src;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import static src.App.boardHeight;
import static src.App.boardWidth;

public class FlappyBird extends JPanel
{
    // bird features
    int birdWidth = 34;
    int birdHeight = 24;

    int birdPosition_x = boardWidth / 8;
    int birdPosition_y = boardHeight / 2;

    // game images
    Image birdImage;
    Image backgroundImage;
    Image upperPipeImage;
    Image bottomPipeImage;

    // declaration of Dictionary of images
    Map<String, Image> imageMap = new HashMap<>();

    class Bird
    {
        Image image;

        public Bird(Image image)
        {
            this.image = image;
            Size birdSize = new Size(birdWidth, birdHeight);
            Position birdPosition = new Position(birdPosition_x, birdPosition_y);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //background
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);

    }

    public FlappyBird()
    {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        // loading game images
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("./images/flappybirdbg.png"))).getImage();
        birdImage = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("./images/flappybird.png"))).getImage();
        upperPipeImage = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("./images/toppipe.png"))).getImage();
        bottomPipeImage = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("./images/bottompipe.png"))).getImage();


    }
}
