package com.lycangoat.dragoncompanion;

import com.lycangoat.dragoncompanion.entity.DragonCompanionEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonCompanion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DragonEvents {
    private DragonEvents() {}

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(DragonCompanion.DRAGON.get(), DragonCompanionEntity.createAttributes().build());
    }
}
