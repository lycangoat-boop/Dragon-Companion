package com.lycangoat.dragoncompanion.item;

import com.lycangoat.dragoncompanion.DragonCompanion;
import com.lycangoat.dragoncompanion.entity.DragonCompanionEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DragonWhistleItem extends Item {
    public DragonWhistleItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, net.minecraft.world.InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel) {
            DragonCompanionEntity found = serverLevel.getEntitiesOfClass(
                    DragonCompanionEntity.class, player.getBoundingBox().inflate(64),
                    dragon -> dragon.isOwnedBy(player)).stream().findFirst().orElse(null);
            if (found != null) {
                if (found.distanceToSqr(player) > 16 * 16) {
                    found.teleportTo(player.getX() + 1.5, player.getY(), player.getZ() + 1.5);
                }
                found.setOrderedToSit(false);
                found.getNavigation().stop();
            } else {
                DragonCompanionEntity dragon = DragonCompanion.DRAGON.get().create(serverLevel);
                if (dragon != null) {
                    dragon.moveTo(player.getX() + 1.5, player.getY(), player.getZ() + 1.5, player.getYRot(), 0);
                    dragon.tame(player);
                    serverLevel.addFreshEntity(dragon);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
