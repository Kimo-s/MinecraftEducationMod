package net.karim.edu.Item;

import net.karim.edu.screen.Cotton.BlockAnalyzerCotton;
import net.karim.edu.screen.Cotton.ReadableMessageScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ReadableMessage extends Item {

    private final String messageText;

    public ReadableMessage(Settings settings) {
        super(settings);
        messageText = "Empty message";
    }

    public ReadableMessage(Settings settings, String messageText) {
        super(settings);
        this.messageText = messageText;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        MinecraftClient.getInstance().setScreen(new BlockAnalyzerCotton(new ReadableMessageScreen(messageText)));
        return super.use(world, user, hand);
    }
}
