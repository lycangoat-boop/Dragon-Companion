package com.lycangoat.dragoncompanion.client;

import com.lycangoat.dragoncompanion.entity.DragonCompanionEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DragonModel extends EntityModel<DragonCompanionEntity> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            new ResourceLocation("dragon_companion", "dragon"), "main");
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public DragonModel(ModelPart root) {
        body = root.getChild("body");
        head = root.getChild("head");
        leftWing = root.getChild("left_wing");
        rightWing = root.getChild("right_wing");
        tail = root.getChild("tail");
        leftLeg = root.getChild("left_leg");
        rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-5F, -4F, -7F, 10F, 8F, 14F), PartPose.offset(0F, 15F, 0F));
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 24)
                .addBox(-3F, -3F, -5F, 6F, 6F, 7F)
                .texOffs(26, 24).addBox(-1F, 0F, -8F, 2F, 2F, 4F),
                PartPose.offset(0F, 12F, -7F));
        root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(32, 0)
                .addBox(0F, 0F, -1F, 10F, 1F, 7F), PartPose.offset(4F, 12F, 0F));
        root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(32, 0)
                .mirror().addBox(-10F, 0F, -1F, 10F, 1F, 7F),
                PartPose.offset(-4F, 12F, 0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 38)
                .addBox(-1.5F, -1.5F, 0F, 3F, 3F, 8F), PartPose.offset(0F, 16F, 7F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(22, 38)
                .addBox(-1.5F, 0F, -1.5F, 3F, 5F, 3F), PartPose.offset(3F, 19F, 3F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(22, 38)
                .mirror().addBox(-1.5F, 0F, -1.5F, 3F, 5F, 3F), PartPose.offset(-3F, 19F, 3F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(DragonCompanionEntity entity, float limbSwing, float limbSwingAmount,
                         float ageInTicks, float netHeadYaw, float headPitch) {
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        head.xRot = headPitch * Mth.DEG_TO_RAD;
        float flap = Mth.cos(ageInTicks * 0.65F) * 0.45F;
        leftWing.zRot = flap;
        rightWing.zRot = -flap;
        tail.yRot = Mth.cos(ageInTicks * 0.12F) * 0.15F;
        leftLeg.xRot = Mth.cos(limbSwing * 0.7F) * limbSwingAmount;
        rightLeg.xRot = Mth.cos(limbSwing * 0.7F + Mth.PI) * limbSwingAmount;
    }
}
