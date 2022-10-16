package game;

import java.awt.*;

public class BasicScreen {
    Graphics g;
    Block[][] grid;

    int x;
    int y;

    int scalingFactor;


    public BasicScreen()
    {

    }

    public BasicScreen(Graphics g, Block[][] grid, int x, int y, int scalingFactor) {
        this.g = g;
        this.grid = grid;

        this.x = x;
        this.y = y;

        this.scalingFactor = scalingFactor;
    }

    public void display()
    {
        //displayBackground();
        displayGrid();  //Includes editor text
        displayInGameText();
        displayBackgroundImages();
        displayGridOutline();
    }

    /*
    public void displayBackground()
    {
        g.setColor(MyColor.getColor(backgroundColor));
        g.fillRect(x, y, getWidth(), getHeight());
    }
     */

    public void displayGrid()
    {
        if(grid != null) {


            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    try {
                        BlockVisual blockVisual = grid[x][y].getBlockVisual();

                        g.setColor(MyColor.getColor(blockVisual.getColor()));

                        int blockXLocation = (x * scalingFactor) + this.x;
                        int blockYLocation = (y * scalingFactor) + this.y;
                        int blockWidth = scalingFactor;
                        int blockHeight = scalingFactor;
                        g.fillRect(blockXLocation, blockYLocation, blockWidth, blockHeight);

                        //Show editor text
                        g.setColor(MyColor.getColor(blockVisual.getEditorTextColor()));
                        g.setFont(new Font("TimesRoman", Font.BOLD, (int) (scalingFactor * .55)));
                        g.drawString(blockVisual.getEditorText(), blockXLocation, blockYLocation + 1 * scalingFactor);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        //Do nothing
                    }
                }
            }
        }
    }



    public void displayInGameText()
    {
        for(int x = 0; x < grid.length ; x++) {
            for(int y = 0 ; y < grid[x].length ; y++) {


                BlockVisual blockVisual = grid[x][y].getBlockVisual();
                String inGameText = blockVisual.getInGameText();

                if(!inGameText.equals("")) {

                    g.setColor(MyColor.getColor(blockVisual.getInGameTextColor()));
                    g.setFont(new Font("Arial", Font.PLAIN, (int) (scalingFactor * .55)));

                    int blockXLocation = (x * scalingFactor) + this.x;
                    int blockYLocation = (y * scalingFactor) + this.y;

                    double textXAdd = 0;
                    double textYAdd = 0;

                    if(inGameText.equals("You completed the game."))
                    {
                        textXAdd = 1.12 - .5;
                    }

                    g.drawString(inGameText, blockXLocation + (int) textXAdd * scalingFactor, blockYLocation + (int) textYAdd * scalingFactor + scalingFactor * 1);
                }
            }
        }
    }

    public void displayBackgroundImages()
    {
        for(int x = 0; x < grid.length ; x++) {
            for(int y = 0 ; y < grid[x].length ; y++) {

                BlockVisual blockVisual = grid[x][y].getBlockVisual();

                if(!blockVisual.getBackgroundImage().equals(""))
                {
                    Image backgroundImage = Toolkit.getDefaultToolkit().getImage("backgroundImages/" + blockVisual.getBackgroundImage());

                    int blockXLocation = (x * scalingFactor) + this.x;
                    int blockYLocation = (y * scalingFactor) + this.y;

                    //Display image as square
                    g.drawImage(backgroundImage, blockXLocation, blockYLocation, scalingFactor, scalingFactor, null);
                }
            }
        }
    }

    public void displayGridOutline()
    {
        for (int x = 0; x < grid.length; x++) {
            try {
                for (int y = 0; y < grid[x].length; y++) {
                    g.setColor(MyColor.getColor("gridOutline"));
                    g.drawRect(this.x + (x * scalingFactor), this.y +  (y * scalingFactor), scalingFactor, scalingFactor);
                }
            }
            catch(ArrayIndexOutOfBoundsException e)
            {

            }
        }
    }

    public int getMouseX(int screenX)
    {
        int x = (int) Math.floor((screenX - this.x) / scalingFactor);

        return x;
    }

    public int getMouseY(int screenY)
    {
        int y = (int) Math.floor((screenY - this.y) / scalingFactor);

        return y;
    }

    public int getWidth() {
        return grid.length * scalingFactor;
    }

    public int getHeight() {
        int gridHeight = 0;
        for(int x = 0 ; x < grid.length ; x++)
        {
            if(grid[x].length > gridHeight)
            {
                gridHeight = grid[x].length;
            }
        }
        return gridHeight;
    }

}
