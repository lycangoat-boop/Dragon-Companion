package com.lycangoat.dragoncompanion;

import com.lycangoat.dragoncompanion.entity.DragonCompanionEntity;
import com.lycangoat.dragoncompanion.item.DragonWhistleItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(DragonCompanion.MOD_ID)
public class DragonCompanion {
    public static final String MOD_ID = "dragon_companion";
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<EntityType<DragonCompanionEntity>> DRAGON =
            ENTITY_TYPES.register("dragon", () -> EntityType.Builder
                    .of(DragonCompanionEntity::new, MobCategory.CREATURE)
                    .sized(1.8F, 1.7F).clientTrackingRange(10).build("dragon"));

    public static final RegistryObject<Item> DRAGON_WHISTLE =
            ITEMS.register("dragon_whistle", () -> new DragonWhistleItem(new Item.Properties().stacksTo(1)));

    public DragonCompanion() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ENTITY_TYPES.register(bus);
        ITEMS.register(bus);
    }
}
