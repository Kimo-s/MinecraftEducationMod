package net.karim.edu.screen.Dialogue;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.karim.edu.EduChemMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class DialogueLeafScreen extends LightweightGuiDescription implements Runnable {
    String textString;

    public DialogueLeafScreen(LightweightGuiDescription prevScreen, Text text) {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(200, 100);
        root.setInsets(Insets.ROOT_PANEL);

        textString = text.getString();

        WText label = new WText(text, 0x000000);
        root.add(label, 0, 0, 9, 1);

        WButton button = new WButton(Text.literal("Back"));
        button.setOnClick(() -> {MinecraftClient.getInstance().setScreen(new CottonClientScreen(prevScreen));});
        root.add(button, 0, 3, 3, 1);


        root.validate(this);
    }

    @Override
    public void run() {
        EduChemMod.LOGGER.info("Player talked with groundhog and presented with this dialogue: " + textString);
        MinecraftClient.getInstance().setScreen(new DialogueScreen(this));
    }
}
