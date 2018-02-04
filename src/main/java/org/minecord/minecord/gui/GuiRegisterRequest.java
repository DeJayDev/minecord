package org.minecord.minecord.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiRegisterRequest extends GuiScreen {

    public GuiRegisterRequest(){
        System.out.println("Derp");
    }

    @Override
    public void initGui(){
        buttonList.clear();
        buttonList.add(new GuiButton(0, 100, 100, 60, 60, "Cancel"));
    }

    @Override
    public boolean doesGuiPauseGame() { return true; }

    @Override
    public void drawScreen(int i, int j, float f){
        drawDefaultBackground();
        super.drawScreen(i, j, f);
    }

    @Override
    public void actionPerformed(GuiButton button){

    }
}
