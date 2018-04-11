package org.minecord.minecord.offline;

import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.config.OfflinePresenceConfig;

public class OfflinePresenceHandler {

    public DiscordRichPresence assembleOfflinePresence(boolean ingame) {
        DiscordRichPresence p = new DiscordRichPresence();

        ServerEnum currentServer = ServerEnum.DEFAULT;
        String currentIP = Minecord.INSTANCE.connection.getConnectedIp().toLowerCase();
        Boolean useServerData = Minecord.INSTANCE.config.getOfflinePresence().isAllowServerSet();
        OfflinePresenceConfig presenceConfig = Minecord.INSTANCE.config.getOfflinePresence();

        if (currentIP.contains("hive")) {
            currentServer = ServerEnum.HIVE;
        } else if (currentIP.contains("hypixel")) {
            currentServer = ServerEnum.HYPIXEL;
        } else if (currentIP.contains("mineplex")) {
            currentServer = ServerEnum.MINEPLEX;
        } else if (currentIP.contains("perilous")) {
            currentServer = ServerEnum.PERILOUS;
        } else if (currentIP.contains("")) {
            currentServer = ServerEnum.SINGLEPLAYER;
        }

        if (!useServerData) {
            String details = presenceConfig.getDetails();
            p.state = presenceConfig.getState();
            p.largeImageText = presenceConfig.getImageLargeText();
            p.smallImageKey = presenceConfig.getImageSmall().getKey();
            p.smallImageText = presenceConfig.getImageSmallText();
            p.instance = 1;
            p.details = parseData(details);
            if (!ingame) {
                p.details = details.contains("%ip") ? details.replace("%ip", "the Main Menu") : details;
                p.largeImageKey = presenceConfig.getImageLarge() == OfflinePresenceConfig.OfflineImagesLarge.SET_BY_IP ? OfflinePresenceConfig.OfflineImagesLarge.GRASS.getKey() : presenceConfig.getImageLarge().getKey();
            }
        } else {
            if(Minecord.INSTANCE.connection.checkConnectionServer()) {
                p.details = "Playing Minecraft";
                p.state = "Singleplayer";
            } else {
                p.details = "Playing" + currentServer != null ? " " + currentServer.getName() : " Minecraft";
                p.largeImageKey = currentServer == null ? "blank_server" : currentServer.getName();
                p.smallImageKey = "command_block";
                p.smallImageText = "Test String! I shouldn't exist.";
            }
        }


       /*
        String details = Minecord.INSTANCE.config.getOfflinePresence().getDetails();
        p.details = details.contains("%ip") ? (connectedServer == ServerEnum.DEFAULT ? details.replace("%ip", ip) : details.replace("%ip", connectedServer.getName())) : details;
        OfflinePresenceConfig.OfflineImagesLarge large = Minecord.INSTANCE.config.getOfflinePresence().getImageLarge();
        p.largeImageKey = large == OfflinePresenceConfig.OfflineImagesLarge.SET_BY_IP ? connectedServer.getKey() : large.getKey();

        if(!ingame){
            p.details = details.contains("%ip") ? details.replace("%ip", "the Main Menu") : details;
            p.largeImageKey = large == OfflinePresenceConfig.OfflineImagesLarge.SET_BY_IP ? OfflinePresenceConfig.OfflineImagesLarge.GRASS.getKey() : large.getKey();
        }*/

        return p;
    }

    private String parseData(String details) {
        String result = "";

        if(details.toLowerCase().contains("%ip")) {
            result = details.replace("%ip", Minecord.INSTANCE.connection.getConnectedIp());
        } else if(details.toLowerCase().contains("%player")) {
            result = details.replace("%player", Minecord.INSTANCE.profile.getUsername());
        }

        return result;
    }

}
