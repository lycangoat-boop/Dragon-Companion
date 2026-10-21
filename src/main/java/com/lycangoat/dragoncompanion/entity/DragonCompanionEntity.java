package com.lycangoat.dragoncompanion.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DragonCompanionEntity extends TamableAnimal {
    private static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(DragonCompanionEntity.class, EntityDataSerializers.BOOLEAN);

    public DragonCompanionEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
        this.setTame(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.FLYING_SPEED, 0.45D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.15D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isTame() && this.getOwner() instanceof ServerPlayer owner
                && this.distanceToSqr(owner) > 32.0D && !this.isPassenger()) {
            // The whistle handles long-distance recall; normal movement stays with vanilla pathfinding.
        }
    }

    @Override
    public boolean isFood(net.minecraft.world.item.ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.level().isClientSide) return InteractionResult.SUCCESS;
        if (this.isOwnedBy(player)) {
            if (player.isShiftKeyDown()) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.navigation.stop();
                return InteractionResult.CONSUME;
            }
            if (!this.isOrderedToSit() && !this.isVehicle()) {
                player.startRiding(this);
                return InteractionResult.CONSUME;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isTame() && source.getEntity() == this.getOwner()) return false;
        return super.hurt(source, amount);
    }

    @Override
    public boolean canAttack(Entity target) {
        return this.isTame() && target != this.getOwner() && super.canAttack(target);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(net.minecraft.world.entity.LivingEntity passenger) {
        return this.position().add(1.0D, 0.2D, 0.0D);
    }
}
