package src;

import java.awt.*;

import static src.App.boardWidth;


public class Pipe
{
    int pipeWidth = boardWidth / 6;

    Position position;
    Size size;
    Image image;

    public Pipe(Image image, int pipeHeight, int pipePosition_y)
    {
        this.image = image;
        size = new Size(pipeWidth);
        size.height = pipeHeight;
        position = new Position(boardWidth/2, pipePosition_y);
    }
}

class PipeCouple
{
    Pipe upperPipe;
    Pipe lowerPipe;

    public PipeCouple(Pipe upperPipe, Pipe lowerPipe)
    {
        this.upperPipe = upperPipe;
        this.lowerPipe = lowerPipe;
    }
}