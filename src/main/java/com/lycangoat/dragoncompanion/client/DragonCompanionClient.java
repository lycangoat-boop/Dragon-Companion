package com.lycangoat.dragoncompanion.client;

import com.lycangoat.dragoncompanion.DragonCompanion;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonCompanion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class DragonCompanionClient {
    private DragonCompanionClient() {}

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DragonCompanion.DRAGON.get(), DragonCompanionClient::createPlaceholderRenderer);
    }

    private static MobRenderer createPlaceholderRenderer(EntityRendererProvider.Context context) {
        return new MobRenderer(context, new PigModel(context.bakeLayer(ModelLayers.PIG)), 0.7F) {
            @Override
            public ResourceLocation getTextureLocation(net.minecraft.world.entity.Entity entity) {
                return new ResourceLocation("minecraft", "textures/entity/pig/pig.png");
            }
        };
    }
}
