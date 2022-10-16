package game;

import java.io.*;
import java.util.LinkedList;

import static game.Display.*;

public class LevelFunctions {

    public static void clearCurrentLevel()
    {
        levelGrid = new Block[50][26];

        for(int x = 0; x < levelGrid.length ; x++)
        {
            for(int y = 0; y < levelGrid[x].length ; y++) {
                levelGrid[x][y] = new Block(new String[] {"blockType-void"});
            }
        }
        exportLevel(currentLevelName);
    }

    public static void saveCurrentLevel()
    {
        exportLevel(currentLevelName);
    }

    public static void importNextLevel()
    {
        levelNumber++;
        changeLevel(levelNumber + "");
    }

    public static void importPreviousLevel()
    {
        levelNumber--;
        changeLevel(levelNumber + "");
    }

    public static void openCurrentLevel()
    {
        changeLevel(currentLevelName);
    }

    private static void changeLevel(String level)
    {
        importLevel(level);

        player.resetPosition();

        if(GridFunctions.gridHasProperty("playerSpawn", "true"))
        {
            player.setExists(true);
        }
        else
        {
            player.setExists(false);
        }

        TypingFunctions.typing = false;
    }

    private static void exportLevel(String exportFileName)
    {
        try {
            FileWriter myWriter = new FileWriter(currentDirectory + "/" + exportFileName + ".txt");
            for(int x = 0; x < levelGrid.length ; x++)
            {
                for(int y = 0; y < levelGrid[x].length ; y++)
                {
                    myWriter.write(levelGrid[x][y].getPropertiesAsExportableString() + ",");
                }
                myWriter.write("\n");
            }
            myWriter.write("");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void importLevel(String levelName)
    {
        Display.currentLevelName = levelName;
        File file = new File(currentDirectory + "/" + levelName + ".txt");

        if(file.exists()){
            LinkedList<String[]> gridAsArraysInLists = new LinkedList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(currentDirectory + "/" + levelName + ".txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] partsOfLine = line.split(",");
                    gridAsArraysInLists.add(partsOfLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Block[][] gridAsNestedArrays = new Block[gridAsArraysInLists.size()][gridAsArraysInLists.get(0).length];

            for (int x = 0; x < gridAsNestedArrays.length; x++) {
                for (int y = 0; y < gridAsNestedArrays[x].length; y++) {
                    gridAsNestedArrays[x][y] = new Block(gridAsArraysInLists.get(x)[y]);
                }
            }

            levelGrid = gridAsNestedArrays;
        }
        else{
            clearCurrentLevel();
        }
    }

    public static void insertLevel()
    {
        int originalLevelNum = levelNumber;
        int maxLevelNum = getMaxLevelNumber();

        if(maxLevelNum == originalLevelNum)
        {
            maxLevelNum++;
        }

        for(int x = maxLevelNum ; x > originalLevelNum ; x--)
        {
            importLevel(x + "");
            currentLevelName = (x+1) + "";
            exportLevel(currentLevelName);
        }

        importNextLevel();
        clearCurrentLevel();
    }

    public static void deleteLevel()
    {
        int originalLevelNum = levelNumber;

        int maxLevelNum = getMaxLevelNumber();

        if(maxLevelNum == originalLevelNum)
        {
            maxLevelNum++;
        }

        for(int x = originalLevelNum ; x < maxLevelNum ; x++)
        {
            importLevel(x + 1 + "");
            Display.currentLevelName = (x) + "";
            exportLevel(currentLevelName);
        }

        changeLevel(levelNumber + "");

        //TODO delete function needs to remove the two unnecessary files at the end of the directory.
    }

    //TODO This function doesn't work correctly.
    private static int getMaxLevelNumber()
    {
        int maxLevelNum = levelNumber;
        for(int x = maxLevelNum ; x < 1000 ; x++)
        {
            File file = new File(currentDirectory + "/" + maxLevelNum + ".txt");
            if(file.exists())
            {
                x++;
                maxLevelNum = x;
            }
            else
            {
                break;
            }
        }

        return maxLevelNum;
    }
}
