package game;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

import static game.Display.*;

public class MouseFunctions {

    public static void mousePressed(MouseEvent e)
    {
        if(editorMode) {
            mousePressedLevelScreen(e);

            mousePressedBlockSelectScreen(e);

            mousePressedButtons(e, buttons);
            mousePressedButtons(e, removePropertyButtons);
        }
    }

    public static void mouseHovered(MouseEvent e)
    {
        if(editorMode) {
            mouseHoveredButtons(e, buttons);
            mouseHoveredButtons(e, removePropertyButtons);
        }
    }

    public  static void mousePressedLevelScreen(MouseEvent e)
    {
        if(e.getX() > levelScreen.x && e.getX() < levelScreen.x + levelScreen.getWidth() && e.getY() > levelScreen.y && e.getY() < levelScreen.y + levelScreen.getHeight()) {
            mousePressedInLevelScreen(e);
        }
    }

    public static void mousePressedInLevelScreen(MouseEvent e)
    {
        int levelGridMouseX = levelScreen.getMouseX(e.getX());
        int levelGridMouseY = levelScreen.getMouseY(e.getY());

        //Select block with right click
        boolean b3 = e.getButton() == MouseEvent.BUTTON3;
        if(b3)
        {
            selectedBlock = levelGrid[levelGridMouseX][levelGridMouseY];
        }

        //Do other actions with left click
        boolean b1 = e.getButton() == MouseEvent.BUTTON1;
        if(b1) {
            if (levelGridMouseX < levelGrid.length && levelGridMouseY < levelGrid[0].length && levelGridMouseX >= 0 && levelGridMouseY >= 0) {
                //Paste the entire selection of blocks
                if (TypingFunctions.holdingControl && TypingFunctions.holdingV) {
                    for (int x = levelGridMouseX; x < levelGridMouseX + selectedBlocksGrid.length; x++) {
                        for (int y = levelGridMouseY; y < levelGridMouseY + selectedBlocksGrid[0].length; y++) {
                            if (x < levelGrid.length && y < levelGrid[x].length) {
                                levelGrid[x][y] = selectedBlocksGrid[x - levelGridMouseX][y - levelGridMouseY];
                            }
                        }
                    }
                }

                if (!(TypingFunctions.holdingShift || (TypingFunctions.holdingControl && TypingFunctions.holdingC) || (TypingFunctions.holdingControl && TypingFunctions.holdingX))) {
                    //Switch single block between solid and void if holding alt
                    if (TypingFunctions.holdingAlt) {
                        levelGrid[levelGridMouseX][levelGridMouseY].switchBetweenSolidAndVoid();
                        //Paste current block
                    } else {
                        levelGrid[levelGridMouseX][levelGridMouseY] = selectedBlock;
                    }
                }

                //If holding shift or control+C or control+X
                if ((TypingFunctions.holdingShift || (TypingFunctions.holdingControl && TypingFunctions.holdingC) || (TypingFunctions.holdingControl && TypingFunctions.holdingX)))
                {
                    //If has not clicked once, set has clicked one to true.
                    if (!TypingFunctions.hasClickedOnce) {
                        TypingFunctions.hasClickedOnce = true;
                        previousLevelGridMouseX = levelGridMouseX;
                        previousLevelGridMouseY = levelGridMouseY;
                    //If has clicked once set hasClickedOnce to false and do pasting stuff.
                    } else {

                        TypingFunctions.hasClickedOnce = false;

                        //Get relative locations of clicks.
                        int smallX;
                        int largeX;

                        int smallY;
                        int bigY;


                        if (previousLevelGridMouseX < levelGridMouseX) {
                            smallX = previousLevelGridMouseX;
                            largeX = levelGridMouseX;
                        } else {
                            largeX = previousLevelGridMouseX;
                            smallX = levelGridMouseX;
                        }

                        if (previousLevelGridMouseY < levelGridMouseY) {
                            smallY = previousLevelGridMouseY;
                            bigY = levelGridMouseY;
                        } else {
                            bigY = previousLevelGridMouseY;
                            smallY = levelGridMouseY;
                        }

                        if ((TypingFunctions.holdingControl && TypingFunctions.holdingC) || (TypingFunctions.holdingControl && TypingFunctions.holdingX)) {
                            //Bring a set of blocks into selected blocks array.
                            selectedBlocksGrid = new Block[largeX - smallX + 1][bigY - smallY + 1];

                            for (int x = smallX; x <= largeX; x++) {
                                for (int y = smallY; y <= bigY; y++) {
                                    selectedBlocksGrid[x - smallX][y - smallY] = levelGrid[x][y];
                                }
                            }

                            //If holding control X, clear selected grid in level grid.
                            if (TypingFunctions.holdingControl && TypingFunctions.holdingX) {
                                for (int x = smallX; x <= largeX; x++) {
                                    for (int y = smallY; y <= bigY; y++) {
                                        levelGrid[x][y] = new Block("void");
                                    }
                                }
                            }
                        //If not holding control C, color a section of blocks in with selected block.
                        } else {
                            for (int x = smallX; x <= largeX; x++) {
                                for (int y = smallY; y <= bigY; y++) {
                                    levelGrid[x][y] = selectedBlock;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void mousePressedBlockSelectScreen(MouseEvent e)
    {
        //Select block from block select
        int blockMouseX = blockSelectScreen.getMouseX(e.getX());
        int blockMouseY = blockSelectScreen.getMouseY(e.getY());

        try {
            Block tempBlock = blockSelectGrid[blockMouseX][blockMouseY];

            if (tempBlock != null) {
                selectedBlock = tempBlock;
            }
        } catch (ArrayIndexOutOfBoundsException error) {
            //Do nothing
        }
    }

    public  static  void mousePressedButtons(MouseEvent e, LinkedList<Button> buttons)
    {
        boolean aButtonWasClicked = false;

        for(Button button : buttons)
        {
            if(e.getX() > button.getX() && e.getX() < button.getX() + button.getWidth() && e.getY() > button.getY() && e.getY() < button.getY() + button.getHeight())
            {
                button.setWasClicked(true);
                aButtonWasClicked = true;
            }
        }

        if(!aButtonWasClicked)
        {
            for(Button button : buttons)
            {
                button.setSelected(false);
            }

            TypingFunctions.typing = false;
        }
    }

    public  static  void mouseHoveredButtons(MouseEvent e, LinkedList<Button> buttons)
    {
        for(Button button : buttons)
        {
            if(e.getX() > button.getX() && e.getX() < button.getX() + button.getWidth() && e.getY() > button.getY() && e.getY() < button.getY() + button.getHeight())
            {
                button.setWasHovered(true);
            }
            else
            {
                button.setWasHovered(false);
            }
        }
    }
}