package org.minecord.minecord.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;

@SideOnly(Side.CLIENT)
public class GuiMinecordConfigGeneral extends GuiScreen {

    @Override
    public void initGui() {
        this.buttonList.clear();
        //this.buttonList.add(new GuiButtonToggle(0, this.width / 2, this.height / 2, this.width, this.height, Minecord.INSTANCE.getConfigHandler().getOfflinePresence().isAllowServerSet()));
        this.buttonList.add(new GuiButton(1, this.width, this.height - (this.height / 8) , "Return to Game"));
        GuiTextField text = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100, 60, 200, 20);
        text.drawTextBox();
        text.setFocused(true);
        text.setText(Minecord.INSTANCE.getConfigHandler().getOfflinePresence().getImageLargeText());
    }

    @Override
    protected void actionPerformed(GuiButton button){
        switch(button.id){
            case 0:
                button.enabled = false;
                break;
            case 1:
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Super Cool Minecord Config", this.width / 2, 40, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        if(!Minecord.INSTANCE.isConnected && Minecord.INSTANCE.getConfigHandler().getGeneral().isEnableOfflinePresence()){
            Minecord.INSTANCE.updateOfflinePresence(false);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
