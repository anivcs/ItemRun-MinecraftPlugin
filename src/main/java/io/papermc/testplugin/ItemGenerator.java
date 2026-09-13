package io.papermc.testplugin;



import java.util.ArrayList;

import java.util.Random;

public class ItemGenerator {

    private ArrayList<String> items;
    private String chosenItem = null;
    public ItemGenerator(ArrayList<String> items) {
        this.items = items;
    }



    public String generateNextItem() {
        Random random = new Random();
        int randomIndex = random.nextInt(items.size());
        chosenItem = items.get(randomIndex);
        return chosenItem;
    }

    public String getChosenItem() {
        return chosenItem;
    }
}
