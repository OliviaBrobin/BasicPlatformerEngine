package game;

import java.awt.event.KeyEvent;
import java.util.Optional;

import static game.Display.*;

public class TypingFunctions {

    //Typing variables
    public static boolean holdingShift = false;
    public static boolean holdingControl = false;
    public static boolean holdingAlt = false;
    public static boolean holdingPoint = false;
    public static boolean holdingC = false;
    public static boolean holdingV = false;
    public static boolean holdingX = false;
    public static boolean hasClickedOnce = false;
    public static boolean typing = false;
    public static boolean pressedSpaceToJump = false;

    public static void keyPressActions(int keyCode)
    {
        //Check if keys are being held.
        setHoldingKeys(keyCode);

        //Player movement
        if((!typing && !holdingControl) || !editorMode) {
           playerMovement(keyCode);

           escapeKey(keyCode);
        }

        //Editor functions
        if(STARTED_IN_EDITOR_MODE && !typing) {
            levelFunctions(keyCode);
            levelDisplayFunctions(keyCode);
            levelGridFunctions(keyCode);

            //Switch between editor and not editor mode
            if (keyCode == KeyEvent.VK_Q) {
                editorMode = !editorMode;
            }
        }

        //Typing functions
        if(editorMode) {

            //Typing
            if (typing) {
               typingFunctions(keyCode);
            } else {
                //Enter typing export file name
                if (keyCode == KeyEvent.VK_T && !holdingAlt) {
                    typing = true;
                    currentLevelName = "";
                    levelNameButton.setSelected(true);
                }
            }
        }
    }

    public static void keyReleaseActions(int keyCode)
    {
        if(keyCode == KeyEvent.VK_A){
            player.setMovingLeft(false);
        }
        if(keyCode == KeyEvent.VK_D){
            player.setMovingRight(false);
        }
        if(keyCode == KeyEvent.VK_W){
            player.setMovingUp(false);
        }
        if(keyCode == KeyEvent.VK_S) {
            player.setMovingDown(false);
        }
        if(keyCode == KeyEvent.VK_SPACE) {
            player.holdingJump = false;
            pressedSpaceToJump = false;
        }
        if(keyCode == KeyEvent.VK_SHIFT){
            holdingShift = false;
            hasClickedOnce = false;
        }
        if(keyCode == KeyEvent.VK_CONTROL){
            holdingControl = false;
        }
        if(keyCode == KeyEvent.VK_ALT) {
            holdingAlt = false;
        }
        if (keyCode == KeyEvent.VK_PERIOD) {
            holdingPoint = false;
        }
        if(keyCode == KeyEvent.VK_C){
            holdingC = false;
        }
        if(keyCode == KeyEvent.VK_V){
            holdingV = false;
        }
        if(keyCode == KeyEvent.VK_X){
            holdingX = false;
        }
    }

    public static void setHoldingKeys(int keyCode)
    {
        if (keyCode == KeyEvent.VK_SHIFT) {
            holdingShift = true;
        }
        if (keyCode == KeyEvent.VK_CONTROL) {
            holdingControl = true;
        }
        if (keyCode == KeyEvent.VK_ALT) {
            holdingAlt = true;
        }
        if (keyCode == KeyEvent.VK_PERIOD) {
            holdingPoint = true;
        }
        if (keyCode == KeyEvent.VK_C) {
            holdingC = true;
        }
        if (keyCode == KeyEvent.VK_V) {
            holdingV = true;
        }
        if (keyCode == KeyEvent.VK_X) {
            holdingX = true;
        }
    }

    public static void playerMovement(int keyCode)
    {
        if (keyCode == KeyEvent.VK_A) {
            player.setMovingLeft(true);
        }
        if (keyCode == KeyEvent.VK_D) {
            player.setMovingRight(true);
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            if(!pressedSpaceToJump) {
                player.startJump();
                pressedSpaceToJump = true;
            }
        }

        if (keyCode == KeyEvent.VK_W) {
            player.setMovingUp(true);
        }
        if (keyCode == KeyEvent.VK_S) {
            player.setMovingDown(true);
        }

        if(keyCode == KeyEvent.VK_J)
        {
            player.setGrappleLeft(true);
        }
        if(keyCode == KeyEvent.VK_L)
        {
            player.setGrappleRight(true);
        }
        if(keyCode == KeyEvent.VK_I)
        {
            player.setGrappleUp(true);
        }
        if(keyCode == KeyEvent.VK_K)
        {
            player.setGrappleDown(true);
        }

        if(keyCode == KeyEvent.VK_G)
        {
            player.swapGravity = true;
        }

        //Editor player position control
        if(editorMode)
        {

            if (keyCode == KeyEvent.VK_R) {
                player.resetPosition();
            }
            if (keyCode == KeyEvent.VK_F) {
                if (player.exists()) {
                    player.setExists(false);
                } else {
                    player.resetPosition();
                    player.setExists(true);
                }
            }
        }
    }

