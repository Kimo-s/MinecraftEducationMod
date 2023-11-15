package net.karim.edu.screen.Cotton;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BlockAnalyzerCottonScreen extends LightweightGuiDescription {

    public BlockAnalyzerCottonScreen(String text) {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(150, 150);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel label = new WLabel(Text.literal("Spectrophotometer Results"), 0x000000);
        root.add(label, 0, 0, 4, 1);

        WText textPrompt = new WText(Text.literal(text), 0x000000);
        root.add(textPrompt, 1, 3, 10, 4);

        WButton button = new WButton(Text.literal("Exit"));
        button.setOnClick(() -> {});
        root.add(button, 0, 7, 3, 1);

        root.validate(this);
    }
}
