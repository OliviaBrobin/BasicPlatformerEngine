package game;

import java.io.File;
import java.io.FilenameFilter;

public class ImageFunctions {
    public static void loadImageBlocksArrays()
    {
        //Get image names from folder
        File directoryPath = new File("backgroundImages");

        //Get .png files only
        File[] files=directoryPath.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });

        Display.backgroundImageBlocks = new Block[files.length + 1];

        Display.backgroundImageBlocks[0] = new Block(new String[] {"blockType-void", "editorText-B"});

        for (int x = 0 ; x < files.length ; x++) {
            Display.backgroundImageBlocks[x + 1] = new Block(new String[] {"blockType-void", "backgroundImage-" + files[x].getName()});
        }
    }
}
