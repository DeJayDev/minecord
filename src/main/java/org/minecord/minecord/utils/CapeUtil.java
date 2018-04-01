package org.minecord.minecord.utils;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;
import java.util.List;

public class CapeUtil implements LayerRenderer<AbstractClientPlayer> {

    private final RenderPlayer playerRender;
    private final Players player;

    public CapeUtil(RenderPlayer playerRender, Players player){
        this.playerRender = playerRender;
        this.player = player;
    }

    public void doRenderLayer(AbstractClientPlayer entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        boolean hasCape = false;
        String uuid = entity.getUniqueID().toString().replaceAll("-", "");
        if(uuid.equalsIgnoreCase(Players.VATUU.uuid) || uuid.equalsIgnoreCase(Players.DEJAYDEV.uuid) || uuid.equalsIgnoreCase(Players.LH_KEITH.uuid)){
            hasCape = true;
        }

        if (entity.hasPlayerInfo() && !entity.isInvisible() && entity.isWearing(EnumPlayerModelParts.CAPE) && hasCape)
        {
            ItemStack itemstack = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

            if (itemstack.getItem() != Items.ELYTRA)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.playerRender.bindTexture(player.getCape());
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0F, 0.0F, 0.125F);
                double d0 = entity.prevChasingPosX + (entity.chasingPosX - entity.prevChasingPosX) * (double)partialTicks - (entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks);
                double d1 = entity.prevChasingPosY + (entity.chasingPosY - entity.prevChasingPosY) * (double)partialTicks - (entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks);
                double d2 = entity.prevChasingPosZ + (entity.chasingPosZ - entity.prevChasingPosZ) * (double)partialTicks - (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks);
                float f = entity.prevRenderYawOffset + (entity.renderYawOffset - entity.prevRenderYawOffset) * partialTicks;
                double d3 = (double)MathHelper.sin(f * 0.017453292F);
                double d4 = (double)(-MathHelper.cos(f * 0.017453292F));
                float f1 = (float)d1 * 10.0F;
                f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
                float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
                float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;

                if (f2 < 0.0F)
                {
                    f2 = 0.0F;
                }

                if (f2 > 165.0F)
                {
                    f2 = 165.0F;
                }

                float f4 = entity.prevCameraYaw + (entity.cameraYaw - entity.prevCameraYaw) * partialTicks;
                f1 = f1 + MathHelper.sin((entity.prevDistanceWalkedModified + (entity.distanceWalkedModified - entity.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;

                if (entity.isSneaking())
                {
                    f1 += 25.0F;
                    GlStateManager.translate(0.0F, 0.142F, -0.0178F);
                }

                GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                this.playerRender.getMainModel().renderCape(0.0625F);
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    public enum Players{
        VATUU(Resources.CAPE_MOJANG, "275df345c3c547689af8440d9cdf593e"),
        DEJAYDEV(Resources.CAPE_DEV, "9f15bf3896dd4d0cba3bcc6e447402ec"),
        LH_KEITH(Resources.CAPE_DEV, "8d96a41c7c03423f93e4c490a198b45c");

        private final Resources resources;
        private final String uuid;

        private final List<String> validPlayers = Arrays.asList("275df345c3c547689af8440d9cdf593e", "9f15bf3896dd4d0cba3bcc6e447402ec", "8d96a41c7c03423f93e4c490a198b45c");

        Players(Resources cape, String uuid){
            this.resources = cape;
            this.uuid = uuid;
        }

        public ResourceLocation getCape() {
            return resources.getResource();
        }

        public Players getIfValid(String uuid){
            validPlayers.forEach(u -> {
                if(u.replaceAll("-", "").equalsIgnoreCase(uuid)){

                }
            });
            return null;
        }
    }
}
