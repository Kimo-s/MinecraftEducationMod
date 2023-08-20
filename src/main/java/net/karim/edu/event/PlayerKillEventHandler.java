package net.karim.edu.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.karim.edu.EduChemMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

public class PlayerKillEventHandler implements ServerEntityCombatEvents.AfterKilledOtherEntity{
    @Override
    public void afterKilledOtherEntity(ServerWorld world, Entity entity, LivingEntity killedEntity) {
        if(entity.isPlayer()){
            if(killedEntity.getRecentDamageSource() != null){
                EduChemMod.LOGGER.info(entity.getDisplayName().getString() + " killed " + killedEntity.getDisplayName().getString() + ". (source: " + killedEntity.getRecentDamageSource().getName() + ")");
            } else {
                EduChemMod.LOGGER.info(entity.getDisplayName().getString() + " killed " + killedEntity.getDisplayName().getString());
            }
        }
    }
}
