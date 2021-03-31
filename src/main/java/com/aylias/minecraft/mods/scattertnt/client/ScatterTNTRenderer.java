package com.aylias.minecraft.mods.scattertnt.client;

import com.aylias.minecraft.mods.scattertnt.entities.UniversalScatterTNTEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TNTMinecartRenderer;
import net.minecraft.client.renderer.entity.TNTRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ScatterTNTRenderer extends EntityRenderer<UniversalScatterTNTEntity> {

    public ScatterTNTRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

    public void render(UniversalScatterTNTEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.5D, 0.0D);
        if ((float)entityIn.getFuse() - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)entityIn.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            matrixStackIn.scale(f1, f1, f1);
        }

        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-90.0F));
        matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
        TNTMinecartRenderer.renderTntFlash(entityIn.getState(), matrixStackIn, bufferIn, packedLightIn, entityIn.getFuse() / 5 % 2 == 0);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public static void renderTntFlash(BlockState blockStateIn, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int combinedLight, boolean doFullBright) {
        int i;
        if (doFullBright) {
            i = OverlayTexture.getPackedUV(OverlayTexture.getU(1.0F), 10);
        } else {
            i = OverlayTexture.NO_OVERLAY;
        }

        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(blockStateIn, matrixStackIn, renderTypeBuffer, combinedLight, i);
    }

    @Override
    public ResourceLocation getEntityTexture(UniversalScatterTNTEntity entity) {
        return new ResourceLocation("textures/block/tnt_side.png");
    }

    public static class RenderFactory implements IRenderFactory<UniversalScatterTNTEntity> {

        @Override
        public EntityRenderer<? super UniversalScatterTNTEntity> createRenderFor(EntityRendererManager manager) {
            manager.setRenderShadow(false);
            return new ScatterTNTRenderer(manager);
        }
    }
}
