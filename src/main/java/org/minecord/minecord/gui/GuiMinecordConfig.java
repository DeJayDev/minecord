package org.minecord.minecord.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.minecord.minecord.Minecord;

public class GuiMinecordConfig extends GuiScreen {

    private GuiScreen prev;

    private GuiButton done;
    private GuiButton general;
    private GuiButton presence;

    public GuiMinecordConfig(GuiScreen prev){
        this.prev = prev;
    }

    @Override
    public void initGui() {
        buttonList.clear();
        done = addButton(new GuiButton(0, width / 2 - 100, 250, 200, 20, I18n.format("gui.done")));
        general = addButton(new GuiButton(1, width / 2 - 100,  100, 200, 20, I18n.format("config.menu.general")));
        presence = addButton(new GuiButton(2, width / 2 - 100, 150, 200, 20, I18n.format("config.menu.presence")));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, I18n.format("config.menu.title"), width / 2, 50, 16777215);
        drawCenteredString(fontRenderer, Minecord.INSTANCE.connection.checkConnectionDiscord() ? "Discord is §2ready." : "Discord is §4not ready.", width / 2, 290, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void actionPerformed(GuiButton button){
        switch(button.id){
            case 0:
                this.mc.displayGuiScreen(prev);
                if (prev == null)
                    mc.setIngameFocus();
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiMinecordConfigGeneral(this));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiMinecordConfigPresence(this));
                break;
        }
    }

    protected void keyTyped(char typedChar, int keyCode){
        if (keyCode != 28 && keyCode != 156){
            if (keyCode == 1)
                actionPerformed(done);
        }
    }

    public boolean doesGuiPauseGame() {
        return true;
    }
}
