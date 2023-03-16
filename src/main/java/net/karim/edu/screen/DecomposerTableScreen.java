package net.karim.edu.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.karim.edu.ExampleMod;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DecomposerTableScreen extends HandledScreen<DecomposerTableScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(ExampleMod.MOD_ID, "textures/gui/element_decomposer_gui.png");

    public DecomposerTableScreen(DecomposerTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    protected void init() {
        super.init();
        backgroundHeight = 186;
        titleX = ((backgroundWidth - textRenderer.getWidth(title)) / 2);
//        titleY -= 10;
//        playerInventoryTitleY += 17;
    }
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}