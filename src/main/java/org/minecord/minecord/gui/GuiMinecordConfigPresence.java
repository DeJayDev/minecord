package org.minecord.minecord.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.config.OfflinePresenceConfig;

import java.io.IOException;

public class GuiMinecordConfigPresence extends GuiScreen {

    private GuiScreen prev;

    private GuiButton doneButton; //ID = 0
    private GuiButton cancelButton;//ID = 1

    private GuiButtonImage setByIp; //ID = 2

    private GuiTextField details; //ID = 3
    private GuiTextField state; //ID = 4

    private GuiButton largeImage; //ID = 5
    private OfflinePresenceConfig.OfflineImagesLarge large;
    private GuiTextField largeText; //ID = 6

    private GuiButton smallImage;//ID = 7
    private OfflinePresenceConfig.OfflineImagesSmall small;
    private GuiTextField smallText;//ID = 8

    public GuiMinecordConfigPresence(GuiScreen prev){
        this.prev = prev;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();

        doneButton = addButton(new GuiButton(0, this.width / 2 - 4 - 150,  450, 150, 20, "Done"));
        cancelButton = this.addButton(new GuiButton(1, this.width / 2 + 4,  450, 150, 20, "Cancel"));

        setByIp = new GuiButtonImage(2, this.width / 2, 420, 20, 20, GuiButtonEnum.TRUE.getxTex(), GuiButtonEnum.TRUE.getyTex(), GuiButtonEnum.TRUE.getYdifTex(), GuiMinecordToast.TEXTURE_TOASTS);
        setByIp.setPosition(this.width / 2, 420);

        details = new GuiTextField(3, fontRenderer, this.width / 2 - 138, 95, 276, 20);
        details.setMaxStringLength(255);
        details.setText(Minecord.INSTANCE.config.getOfflinePresence().getDetails());
        details.setFocused(true);

        state = new GuiTextField(4, fontRenderer, this.width / 2 - 138, 146, 276, 20);
        state.setMaxStringLength(255);
        state.setText(Minecord.INSTANCE.config.getOfflinePresence().getState());

        large = Minecord.INSTANCE.config.getOfflinePresence().getImageLarge();
        largeImage = addButton(new GuiButton(5, this.width / 2 - 128, 202, 250, 20, large.getReadable()));
        largeText = new GuiTextField(6, fontRenderer, this.width / 2 - 138, 253, 276, 20);
        largeText.setMaxStringLength(255);
        largeText.setText(Minecord.INSTANCE.config.getOfflinePresence().getImageLargeText());

        small = Minecord.INSTANCE.config.getOfflinePresence().getImageSmall();
        smallImage = addButton(new GuiButton(7, this.width / 2 - 125, 309, 250, 20, small.getReadable()));
        smallText = new GuiTextField(8, fontRenderer, this.width / 2 - 138, 360, 276, 20);
        smallText.setMaxStringLength(255);
        smallText.setText(Minecord.INSTANCE.config.getOfflinePresence().getImageSmallText());
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Offline Presence Config", this.width / 2, 40, 16777215);
        this.drawString(this.fontRenderer, "Presence Details:", this.width / 2 - 138, 80, 16777215);
        this.drawString(this.fontRenderer, "Presence State:", this.width / 2 - 138, 131, 16777215);
        this.drawString(this.fontRenderer, "Large Image:", this.width / 2 - 138, 187, 16777215);
        this.drawString(this.fontRenderer, "Large Image Text:", this.width / 2 - 138, 238, 16777215);
        this.drawString(this.fontRenderer, "Small Image:", this.width / 2 - 138, 294, 16777215);
        this.drawString(this.fontRenderer, "Small Image Text:", this.width / 2 - 138, 345, 16777215);
        this.details.drawTextBox();
        this.state.drawTextBox();
        this.largeText.drawTextBox();
        this.smallText.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void updateScreen(){
        details.updateCursorCounter();
        state.updateCursorCounter();
        largeText.updateCursorCounter();
        smallText.updateCursorCounter();
    }

    public boolean doesGuiPauseGame(){
        return true;
    }

    public void onGuiClosed(){
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button){
        switch(button.id){
            case 0:
                Minecord.INSTANCE.config.getOfflinePresence().setDetails(details.getText());
                Minecord.INSTANCE.config.getOfflinePresence().setState(state.getText());
                Minecord.INSTANCE.config.getOfflinePresence().setImageLargeText(largeText.getText());
                Minecord.INSTANCE.config.getOfflinePresence().setImageSmallText(smallText.getText());
                Minecord.INSTANCE.config.getOfflinePresence().setImageLarge(large);
                Minecord.INSTANCE.config.getOfflinePresence().setImageSmall(small);
                if(!Minecord.INSTANCE.connection.checkConnectionServer() && Minecord.INSTANCE.config.getGeneral().isEnableOfflinePresence())
                    Minecord.INSTANCE.connection.updateOfflinePresence(true);
                this.mc.displayGuiScreen(prev);
                if(prev == null)
                    this.mc.setIngameFocus();
                break;
            case 1:
                this.mc.displayGuiScreen(prev);
                if(prev == null)
                    this.mc.setIngameFocus();
                break;
            case 2:
                large = OfflinePresenceConfig.OfflineImagesLarge.SET_BY_IP;
                largeImage.displayString = OfflinePresenceConfig.OfflineImagesLarge.SET_BY_IP.getReadable();
                largeImage.enabled = false;
            case 5:
                large = OfflinePresenceConfig.OfflineImagesLarge.getNext(large);
                button.displayString = large.getReadable();
                break;
            case 7:
                small = OfflinePresenceConfig.OfflineImagesSmall.getNext(small);
                button.displayString = small.getReadable();
                break;
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.details.mouseClicked(mouseX, mouseY, mouseButton);
        this.state.mouseClicked(mouseX, mouseY, mouseButton);
        this.largeText.mouseClicked(mouseX, mouseY, mouseButton);
        this.smallText.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode){
        this.details.textboxKeyTyped(typedChar, keyCode);
        this.state.textboxKeyTyped(typedChar, keyCode);
        this.largeText.textboxKeyTyped(typedChar, keyCode);
        this.smallText.textboxKeyTyped(typedChar, keyCode);

        if (keyCode != 28 && keyCode != 156){
            if (keyCode == 1)
                this.actionPerformed(cancelButton);
            else
                this.actionPerformed(doneButton);
        }
    }
}
