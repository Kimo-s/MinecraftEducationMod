package net.karim.edu.screen.Cotton;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class ReadableMessageScreen extends LightweightGuiDescription{

    ArrayList<String> hints;
    WGridPanel root;

    int cury = 0;
    int curHint = 0;

    public ReadableMessageScreen(String text, ArrayList<String> hints) {
        root = new WGridPanel();
        setRootPanel(root);
        root.setSize(200, 300);
        root.setInsets(Insets.ROOT_PANEL);

        this.hints = hints;

        WLabel label = new WLabel(Text.literal("Question:"), 0x000000);
        root.add(label, 0, 0, 4, 1);

        WText textPrompt = new WText(Text.literal(text), 0x000000);
        root.add(textPrompt, 1, 1, 10, 4);

        cury = 4+4;

        WButton button = new WButton(Text.literal("Hint"));
        button.setOnClick(this::onHintClick);
        root.add(button, 0, 15, 3, 1);

        root.validate(this);
    }


    void onHintClick(){
        if(curHint < hints.size()){
            root.add(new WText(Text.literal(hints.get(curHint)), 0x000000),1 ,cury ,10 ,1);
            cury += 1;
            curHint += 1;
            root.validate(this);
        }
    }

}