    public  static void escapeKey(int keyCode)
    {
        if(keyCode == KeyEvent.VK_ESCAPE)
        {
            gamePaused = !gamePaused;
        }
    }

    public static void levelFunctions(int keyCode)
    {
        if (holdingControl && keyCode == KeyEvent.VK_S) {
            LevelFunctions.saveCurrentLevel();
        }
        if (holdingControl && keyCode == KeyEvent.VK_O) {
            LevelFunctions.openCurrentLevel();
            typing = false;
        }
        if (holdingControl && keyCode == KeyEvent.VK_RIGHT) {
            LevelFunctions.saveCurrentLevel();
            LevelFunctions.importNextLevel();
            typing = false;
        }
        if (holdingControl && keyCode == KeyEvent.VK_LEFT) {
            LevelFunctions.saveCurrentLevel();
            LevelFunctions.importPreviousLevel();
            typing = false;
        }
        if (holdingControl && keyCode == KeyEvent.VK_I) {
            LevelFunctions.insertLevel();
            typing = false;
        }
        if (holdingControl && keyCode == KeyEvent.VK_R) {
            LevelFunctions.openCurrentLevel();
        }
        if (holdingControl && keyCode == KeyEvent.VK_DELETE) {
            LevelFunctions.deleteLevel();
        }
    }

    public static void levelDisplayFunctions(int keyCode)
    {
        if (holdingAlt && keyCode == KeyEvent.VK_G) {
            levelHasGridOutline = !levelHasGridOutline;
        }
        if (holdingAlt && keyCode == KeyEvent.VK_T) {
            printEditorText = !printEditorText;
        }
        if (holdingAlt && keyCode == KeyEvent.VK_E) {
            if(printEditorText || levelHasGridOutline)
            {
                printEditorText = false;
                levelHasGridOutline = false;
            }
            else
            {
                printEditorText = true;
                levelHasGridOutline = true;
            }
        }
    }

    public static void levelGridFunctions(int keyCode)
    {
        //Grid changing
        if(holdingPoint) {
            if (!holdingShift) {
                if (keyCode == KeyEvent.VK_J) {
                    levelGrid = GridFunctions.getExpandedGrid("left");
                }
                if (keyCode == KeyEvent.VK_L) {
                    levelGrid = GridFunctions.getExpandedGrid("right");
                }
                if (keyCode == KeyEvent.VK_I) {
                    levelGrid = GridFunctions.getExpandedGrid("top");
                }
                if (keyCode == KeyEvent.VK_K) {
                    levelGrid = GridFunctions.getExpandedGrid("bottom");
                }
            }
            if (holdingShift) {
                if (keyCode == KeyEvent.VK_J) {
                    levelGrid = GridFunctions.getShrunkGrid("left");
                }
                if (keyCode == KeyEvent.VK_L) {
                    levelGrid = GridFunctions.getShrunkGrid("right");
                }
                if (keyCode == KeyEvent.VK_I) {
                    levelGrid = GridFunctions.getShrunkGrid("top");
                }
                if (keyCode == KeyEvent.VK_K) {
                    levelGrid = GridFunctions.getShrunkGrid("bottom");
                }
            }
        }
    }

    public static void typingFunctions(int keyCode)
    {
        if(levelNameButton.isSelected())
        {
            currentLevelName = modifyVariable(keyCode, currentLevelName);
        }

        if(currentDirectoryButton.isSelected())
        {
            currentDirectory = modifyVariable(keyCode, currentDirectory);
        }

        if(addPropertyButton.isSelected())
        {
            propertyToAdd = modifyVariable(keyCode, propertyToAdd);
        }
    }

