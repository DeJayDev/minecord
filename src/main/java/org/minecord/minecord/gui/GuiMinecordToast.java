package org.minecord.minecord.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class GuiMinecordToast implements IToast{

    private final ResourceLocation TEXTURE_TOASTS = new ResourceLocation("minecord:textures/gui/toasts.png");

    private Icons icon;
    private String title;
    private String subtitle;
    private long firstDrawTime;
    private boolean drawed = true;

    public GuiMinecordToast(Icons icon, ITextComponent title, @Nullable ITextComponent subtitle){
        this.icon = icon;
        this.title = title.getUnformattedText();
        this.subtitle = subtitle == null ? null : subtitle.getUnformattedText();
    }

    @Override
    public Visibility draw(GuiToast toastGui, long delta) {
        if(drawed) {
            firstDrawTime = delta;
            drawed = false;
        }
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        if(icon == Icons.CONNECT_SUCCESS){
            toastGui.drawTexturedModalRect(0, 0, 0, 0, 160, 32);
        }else if(icon == Icons.CONNECT_FAILURE){
            toastGui.drawTexturedModalRect(0, 0, 0, 32, 160, 32);
        }else{
            toastGui.drawTexturedModalRect(0, 0, 0, 64, 160, 32);
        }
        icon.draw(toastGui, 6, 6);

        if(subtitle == null){
            toastGui.getMinecraft().fontRenderer.drawString(title, 30, 12, -11534256);
        }else{
            toastGui.getMinecraft().fontRenderer.drawString(title, 30, 7, -11534256);
            toastGui.getMinecraft().fontRenderer.drawString(subtitle, 30, 18, -16777216);
        }

        return delta - this.firstDrawTime >= 2500L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
    }

    public enum Icons{
        CONNECT_FAILURE(0, 0),
        CONNECT_SUCCESS(1, 0),
        YOU_WIN(2, 0);

        private final int column;
        private final int row;

        Icons(int columnIn, int rowIn){
            this.column = columnIn;
            this.row = rowIn;
        }

        public void draw(Gui guiIn, int x, int y) {
            GlStateManager.enableBlend();
            guiIn.drawTexturedModalRect(x, y, 176 + this.column * 20, this.row * 20, 20, 20);
            GlStateManager.enableBlend();
        }
    }
}
