package org.minecord.minecord.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import org.minecord.minecord.utils.Resources;

public class GuiButtonBoolean extends GuiButton {

    public boolean state;
    private GuiButtonTexture stateTex;

    public GuiButtonBoolean(int id, int x, int y, boolean start){
        super(id, x, y, 20, 20, "");
        this.state = start;
        this.stateTex = state ? GuiButtonTexture.TRUE : GuiButtonTexture.FALSE;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
        if (this.visible){
            mc.getTextureManager().bindTexture(Resources.GUI.getResource());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            boolean hover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int yt;

            if (hover)
                yt = stateTex.yDifTex;
            else
                yt = stateTex.yTex;

            if(!enabled)
                yt = stateTex.yDisTex;

            drawTexturedModalRect(x, y, stateTex.xTex, yt, 20, 20);
        }
    }

    public void updateState(boolean state){
        this.state = state;
        this.stateTex = state ? GuiButtonTexture.TRUE : GuiButtonTexture.FALSE;
    }
}