    public static String modifyVariable(int keyCode, String exportFileName)
    {
        if(holdingShift) {
            switch (keyCode) {

                case KeyEvent.VK_A:
                    exportFileName += "A";
                    break;
                case KeyEvent.VK_B:
                    exportFileName += "B";
                    break;
                case KeyEvent.VK_C:
                    exportFileName += "C";
                    break;
                case KeyEvent.VK_D:
                    exportFileName += "D";
                    break;
                case KeyEvent.VK_E:
                    exportFileName += "E";
                    break;
                case KeyEvent.VK_F:
                    exportFileName += "F";
                    break;
                case KeyEvent.VK_G:
                    exportFileName += "G";
                    break;
                case KeyEvent.VK_H:
                    exportFileName += "H";
                    break;
                case KeyEvent.VK_I:
                    exportFileName += "I";
                    break;
                case KeyEvent.VK_J:
                    exportFileName += "J";
                    break;
                case KeyEvent.VK_K:
                    exportFileName += "K";
                    break;
                case KeyEvent.VK_L:
                    exportFileName += "L";
                    break;
                case KeyEvent.VK_M:
                    exportFileName += "M";
                    break;
                case KeyEvent.VK_N:
                    exportFileName += "N";
                    break;
                case KeyEvent.VK_O:
                    exportFileName += "O";
                    break;
                case KeyEvent.VK_P:
                    exportFileName += "P";
                    break;
                case KeyEvent.VK_Q:
                    exportFileName += "Q";
                    break;
                case KeyEvent.VK_R:
                    exportFileName += "R";
                    break;
                case KeyEvent.VK_S:
                    exportFileName += "S";
                    break;
                case KeyEvent.VK_T:
                    exportFileName += "T";
                    break;
                case KeyEvent.VK_U:
                    exportFileName += "U";
                    break;
                case KeyEvent.VK_V:
                    exportFileName += "V";
                    break;
                case KeyEvent.VK_W:
                    exportFileName += "W";
                    break;
                case KeyEvent.VK_X:
                    exportFileName += "X";
                    break;
                case KeyEvent.VK_Y:
                    exportFileName += "Y";
                    break;
                case KeyEvent.VK_Z:
                    exportFileName += "Z";
                    break;
            }
        }
        else {
            switch (keyCode) {

                case KeyEvent.VK_A:
                    exportFileName += "a";
                    break;
                case KeyEvent.VK_B:
                    exportFileName += "b";
                    break;
                case KeyEvent.VK_C:
                    exportFileName += "c";
                    break;
                case KeyEvent.VK_D:
                    exportFileName += "d";
                    break;
                case KeyEvent.VK_E:
                    exportFileName += "e";
                    break;
                case KeyEvent.VK_F:
                    exportFileName += "f";
                    break;
                case KeyEvent.VK_G:
                    exportFileName += "g";
                    break;
                case KeyEvent.VK_H:
                    exportFileName += "h";
                    break;
                case KeyEvent.VK_I:
                    exportFileName += "i";
                    break;
                case KeyEvent.VK_J:
                    exportFileName += "j";
                    break;
                case KeyEvent.VK_K:
                    exportFileName += "k";
                    break;
                case KeyEvent.VK_L:
                    exportFileName += "l";
                    break;
                case KeyEvent.VK_M:
                    exportFileName += "m";
                    break;
                case KeyEvent.VK_N:
                    exportFileName += "n";
                    break;
                case KeyEvent.VK_O:
                    exportFileName += "o";
                    break;
                case KeyEvent.VK_P:
                    exportFileName += "p";
                    break;
                case KeyEvent.VK_Q:
                    exportFileName += "q";
                    break;
                case KeyEvent.VK_R:
                    exportFileName += "r";
                    break;
                case KeyEvent.VK_S:
                    exportFileName += "s";
                    break;
                case KeyEvent.VK_T:
                    exportFileName += "t";
                    break;
                case KeyEvent.VK_U:
                    exportFileName += "u";
                    break;
                case KeyEvent.VK_V:
                    exportFileName += "v";
                    break;
                case KeyEvent.VK_W:
                    exportFileName += "w";
                    break;
                case KeyEvent.VK_X:
                    exportFileName += "x";
                    break;
                case KeyEvent.VK_Y:
                    exportFileName += "y";
                    break;
                case KeyEvent.VK_Z:
                    exportFileName += "z";
                    break;
                case KeyEvent.VK_EQUALS:
                    exportFileName += "-";
                    break;
                case KeyEvent.VK_0:
                    exportFileName += "0";
                    break;
                case KeyEvent.VK_1:
                    exportFileName += "1";
                    break;
                case KeyEvent.VK_2:
                    exportFileName += "2";
                    break;
                case KeyEvent.VK_3:
                    exportFileName += "3";
                    break;
                case KeyEvent.VK_4:
                    exportFileName += "4";
                    break;
                case KeyEvent.VK_5:
                    exportFileName += "5";
                    break;
                case KeyEvent.VK_6:
                    exportFileName += "6";
                    break;
                case KeyEvent.VK_7:
                    exportFileName += "7";
                    break;
                case KeyEvent.VK_8:
                    exportFileName += "8";
                    break;
                case KeyEvent.VK_9:
                    exportFileName += "9";
                    break;
                case KeyEvent.VK_SPACE:
                    exportFileName += " ";
                    break;
                case KeyEvent.VK_MINUS:
                    exportFileName += "-";
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    exportFileName = removeLastCharacter(exportFileName);
                    break;
                case KeyEvent.VK_ESCAPE:
                    typing = false;
                    break;
                case KeyEvent.VK_ENTER:
                    typing = false;
                    break;
            }
        }

        return exportFileName;
    }

    public static String removeLastCharacter(String str) {
        String result = Optional.ofNullable(str)
                .filter(sStr -> sStr.length() != 0)
                .map(sStr -> sStr.substring(0, sStr.length() - 1))
                .orElse(str);
        return result;
    }



}
