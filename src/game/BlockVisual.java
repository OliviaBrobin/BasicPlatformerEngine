package game;

public class BlockVisual {

    private String color = "void";
    private String editorTextColor = "editorTextBlack";
    private String inGameTextColor = "levelTextBlack";
    private String editorText = "";
    private String inGameText = "";
    private int inGameTextX = 0;
    private int inGameTextY = 0;
    private String backgroundImage = "";

    public BlockVisual(Block block)
    {
        if(block.hasProperty("blockType","solid")) {
                color = "solid";
                editorTextColor = "editorTextWhite";
        }
        if (block.hasProperty("isDeath","true")) {
            color = "death";
            editorTextColor = "editorTextBlack";
        }
        if (block.hasProperty("isBouncy","true")) {
            color = "bouncy";
        }
        if (block.hasProperty("isSticky","true")) {
            color = "sticky";
        }
        if (block.hasProperty("isGrapple","true")) {
            color = "grapple";
        }

        if (block.hasProperty("playerSticky","true")) {
            color = "playerSticky";
        }
        if (block.hasProperty("playerDoubleJump","true")) {
            color = "playerDoubleJump";
        }
        if (block.hasProperty("playerControlGravity","true")) {
            color = "playerControlGravity";
        }

        if(block.hasProperty("isFinish","true")) {
                color = "finish";
                editorText += "f";
        }

        if(block.hasProperty("playerSpawn","true")) {
                editorText += "P";
        }

        if(block.hasThisPropertyName("color")) {
            color = block.getThisPropertyValue("color");
        }

        if(block.hasThisPropertyName("gameText")) {
            inGameText = block.getThisPropertyValue("gameText");
        }

        if(block.hasThisPropertyName("gameTextX")) {
            try {
                inGameTextX = Integer.parseInt(block.getThisPropertyValue("gameTextX"));
            }
            catch (NumberFormatException e)
            {
                inGameTextX = 0;
            }
        }

        if(block.hasThisPropertyName("gameTextY")) {
            try {
                inGameTextY = Integer.parseInt(block.getThisPropertyValue("gameTextY"));
            }
            catch (NumberFormatException e)
            {
                inGameTextY = 0;
            }
        }

        if(block.hasThisPropertyName("editorText")) {
            editorText += block.getThisPropertyValue("editorText");
        }

        if(block.hasThisPropertyName("backgroundImage"))
        {
            backgroundImage = block.getThisPropertyValue("backgroundImage");
        }
    }

    public String getColor() {
        return color;
    }

    public String getEditorTextColor() {
        return editorTextColor;
    }

    public String getInGameTextColor() {
        return inGameTextColor;
    }

    public String getEditorText() {
        return editorText;
    }

    public String getInGameText() {
        return inGameText;
    }

    public int getInGameTextX() { return inGameTextX; }

    public int getInGameTextY() { return inGameTextY; }

    public String getBackgroundImage() {
        return backgroundImage;
    }
}
