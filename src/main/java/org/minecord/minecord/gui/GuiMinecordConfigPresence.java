package org.minecord.minecord.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import org.minecord.minecord.Minecord;

import java.io.IOException;

public class GuiMinecordConfigPresence extends GuiScreen {

    private GuiButton doneButton; //ID = 0
    private GuiButton cancelButton;// ID = 1

    private GuiTextField details;
    private GuiTextField state;
    private GuiTextField largeText;
    private GuiTextField smallText;

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        doneButton = addButton(new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, "Done"));
        cancelButton = this.addButton(new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, "Cancel"));
        details = new GuiTextField(2, fontRenderer, this.width / 2 - 150, 135, 276, 20);
        details.setMaxStringLength(255);
        details.setText(Minecord.INSTANCE.getConfigHandler().getOfflinePresence().getDetails());
        details.setFocused(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Offline Presence Config", this.width / 2, 40, 16777215);
        this.details.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.details.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void updateScreen()
    {
        details.updateCursorCounter();
    }

    @Override
    protected void actionPerformed(GuiButton button){
        switch(button.id){
            case 0:
                button.enabled = false;
                break;
            case 1:
                Minecord.INSTANCE.getConfigHandler().getOfflinePresence().setDetails(details.getText());
                if(!Minecord.INSTANCE.isConnected && Minecord.INSTANCE.getConfigHandler().getGeneral().isEnableOfflinePresence())
                    Minecord.INSTANCE.updateOfflinePresence(true);
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
        }
    }

    protected void keyTyped(char typedChar, int keyCode){
        this.details.textboxKeyTyped(typedChar, keyCode);

        if (keyCode != 28 && keyCode != 156)
        {
            if (keyCode == 1)
            {
                this.actionPerformed(cancelButton);
            }
        }
        else
        {
            this.actionPerformed(doneButton);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
}
