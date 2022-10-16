package game;

import java.awt.*;

import static game.Display.*;

public class LevelScreen {
    //When we start moving the screen independently of the player it should switch into a different mode that it can switch back to the normal mode if we press some specific button or simply start moving the guy again. I think this mode could involve using gridHeight.
    //We want to be able to get the grid to work like RFTS where it's fixed.
    //We might want scaling factor to be able to be an input.
    //The player should be almost but not quite centered, a bit left, or almost completely centered I think.
    //TODO implement vertical and freeScrolling
    //TODO player needs to be cut off if off screen.


    Graphics g;
    Block[][] grid;

    double x;
    double y;
    double width;
    double height;
    static double gridXOffset = 0;
    static double offsetAcceleration = 0;
    double gridYOffset = 0;

    double scalingFactor;

    String backgroundColor = "levelBackground";


    public LevelScreen()
    {

    }


    public LevelScreen(Graphics g, Block[][] grid, double x, double y, double width, double height) {

        this.g = g;
        this.grid = grid;

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        scalingFactor = 0;
    }

    public void useGridHeight()
    {
        if(grid != null) {
            scalingFactor = height / grid[0].length;
        }
    }


    public void display()
    {
        //TODO There should eventually be displayForegroundImages, displayBackgroundImages.
        displayBackground();
        displayBackgroundImages();
        displayGrid();
        displayInGameText();
        displayGridOutline();
        displayPlayer();
        displayOutline();
        colorAroundScreen();
    }

    public void displayBackground()
    {
        g.setColor(MyColor.getColor(backgroundColor));
        g.fillRect((int) x,(int) y, (int) width,(int) height);
    }

    public void displayGrid()
    {
        if(grid != null) {

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    try {
                        BlockVisual blockVisual = grid[x][y].getBlockVisual();

                        g.setColor(MyColor.getColor(blockVisual.getColor()));

                        double xValue = x + gridXOffset;
                        double yValue = y + gridYOffset;

                        double blockXLocation = (xValue * scalingFactor) + this.x;
                        double blockYLocation = (yValue * scalingFactor) + this.y;
                        double blockWidth = scalingFactor + 1;
                        double blockHeight = scalingFactor + 1;



                        if(blockXLocation < this.x + width && blockYLocation < this.y + height && blockXLocation + blockWidth > this.x && blockYLocation + blockHeight > this.y) {

                            if (blockXLocation + blockWidth > this.x + width) {
                                blockWidth = this.x + width - blockXLocation;
                            }

                            if (blockYLocation + blockHeight > this.y + height) {
                                blockHeight = this.y + height - blockYLocation;
                            }

                            if (blockXLocation < this.x) {
                                blockWidth = blockXLocation + blockWidth - this.x;
                                blockXLocation = this.x;
                            }

                            if (blockYLocation < this.y) {
                                blockHeight = blockYLocation + blockHeight - this.y;
                                blockYLocation = this.y;
                            }

                            //Don't fill void blocks so that background images are seen.
                            if(!blockVisual.getColor().equals("void")) {
                                g.fillRect((int) blockXLocation, (int) blockYLocation, (int) blockWidth, (int) blockHeight);
                            }
                        }


                        //Show editor text
                        if (printEditorText && editorMode) {
                            g.setColor(MyColor.getColor(blockVisual.getEditorTextColor()));
                            g.setFont(new Font("TimesRoman", Font.BOLD, (int) (scalingFactor * .55)));
                            g.drawString(blockVisual.getEditorText(), (int) ((x * scalingFactor) + this.x), (int) ((y * scalingFactor) + this.y + (int) scalingFactor));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

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

                    g.drawString(inGameText, (int) ((x + blockVisual.getInGameTextX()) * scalingFactor + this.x), (int) (((y + blockVisual.getInGameTextY()) * scalingFactor) + this.y + (int) scalingFactor));
                }
            }
        }
    }

