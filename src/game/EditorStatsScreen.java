package game;

import java.awt.*;

import static game.Display.*;

public class EditorStatsScreen {

    public int x;
    public int y;
    public int topTextBuffer;
    public Graphics g;

    public final int VERTICAL_DISTANCE = 40;
    public  final  int FONT_SIZE = 28;

    public EditorStatsScreen(int x, int y, Graphics g)
    {
        this.x = x;
        this.y = y;
        this.g = g;
    }

    public void display()
    {
        topTextBuffer = this.y - 20;

        g.setColor(MyColor.getColor("editorStatsText"));
        g.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE));


        displayLevelFileStats();
        displayGridStats();

        displaySelectedBlockProperties();
    }

    public int getBottom()
    {
        return topTextBuffer + VERTICAL_DISTANCE;
    }

    public void displayLevelFileStats()
    {
        //Display info
        updateLevelNameButton();
        displayLevelNameButton();

        g.drawString("Export File Name: " + currentLevelName, this.x, topTextBuffer += VERTICAL_DISTANCE);

        updateCurrentDirectoryButton();
        displayCurrentDirectoryButton();

        g.drawString("Current Directory: " + currentDirectory, this.x, topTextBuffer += VERTICAL_DISTANCE);
    }

    public void displayGridStats()
    {
        double levelGridWidth = 0;
        double levelGridHeight = 0;
        double levelGridDimensionsRatio = 0;

        try{
            levelGridWidth = levelGrid.length;
            levelGridHeight = levelGrid[0].length;
            levelGridDimensionsRatio = levelGridHeight / levelGridWidth;
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {

        }

        g.drawString("Scaling Factor: " + levelScreen.getScalingFactor(), this.x, topTextBuffer += VERTICAL_DISTANCE);
        g.drawString("Column Width: " + levelGridWidth, this.x, topTextBuffer += VERTICAL_DISTANCE);
        g.drawString("Column Height: " + levelGridHeight, this.x, topTextBuffer += VERTICAL_DISTANCE);
        g.drawString("Column Ratio: " + levelGridDimensionsRatio, this.x, topTextBuffer += VERTICAL_DISTANCE);
        g.drawString("Offset Acceleration: " + levelScreen.offsetAcceleration, this.x, topTextBuffer += VERTICAL_DISTANCE);
    }

    public  void updateLevelNameButton()
    {
        if(levelNameButton.wasClicked())
        {
            LevelFunctions.saveCurrentLevel();

            TypingFunctions.typing = true;
            currentLevelName = "";

            levelNameButton.setSelected(true);
            levelNameButton.setWasClicked(false);
        }

        if(!TypingFunctions.typing)
        {
            if(levelNameButton.isSelected()) {
                LevelFunctions.openCurrentLevel();
                levelNameButton.setSelected(false);
            }
        }
    }

    public void displayLevelNameButton()
    {
        levelNameButton.setX(this.x + 246);
        levelNameButton.setY(topTextBuffer + 15);
        levelNameButton.setHeight(VERTICAL_DISTANCE - 10);
        levelNameButton.setWidth(200);
        levelNameButton.setHasRectangle(true);

        levelNameButton.display(g);
    }

    public void updateCurrentDirectoryButton()
    {
        if(currentDirectoryButton.wasClicked())
        {
            TypingFunctions.typing = true;
            currentDirectory = "";

            currentDirectoryButton.setSelected(true);
            currentDirectoryButton.setWasClicked(false);
        }

        if(!TypingFunctions.typing)
        {
            currentDirectoryButton.setSelected(false);
        }
    }

    public void displayCurrentDirectoryButton()
    {
        currentDirectoryButton.setX(this.x + 246);
        currentDirectoryButton.setY(topTextBuffer + 15);
        currentDirectoryButton.setHeight(VERTICAL_DISTANCE - 10);
        currentDirectoryButton.setWidth(200);
        currentDirectoryButton.setHasRectangle(true);

        currentDirectoryButton.display(g);
    }

    public void displaySelectedBlockProperties()
    {
        g.drawString("Selected Block: ", this.x, topTextBuffer += VERTICAL_DISTANCE);

        String[] properties = selectedBlock.getPropertiesAsArray();
        displayProperties(properties);

        updateRemovePropertyButtons(properties);
        displayRemovePropertyButtons(properties);


        displayAddPropertyButton();
        g.drawString("Add Property: " + propertyToAdd, this.x, topTextBuffer += VERTICAL_DISTANCE);
    }

    public void displayProperties(String[] properties)
    {
        int number = 0;
        for(String property : properties)
        {
            g.drawString(property, this.x + 40, topTextBuffer += VERTICAL_DISTANCE);
            number++;
        }

        topTextBuffer -= number * VERTICAL_DISTANCE;
    }

    public void updateRemovePropertyButtons(String[] properties) {
        if(removePropertyButtons.size() < properties.length)
        {
            Button button = new Button();

            removePropertyButtons.add(button);
        }

        if(removePropertyButtons.size() > properties.length)
        {
            removePropertyButtons.removeLast();
        }

        for (int x = 0 ; x < removePropertyButtons.size() ; x++) {
            Button removePropertyButton = removePropertyButtons.get(x);

            if(removePropertyButton.wasClicked())
            {
                String propertyToRemove = properties[x];
                selectedBlock.removeProperty(propertyToRemove);

                removePropertyButton.setWasClicked(false);
            }
        }
    }

    public void displayRemovePropertyButtons(String[] properties)
    {
        int number = 0;
        for(Button removePropertyButton : removePropertyButtons)
        {
            removePropertyButton.setX(this.x);
            removePropertyButton.setY((topTextBuffer += VERTICAL_DISTANCE) - 25);
            removePropertyButton.setHeight(VERTICAL_DISTANCE - 10);
            removePropertyButton.setWidth(30);
            removePropertyButton.setHeight(30);
            removePropertyButton.setHasRectangle(true);

            removePropertyButton.display(g);
            number++;
        }

        topTextBuffer -= number * VERTICAL_DISTANCE;
    }

    public void displayAddPropertyButton()
    {
        topTextBuffer += 4 * VERTICAL_DISTANCE;

        addPropertyButton.setX(this.x + 190);
        addPropertyButton.setY(topTextBuffer + 15);
        addPropertyButton.setHeight(VERTICAL_DISTANCE - 10);
        addPropertyButton.setWidth(200 + 246 - 190);
        addPropertyButton.setHasRectangle(true);

        if(addPropertyButton.wasClicked())
        {
            TypingFunctions.typing = true;
            propertyToAdd = "";


            addPropertyButton.setSelected(true);
            addPropertyButton.setWasClicked(false);
        }

        if(!TypingFunctions.typing && addPropertyButton.isSelected())
        {
            String[] propertyToAddAsArray = propertyToAdd.split("-");
            if(propertyToAddAsArray.length > 1) {
                selectedBlock.addProperty(propertyToAddAsArray[0], propertyToAddAsArray[1]);
            }

            propertyToAdd = "";
            addPropertyButton.setSelected(false);
        }

        addPropertyButton.display(g);
    }
}
