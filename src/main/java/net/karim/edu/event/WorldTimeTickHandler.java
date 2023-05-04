package net.karim.edu.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.profiling.jfr.event.ServerTickTimeEvent;
import net.minecraft.world.tick.Tick;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldTimeTickHandler implements ServerTickEvents.StartTick {

    int timeBetween = 100;

    List<String> messages;
    Random rand = new Random();

   public  WorldTimeTickHandler(){
       messages = new ArrayList<String>();
       messages.add("This is a help message!! Use the decomposer with elements from the world to get many elements.");
       messages.add("Another help message!");
       messages.add("Even more messages!!");
    }

    @Override
    public void onStartTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if(server.getTicks() % timeBetween == 0) {
                int ind = rand.nextInt(messages.size());
                player.sendMessage(Text.literal(messages.get(ind)));
            }

        }
    }
}
