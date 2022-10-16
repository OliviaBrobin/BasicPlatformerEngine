package game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Block {
    private HashMap<String, String> properties;

    public Block(String[] propertiesAsList) {
        properties = new HashMap<>();

        for(String property : propertiesAsList)
        {
            String[] propertyParts = property.split("-");
            String propertyName = propertyParts[0];
            String propertyValue = propertyParts[1];
            properties.put(propertyName, propertyValue);
        }
    }

    public Block(String importableString)
    {
        String[] propertiesAsArray = importableString.split(":");

        properties = new HashMap<>();

        for(String property : propertiesAsArray)
        {
            String[] propertyParts = property.split("-");
            String propertyName = propertyParts[0];
            String propertyValue = propertyParts[1];
            properties.put(propertyName, propertyValue);
        }
    }

    public boolean hasProperty(String propertyName, String propertyValue)
    {
        if(hasThisPropertyName(propertyName))
        {
            if(getThisPropertyValue(propertyName).equals(propertyValue))
            {
                return true;
            }
        }
        return false;
    }



    public boolean hasThisPropertyName(String propertyName) {
        if(properties.containsKey(propertyName))
        {
                return true;
        }
        return false;
    }

    public String getThisPropertyValue(String propertyName) {
        return properties.get(propertyName);
    }

    public void addProperty(String propertyName, String propertyValue) {
        properties.remove(propertyName);
        properties.put(propertyName, propertyValue);
    }

    public void removeProperty(String property)
    {
        String[] propertyAsArray = property.split("-");
        String propertyName = propertyAsArray[0];
        if(!propertyName.equals("blockType")) {
            properties.remove(propertyName);
        }
    }

    public String getPropertiesAsExportableString()
    {
        String exportableString = "";

        //Add properties with a colon after each property.
        for(Map.Entry<String, String> entry : properties.entrySet())
        {
            exportableString += entry.getKey() + "-" + entry.getValue();
            exportableString += ":";
        }

        //Remove end colon.
        if(exportableString.endsWith(":"))
        {
            exportableString = exportableString.substring(0, exportableString.length() - 1);
        }
        return exportableString;
    }

    public String getPropertiesAsSeparatedByLines()
    {
        String exportableString = "";

        //Add properties with a colon after each property.
        for(Map.Entry<String, String> entry : properties.entrySet())
        {
            exportableString += entry.getKey() + "-" + entry.getValue();
            exportableString += "\n";
        }

        //Remove end colon.
        if(exportableString.endsWith("\n"))
        {
            exportableString = exportableString.substring(0, exportableString.length() - 1);
        }
        return exportableString;
    }

    public String[] getPropertiesAsArray()
    {
        LinkedList<String> propertiesAsList = new LinkedList<String>();

        //Add properties with a colon after each property.
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            propertiesAsList.add(entry.getKey() + "-" + entry.getValue());
        }

        String[] propertiesAsArray = new String[propertiesAsList.size()];

        for(int x = 0 ; x < propertiesAsArray.length ; x++)
        {
            propertiesAsArray[x] = propertiesAsList.get(x);
        }

        return propertiesAsArray;
    }


    public BlockVisual getBlockVisual()
    {
        BlockVisual blockVisual = new BlockVisual(this);
        return  blockVisual;
    }

    //TODO Add is hard function that checks if blockType is solid, sticky, bouncy, etc.

    public void switchBetweenSolidAndVoid()
    {
        if(!hasProperty("blockType","solid"))
            {
                addProperty("blockType", "solid");
            }
            else if(hasProperty("blockType","solid"))
            {
                addProperty("blockType", "void");
            }
    }





    //TODO We need to make adding properties easy with the enum solution.
    /*
    //This doesn't seem like a necessary function.
    public void addProperties (Block otherBlock)
    {
        //This function doesn't work.

        //Void, solid, death, finish, player, teleport block, text, image
        LinkedList<String> typesAsList = new LinkedList<>();
        typesAsList.addAll(java.util.List.of(types));

        String[] otherBlockTypes = otherBlock.getTypes();

        for(String otherBlockType : otherBlockTypes)
        {
            if(!typesAsList.contains(otherBlockType)) {
                if (otherBlockType.equals("solid")) {
                    typesAsList.remove("void");
                    typesAsList.add("solid");
                }
                if (otherBlockType.equals("void") || otherBlockType.equals("small finish") || otherBlockType.equals("big finish") || otherBlockType.equals("final finish") || otherBlockType.equals("death") || otherBlockType.equals("player")) {
                    typesAsList.remove("solid");
                    typesAsList.add(otherBlockType);
                }
            }
        }

        if(otherBlock.hasTeleportBlock())
        {
            if(!typesAsList.contains(otherBlock.getTeleportBlock())) {
                typesAsList.remove("solid");
                typesAsList.add(otherBlock.getTeleportBlock());
            }
        }
        if(otherBlock.hasTypeImage())
        {
            if(!typesAsList.contains("image" + "-" + otherBlock.getImage())) {
                typesAsList.remove("solid");
                typesAsList.add("image" + "-" + otherBlock.getImage());
            }
        }
        if(otherBlock.hasTypeText())
        {
            if(!typesAsList.contains("text" + "-" + otherBlock.getText())) {
                typesAsList.add("text" + "-" + otherBlock.getText());
            }
        }

        types = typesAsList.toArray(new String[typesAsList.size()]);
    }
     */
}
