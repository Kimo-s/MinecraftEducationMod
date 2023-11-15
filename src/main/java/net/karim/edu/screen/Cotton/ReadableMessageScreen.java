package net.karim.edu.screen.Cotton;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;

public class ReadableMessageScreen extends LightweightGuiDescription {

    public ReadableMessageScreen(String text) {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(200, 200);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel label = new WLabel(Text.literal("Question:"), 0x000000);
        root.add(label, 0, 0, 4, 1);

        WText textPrompt = new WText(Text.literal(text), 0x000000);
        root.add(textPrompt, 1, 3, 10, 4);

//        WButton button = new WButton(Text.literal("Exit"));
//        button.setOnClick(() -> {});
//        root.add(button, 0, 7, 3, 1);

        root.validate(this);
    }
}
