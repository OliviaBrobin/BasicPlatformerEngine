package game;

import java.util.Arrays;
import java.util.LinkedList;

import static game.Display.levelGrid;

public class GridFunctions {
    public static Block[][] getExpandedGrid(String side)
    {
        Block[][] newGrid = null;

        if(side.equals("right")) {
            newGrid = new Block[levelGrid.length + 1][levelGrid[0].length];
            for(int x = 0 ; x < newGrid.length - 1 ; x++)
            {
                for(int y = 0 ; y < newGrid[x].length ; y++)
                {
                    newGrid[x][y] = levelGrid[x][y];
                }
            }
        }

        if(side.equals("left")) {
            newGrid = new Block[levelGrid.length + 1][levelGrid[0].length];
            for(int x = 1 ; x < newGrid.length ; x++)
            {
                for(int y = 0 ; y < newGrid[x].length ; y++)
                {
                    newGrid[x][y] = levelGrid[x - 1][y];
                }
            }
        }

        if(side.equals("bottom")) {
            newGrid = new Block[levelGrid.length][levelGrid[0].length + 1];
            for(int x = 0 ; x < newGrid.length ; x++)
            {
                for(int y = 0 ; y < newGrid[x].length - 1 ; y++)
                {
                    newGrid[x][y] = levelGrid[x][y];
                }
            }
        }

        if(side.equals("top")) {
            newGrid = new Block[levelGrid.length][levelGrid[0].length + 1];
            for(int x = 0 ; x < newGrid.length ; x++)
            {
                for(int y = 1 ; y < newGrid[x].length ; y++)
                {
                    newGrid[x][y] = levelGrid[x][y - 1];
                }
            }
        }

        //This is for if we want the squares to turn blank. We may add this functionality later.
        for(int x = 0 ; x < newGrid.length ; x++)
        {
            for(int y = 0 ; y < newGrid[x].length ; y++)
            {
                if(newGrid[x][y] == null)
                {
                    newGrid[x][y] = new Block(new String[] {"blockType-void"});
                }
            }
        }

        if(side.equals("right"))
        {
            for(int y = 0 ; y < newGrid[newGrid.length - 1].length ; y++)
            {
                newGrid[newGrid.length - 1][y] = levelGrid[newGrid.length - 2][y];
            }
        }

        if(side.equals("left")) {
            for(int y = 0 ; y < newGrid[0].length ; y++)
            {
                newGrid[0][y] = newGrid[1][y];
            }
        }

        if(side.equals("bottom")) {
            for(int x = 0 ; x < newGrid.length ; x++)
            {
                newGrid[x][newGrid[x].length - 1] = newGrid[x][newGrid[x].length - 2];
            }
        }

        if(side.equals("top")) {
            for(int x = 0 ; x < newGrid.length ; x++)
            {
                newGrid[x][0] = newGrid[x][1];
            }
        }

        return newGrid;
    }

    public static Block[][] getShrunkGrid(String side)
    {
        Block[][] newGrid = null;

        boolean canShrinkThatWay = ((side.equals("right") || side.equals("left")) && levelGrid.length > 1) || ((side.equals("bottom") || side.equals("top")) && levelGrid[0].length > 1);

        if(canShrinkThatWay) {
            if (side.equals("right")) {
                newGrid = new Block[levelGrid.length - 1][levelGrid[0].length];
                for (int x = 0; x < newGrid.length; x++) {
                    for (int y = 0; y < newGrid[x].length; y++) {
                        newGrid[x][y] = levelGrid[x][y];
                    }
                }
            }

            if (side.equals("left")) {
                newGrid = new Block[levelGrid.length - 1][levelGrid[0].length];
                for (int x = 0; x < newGrid.length; x++) {
                    for (int y = 0; y < newGrid[x].length; y++) {
                        newGrid[x][y] = levelGrid[x + 1][y];
                    }
                }
            }

            if (side.equals("bottom")) {
                newGrid = new Block[levelGrid.length][levelGrid[0].length - 1];
                for (int x = 0; x < newGrid.length; x++) {
                    for (int y = 0; y < newGrid[x].length; y++) {
                        newGrid[x][y] = levelGrid[x][y];
                    }
                }
            }

            if (side.equals("top")) {
                newGrid = new Block[levelGrid.length][levelGrid[0].length - 1];
                for (int x = 0; x < newGrid.length; x++) {
                    for (int y = 0; y < newGrid[x].length; y++) {
                        newGrid[x][y] = levelGrid[x][y + 1];
                    }
                }
            }
            return newGrid;
        }
        return levelGrid;
    }

    public static boolean gridHasProperty(String propertyName, String propertyValue)
    {
        for(int x = 0; x < levelGrid.length ; x++)
        {
            for(int y = 0; y < levelGrid[0].length ; y++)
            {
                if(levelGrid[x][y].hasProperty(propertyName, propertyValue))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasBlockToRight(int x, int y)
    {
        if(x < levelGrid.length - 1)
        {
            return true;
        }
        return false;
    }

    public static boolean hasBlockBelow(int x, int y)
    {
        if(y < levelGrid[0].length - 1)
        {
            return true;
        }
        return false;
    }

    public static Block getBlockToRight(int x, int y)
    {
        try {
            return levelGrid[x + 1][y];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return new Block("");
        }
    }

    public static Block getBlockBelow(int x, int y)
    {
        try {
            return levelGrid[x][y + 1];
        }catch(ArrayIndexOutOfBoundsException e)
        {
            return new Block("");
        }
    }
}
