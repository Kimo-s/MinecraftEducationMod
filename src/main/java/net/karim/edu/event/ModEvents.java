package net.karim.edu.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ModEvents {

    public static void registerModEvents(){
        ServerTickEvents.START_SERVER_TICK.register(new WorldTimeTickHandler());
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(new PlayerKillEventHandler());
    }
}
