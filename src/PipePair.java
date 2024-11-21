package src;

import java.awt.*;
import java.util.Random;

import static src.App.boardHeight;
import static src.App.boardWidth;
import static src.FlappyBird.gap;

public class PipePair
{
    class Pipe
    {
        Position position;
        Size size;
        Image image;

        public Pipe(Image image, Position position, Size size)
        {
            this.image = image;
            this.position = position;
            this.size = size;
        }
    }

    Pipe upperPipe;
    Pipe lowerPipe;

    static final int pipeWidth = boardWidth / 6;

    private final Random random = new Random();

    // random upper pipe height
    int upperPipeHeight = Math.max(random.nextInt((boardHeight * 7) / 10), boardHeight / 4);
    int lowerPipeHeight = Math.max(boardHeight - upperPipeHeight - gap, boardHeight / 10);

    // y axis positions for pipes
    int upperPipePosition_y = 0;
    int lowerPipePosition_y = boardHeight - lowerPipeHeight;

    public PipePair(Image upperPipeImg, Image lowerPipeImg)
    {
        upperPipe = new Pipe(upperPipeImg, new Position(boardWidth / 2, upperPipePosition_y),
                new Size(pipeWidth, upperPipeHeight));
        lowerPipe = new Pipe(lowerPipeImg, new Position(boardWidth / 2, lowerPipePosition_y),
                new Size(pipeWidth, lowerPipeHeight));
    }
}