package src;

import java.awt.*;

import static src.App.boardHeight;
import static src.App.boardWidth;

public class Bird
{
    // Attributes
    Position position;
    Size size;
    Image image;

    // bird feature dimensions
    int birdWidth = 34;
    int birdHeight = 24;

    int birdPosition_x = boardWidth / 8;
    int birdPosition_y = boardHeight / 2;

    public Bird(Image image)
    {
        this.image = image;
        size = new Size(birdWidth, birdHeight);
        position = new Position(birdPosition_x, birdPosition_y);
    }
}