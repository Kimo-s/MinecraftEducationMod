package net.karim.edu.screen.Dialogue;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;


public class DialogueMainScreen extends LightweightGuiDescription {



    HashMap<String, String> leafScreen;

    public DialogueMainScreen() {

        leafScreen = new HashMap<String, String>();
        leafScreen.put("What should I do?", "This is minecraft where you can go mine, fish, trade, explore and build! You also can use the decomposer and chemistry table block to create compounds and reactions.");
        leafScreen.put("Chemistry table?", "The chemistry table allows you to combine elements into chemical compounds. For example you can combine H + O2 to get H2O!");
        leafScreen.put("How can I get elements?", "You can get elements by using the decomposer, killing monsters around you, farming, and trading with villagers. \nDon't forgot there is a village nearby that you can go to.");
        leafScreen.put("Who are you?", "I am a hedgehog! I will help you survive and advance in this game.");
        leafScreen.put("Quests", "There are doors in the basement of this building. Those Doors need keys in order to open. You need to use the Chemistry table to combine the items needed to find make the keys.");


        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        int width = 320;
        root.setSize(width, 240);
        root.setInsets(Insets.ROOT_PANEL);


        WLabel label = new WLabel(Text.literal("Hi, welcome to my house. How can I help you?"), 0xFFFFFF);
        root.add(label, 0, 0, 4, 1);



        ArrayList<String> keyArray = new ArrayList<String>(leafScreen.keySet());

        String[] buttonPrompts = new String[] {"What should I do?", "Where am I?", "Resources", "Chemistry table", "test button"};

        for(int i = 0; i < keyArray.size(); i++) {
            int yoffset = (i+2)/2;
            WButton button = new WButton(Text.literal(keyArray.get(i)));
            button.setOnClick(new DialogueLeafScreen(this, Text.literal(leafScreen.get(keyArray.get(i)))));
            if(i % 2 == 0) {
                root.add(button, 1, 6+2*yoffset, 7, 1);
            } else {
                root.add(button, 1 + 8, 6+2*yoffset, 7, 1);
            }
        }

        root.validate(this);


    }
}

