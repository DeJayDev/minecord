package org.minecord.minecord;

import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import org.minecord.minecord.discord.*;
import org.minecord.minecord.gui.GuiMinecordToast;

public class ConnectionHandler{

    private boolean discordActive;
    private boolean isConnected;
    private DiscordRichPresence offlinePresence;
    private String connectedIp = "";

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void setDiscord(boolean connected){
        discordActive = connected;
    }

    public void setOfflinePresence(DiscordRichPresence presence){
        this.offlinePresence = presence;
    }

    public void setConnectedIp(String ip){
        connectedIp = ip;
    }

    public boolean checkConnectionServer(){
        return isConnected;
    }

    public boolean checkConnectionDiscord(){
        return discordActive;
    }

    public DiscordRichPresence getOfflinePresence(){
        return offlinePresence;
    }

    public String getConnectedIp(){
        return connectedIp;
    }

    public void updateOfflinePresence(boolean ingame){
        if(Minecord.INSTANCE.config.getGeneral().isEnableToasts()) {
            offlinePresence = Minecord.INSTANCE.presenceHandler.assembleOfflinePresence(ingame);
            Minecord.INSTANCE.discordUtil.updatePresence(offlinePresence);
            System.out.println("Updated offline presence.");
        }else{
            System.out.println("Offline Presence was disabled in the config.");
        }
    }

    public void disconnect(){
        setConnected(false);
        if(Minecord.INSTANCE.config.getGeneral().isEnableToasts())
            Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.CONNECT_FAILURE, new TextComponentString("Disconnected!"), new TextComponentString("Terminated by server.")));
        updateOfflinePresence(true);
    }
}
