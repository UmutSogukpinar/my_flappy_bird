package src;

import java.awt.*;
import java.util.Random;

import static src.App.boardHeight;
import static src.App.boardWidth;

public class PipePair
{
    private final Random random = new Random();

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

    // attributes of PipePair class
    Pipe upperPipe;
    Pipe lowerPipe;

    //common width of the pipes
    public static final int pipeWidth = boardWidth / 6;

    // the gap between upper and lower pipe
    private static final int gap = boardHeight / 6;

    // extra length for the pipes;
    private static final int extraLength = boardHeight / 2;

    public PipePair(Image upperPipeImg, Image lowerPipeImg, int pipesPosition_x)
    {
        // random upper pipe height
         int upperPipeHeight = Math.max(
                random.nextInt((boardHeight * 7) / 10) + extraLength,
                (boardHeight / 4) + extraLength);
         int lowerPipeHeight = boardHeight - upperPipeHeight - gap + extraLength;

        // y axis positions for pipes
        int upperPipePosition_y = -extraLength;
        int lowerPipePosition_y = boardHeight - lowerPipeHeight;

        upperPipe = new Pipe(
                upperPipeImg,
                new Position(pipesPosition_x, upperPipePosition_y),
                new Size(pipeWidth, upperPipeHeight));
        lowerPipe = new Pipe(
                lowerPipeImg,
                new Position(pipesPosition_x, lowerPipePosition_y),
                new Size(pipeWidth, lowerPipeHeight));
    }

    public void updatePipesLengths()
    {
        int newUpperPipeHeight = Math.max(
                random.nextInt((boardHeight * 7) / 10) + extraLength,
                (boardHeight / 4) + extraLength);
        int newLowerPipeHeight = boardHeight - newUpperPipeHeight - gap + extraLength;

        upperPipe.size.height = newUpperPipeHeight;
        lowerPipe.size.height = newLowerPipeHeight;

        lowerPipe.position.y_axis = boardHeight - newLowerPipeHeight;
    }

    public void updatePipePositions(int change)
    {
        upperPipe.position.x_axis += change;
        lowerPipe.position.x_axis += change;
    }
}