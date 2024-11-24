package src;

import java.awt.*;

import static src.App.boardHeight;
import static src.App.boardWidth;

public class Bird
{
    // Attributes
    Image image;
    Position position;
    Velocity velocity;
    Size size;


    // bird feature dimensions
    private static final int birdWidth = 34;
    private static final int birdHeight = 24;

    private static final int birdPosition_x = boardWidth / 8;
    private static final int birdPosition_y = boardHeight / 2;

    private static final int birdVelocity_y = 0;
    private static final int birdVelocity_x = 0;

    public Bird(Image image)
    {
        this.image = image;
        size = new Size(birdWidth, birdHeight);
        position = new Position(birdPosition_x, birdPosition_y);
        velocity = new Velocity(birdVelocity_x, birdVelocity_y);
    }
}