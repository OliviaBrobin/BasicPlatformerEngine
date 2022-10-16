package game;

import java.awt.*;

public class Button {
    private int x;
    private int y;

    private int width;
    private int height;

    private boolean wasClicked;
    private boolean wasHovered;

    private boolean hasRectangle;
    private boolean isText;

    private boolean isSelected;

    public Button()
    {
        this.x = -100;
        this.y = -100;
        this.width = 0;
        this.height = 0;

        this.hasRectangle = false;
        this.isText = false;
    }

    public Button(int x, int y, int width, int height, boolean hasRectangle, boolean isText)
    {
        //TODO There should be an empty constructor that defaults the x and y values to -100 and -100 and the width and height to 0 and 0. The boolean should be set to false.
        //TODO The basic button should use this empty constructor and set hasRectangle to true in the EditorStatsClass.
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.hasRectangle = hasRectangle;
        this.isText = isText;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean wasClicked() {
        return wasClicked;
    }

    public void setWasClicked(boolean wasClicked) {
        this.wasClicked = wasClicked;
    }

    public boolean wasHovered() {
        return wasHovered;
    }

    public void setWasHovered(boolean wasHovered) {
        this.wasHovered = wasHovered;
    }

    public boolean hasRectangle() {
        return hasRectangle;
    }

    public void setHasRectangle(boolean hasRectangle) {
        this.hasRectangle = hasRectangle;
    }

    public boolean isText() {
        return isText;
    }

    public void setText(boolean text) {
        isText = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public  void display(Graphics g)
    {
        if(hasRectangle) {
            g.setColor(MyColor.getColor("editorButtonWhite"));

            if (wasHovered) {
                g.setColor(MyColor.getColor("editorButtonGray"));
            }

            if (isSelected) {
                g.setColor(MyColor.getColor("editorButtonColored"));
            }

            g.fillRect(x, y, width, height);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }
    }
}
