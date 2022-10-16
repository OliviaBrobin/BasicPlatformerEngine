package game;

import java.awt.Color;
import java.util.Map;

import static java.util.Map.entry;

public class MyColor {
    private Color color;

    static Map<String, String> colors = Map.ofEntries(
            /*
            entry("veryLightDeathRed", "#99435f"),
            entry("lightDeathRed", "#5c2839"),
            entry("deathRed", "#5c2839"),
            entry("pink", "#e5adad"),
            entry("yellow", "#bf974c"),
            entry("green", "#5f9943"),
            entry("blue", "#435f99"),
            entry("veryLightViolet", "dad3d8"),
            entry("violet", "#694cbf"),
            entry("white", "#ffffff"),
            entry("lightGray", "#ffffff"), //White
            entry("darkGray", "#4c4c4c"),
            entry("black", "#000000"), //Almost black, black is #000000
            entry("offWhite", "#ffffff"), //extremely light violet
            entry("offBlack", "#130f08"), //Dark yellow
            */
            entry("offWhite", "#ffffff"), //extremely light violet
            entry("offBlack", "#130f08"), //Dark yellow

            entry("white", "#ffffff"),
            entry("blue", "#439ac6"),
            entry("darkBlue", "#285c77"), // + 40% darkness
            entry("lightBlue", "#8ec2dd"),
            entry("black", "#000000"),
            entry("red", "#eb483b"),
            entry("darkRed", "#8d2b23"),
            entry("lightRed", "#f39189"),
            entry("green","#86eb3b"),
            entry("purple", "#8943c6"),
            entry("darkPurple", "#522877"),
            entry("lightPurple", "#b88edd"),
            entry("darkGray","#6a6a6a"),
            entry("lightGray","#f0f0f0"),
            //entry("orange", "#f3c115"),
            entry("orange", "#f39515"),
            entry("lightOrange", "#f8bf73"),
            entry("yellow", "#ffe800")
            );


    static Color getColor(String col) {
        Color color = Color.GREEN;
        switch (col) {
            case "editorBackground":
                color = Color.decode(colors.get("lightGray"));
                break;
            case "editorStatsText":
                color = Color.decode(colors.get("black"));
                break;
            case "editorTextBlack":
                color = Color.decode(colors.get("black"));
                break;
            case "editorTextViolet":
                color = Color.decode(colors.get("darkPurple"));
                break;
            case "editorTextWhite":
                color = Color.decode(colors.get("offWhite"));
                break;
            case "editorButtonWhite":
                color = Color.decode(colors.get("offWhite"));
                break;
            case "editorButtonGray":
                color = Color.LIGHT_GRAY;
                break;
            case "editorButtonColored":
                color = Color.PINK;
                break;
            case "basicScreen":
                color = Color.decode(colors.get("white"));
                break;
            case "gameBackground":
                color = Color.decode(colors.get("darkGray"));
                break;
            case "pauseTint":
                //TODO We'll need to modify this at a later time.
                //color = Color.decode(colors.get("black"));
                color = new Color(0,0,0,60);
                break;
            case "levelBackground":
                color = Color.decode(colors.get("offWhite"));
                break;
            case "levelTextBlack":
                color = Color.decode(colors.get("black"));
                break;
            case "levelTextWhite":
                color = Color.decode(colors.get("offWhite"));
                break;
            case "gridOutline":
                color = Color.decode(colors.get("black"));
                break;
            case "player":
                color = Color.decode(colors.get("black"));
                break;
            case "void":
                color = Color.decode(colors.get("offWhite"));
                break;
            case "solid":
                color = Color.decode(colors.get("offBlack"));
                break;
            case "bouncy":
                color = Color.decode(colors.get("blue"));
                break;
            case "sticky":
                color = Color.decode(colors.get("orange"));
                break;
            case "playerSticky":
                color = Color.decode(colors.get("lightOrange"));
                break;
            case "grapple":
                color = Color.decode(colors.get("lightPurple"));
                break;
            case "playerDoubleJump":
                color = Color.decode(colors.get("lightBlue"));
                break;
            case "playerControlGravity":
                color = Color.decode(colors.get("green"));
                break;
            case "death":
                color = Color.decode(colors.get("darkGray"));
                break;
            case "finish":
                color = Color.decode(colors.get("yellow"));
                break;
            case "red":
                color = Color.decode((colors.get("red")));
                break;
        }
        return color;
    }
}