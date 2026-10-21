package com.lycangoat.dragoncompanion.client;

import com.lycangoat.dragoncompanion.DragonCompanion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonCompanion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class DragonCompanionClient {
    private DragonCompanionClient() {}

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DragonModel.LAYER, DragonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DragonCompanion.DRAGON.get(), context ->
                new MobRenderer<>(context, new DragonModel(context.bakeLayer(DragonModel.LAYER)), 0.8F) {
                    @Override
                    public ResourceLocation getTextureLocation(com.lycangoat.dragoncompanion.entity.DragonCompanionEntity entity) {
                        return new ResourceLocation(DragonCompanion.MOD_ID, "textures/entity/dragon.png");
                    }
                });
    }
}
