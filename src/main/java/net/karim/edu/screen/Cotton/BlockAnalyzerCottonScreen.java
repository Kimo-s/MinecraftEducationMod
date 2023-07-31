package net.karim.edu.screen.Cotton;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BlockAnalyzerCottonScreen extends LightweightGuiDescription {

    public BlockAnalyzerCottonScreen(String text) {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(256, 240);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel label = new WLabel(Text.literal(text), 0x000000);
        root.add(label, 1, 4, 2, 1);

        root.validate(this);
    }
}
