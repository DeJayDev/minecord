package org.minecord.minecord.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.messaging.PacketMinecordOutConnectRequest;

@SideOnly(Side.CLIENT)
public class GuiMinecordConfigGeneral extends GuiScreen {

    private GuiScreen prev;

    private GuiButton done; //ID = 0
    private GuiButton cancel; //ID = 1

    private GuiButtonBoolean allowRemote; //ID = 2
    private GuiButtonBoolean enableOfflinePresence; //ID = 3
    private GuiButtonBoolean enableToasts; //ID = 4
    private GuiButtonBoolean disableAnalytics;  //ID = 5

    public GuiMinecordConfigGeneral(GuiScreen prev){
        this.prev = prev;
    }

    public void initGui() {
        buttonList.clear();

        done = addButton(new GuiButton(0, width / 2 - 4 - 150,  300, 150, 20, I18n.format("gui.done")));
        cancel = addButton(new GuiButton(1, width / 2 + 4,  300, 150, 20, I18n.format("gui.cancel")));

        allowRemote = addButton(new GuiButtonBoolean(2, width / 2 + 10 + 4, 95, Minecord.INSTANCE.config.getGeneral().isEnableServerApi()));
        enableOfflinePresence = addButton(new GuiButtonBoolean(3, width / 2 + 10 + 4, 146, Minecord.INSTANCE.config.getGeneral().isEnableOfflinePresence()));
        enableToasts = addButton(new GuiButtonBoolean(4, width / 2 + 10 + 4, 202, Minecord.INSTANCE.config.getGeneral().isEnableToasts()));
        disableAnalytics = addButton(new GuiButtonBoolean(5, width / 2 + 10 + 4, 253, Minecord.INSTANCE.config.getGeneral().isDisableAnalytics()));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, I18n.format("config.general.title"), width / 2, 50, 16777215);
        drawString(fontRenderer, I18n.format("config.general.server"), width / 2 - fontRenderer.getStringWidth(I18n.format("config.general.server")), 100, 16777215);
        drawString(fontRenderer, I18n.format("config.general.offline"), width / 2 - fontRenderer.getStringWidth(I18n.format("config.general.offline")), 151, 16777215);
        drawString(fontRenderer, I18n.format("config.general.toasts"), width / 2 - fontRenderer.getStringWidth(I18n.format("config.general.toasts")), 207, 16777215);
        drawString(fontRenderer, I18n.format("config.general.analytics"), width / 2 - fontRenderer.getStringWidth(I18n.format("config.general.analytics")), 258, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void actionPerformed(GuiButton button) {
        switch(button.id) {
            case 0:
                Minecord.INSTANCE.config.getGeneral().setEnableOfflinePresence(enableOfflinePresence.state);
                Minecord.INSTANCE.config.getGeneral().setEnableToasts(enableToasts.state);
                Minecord.INSTANCE.config.getGeneral().setDisableAnalytics(disableAnalytics.state);

                if (!Minecord.INSTANCE.connection.checkConnectionServer() && Minecord.INSTANCE.config.getGeneral().isEnableOfflinePresence()) {
                    Minecord.INSTANCE.connection.updateOfflinePresence(true);
                }else if(!Minecord.INSTANCE.connection.checkConnectionServer() && !Minecord.INSTANCE.config.getGeneral().isEnableOfflinePresence()){
                    Minecord.INSTANCE.discordUtil.clearPresence();
                }

                if(Minecord.INSTANCE.connection.checkConnectionServer() && !allowRemote.state){
                    Minecord.INSTANCE.config.getGeneral().setEnableServerApi(allowRemote.state);
                    Minecord.INSTANCE.connection.disconnect();
                    Minecord.INSTANCE.connection.updateOfflinePresence(true);
                }else if(Minecord.INSTANCE.connection.checkConnectionServer() && allowRemote.state){
                    if(!Minecord.INSTANCE.config.getGeneral().isEnableServerApi()){
                        Minecord.INSTANCE.config.getGeneral().setEnableServerApi(allowRemote.state);
                        Minecord.INSTANCE.packetHandler.sendInitMessage(new PacketMinecordOutConnectRequest());
                    }
                }
                mc.displayGuiScreen(prev);
                if (prev == null)
                    mc.setIngameFocus();
                break;
            case 1:
                this.mc.displayGuiScreen(prev);
                if (prev == null)
                    mc.setIngameFocus();
                break;
            case 2:
                allowRemote.updateState(!allowRemote.state);
                break;
            case 3:
                enableOfflinePresence.updateState(!enableOfflinePresence.state);
                break;
            case 4:
                enableToasts.updateState(!enableToasts.state);
                break;
            case 5:
                disableAnalytics.updateState(!disableAnalytics.state);
                break;
        }
    }

    protected void keyTyped(char typedChar, int keyCode){
        if (keyCode != 28 && keyCode != 156){
            if (keyCode == 1)
                actionPerformed(cancel);
        }
    }

    public boolean doesGuiPauseGame() {
        return true;
    }
}