    public void displayBackgroundImages()
    {
        for(int x = 0; x < grid.length ; x++) {
            for(int y = 0 ; y < grid[x].length ; y++) {

                BlockVisual blockVisual = grid[x][y].getBlockVisual();
                if(!blockVisual.getBackgroundImage().equals("")) {

                    double xValue = x + gridXOffset;
                    double yValue = y + gridYOffset;

                    double blockXLocation = (xValue * scalingFactor) + this.x;
                    double blockYLocation = (yValue * scalingFactor) + this.y;


                    if(!blockVisual.getBackgroundImage().equals(""))
                    {
                        Image backgroundImage = Toolkit.getDefaultToolkit().getImage("backgroundImages/" + blockVisual.getBackgroundImage());
                        //g.drawImage(backgroundImage, (int) blockXLocation, (int) blockYLocation, , (int) blockXLocation, (int) blockYLocation, (int) (x * scalingFactor), (int) (y * scalingFactor),  null);
                    }
                }
            }
        }
    }

    public void displayGridOutline()
    {
        if(Display.levelHasGridOutline && editorMode) {
            for (int x = 0; x < grid.length; x++) {
                try {
                    for (int y = 0; y < grid[x].length; y++) {
                        double xValue = x + gridXOffset;
                        double yValue = y + gridYOffset;

                        double blockXLocation = (xValue * scalingFactor) + this.x;
                        double blockYLocation = (yValue * scalingFactor) + this.y;
                        double blockWidth = scalingFactor + 1;
                        double blockHeight = scalingFactor + 1;


                        if(blockXLocation < this.x + width && blockYLocation < this.y + height && blockXLocation + blockWidth > this.x && blockYLocation + blockHeight > this.y) {

                            if (blockXLocation + blockWidth > this.x + width) {
                                blockWidth = this.x + width - blockXLocation;
                            }

                            if (blockYLocation + blockHeight > this.y + height) {
                                blockHeight = this.y + height - blockYLocation;
                            }

                            if (blockXLocation < this.x) {
                                blockWidth = blockXLocation + blockWidth - this.x;
                                blockXLocation = this.x;
                            }

                            if (blockYLocation < this.y) {
                                blockHeight = blockYLocation + blockHeight - this.y;
                                blockYLocation = this.y;
                            }

                            g.setColor(MyColor.getColor("gridOutline"));
                            g.drawRect((int) (blockXLocation),(int) (blockYLocation), (int) blockWidth, (int) blockHeight);
                        }
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
    }

    public void displayPlayer()
    {
        if (Display.player.exists()) {
            g.setColor(MyColor.getColor(Display.player.getColor()));

            double xValue = Display.player.getX() + gridXOffset;
            double yValue = Display.player.getY() + gridYOffset;

            int playerXLocation = (int) ((xValue * scalingFactor) + this.x);
            int playerYLocation = (int) ((yValue * scalingFactor) + this.y);
            int playerWidth = (int) (Display.player.getWidth() * scalingFactor) + 1;
            int playerHeight = (int) (Display.player.getHeight() * scalingFactor) + 1;

            if(player.getX() < 10 || player.getX() > grid.length - width / scalingFactor)
            {
                g.fillRect(playerXLocation, playerYLocation, playerWidth, playerHeight);
            }
            else
            {
                g.fillRect((int) (10 * scalingFactor + this.x), playerYLocation, playerWidth, playerHeight);
            }
        }
    }

    public void colorAroundScreen()
    {
        if (editorMode) {
            g.setColor(MyColor.getColor(EDITOR_BACKGROUND_COLOR));
        } else {

            g.setColor(MyColor.getColor(EDITOR_BACKGROUND_COLOR));
        }
        g.fillRect(0, 0, (int) (x), 10000);
        g.fillRect(0, 0, 100000, (int) (y));
        g.fillRect((int) (x + grid.length * scalingFactor),0, 10000, 10000);
        g.fillRect(0, (int) (y + grid[0].length * scalingFactor), 10000, 10000);

    }

    public void displayOutline()
    {
        if(editorMode)
        {
            g.setColor(MyColor.getColor("gridOutline"));
            g.drawRect((int) x,(int) y,(int) width,(int) height);
        }
    }

    public int getMouseX(int screenX)
    {
        int x = (int) Math.floor((screenX - this.x) / scalingFactor - gridXOffset);

        return x;
    }

    public int getMouseY(int screenY)
    {
        int y = (int) Math.floor((screenY - this.y) / scalingFactor - gridYOffset);

        return y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getScalingFactor() {
        return scalingFactor;
    }

}
