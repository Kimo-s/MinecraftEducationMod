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

import java.util.ArrayList;

public class ReadableMessage extends Item {

    private final String messageText;
    private final ArrayList<String> hints;

    public ReadableMessage(Settings settings, String messageText) {
        super(settings);
        this.hints = new ArrayList<>();
        this.messageText = messageText;
    }

    public ReadableMessage(Settings settings, String messageText, ArrayList<String> hints) {
        super(settings);
        this.messageText = messageText;
        this.hints = hints;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) {
            MinecraftClient.getInstance().setScreen(new BlockAnalyzerCotton(new ReadableMessageScreen(messageText, hints)));
        }
        return super.use(world, user, hand);
    }
}
