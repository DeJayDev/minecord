package org.minecord.minecord.offline;

import net.arikia.dev.drpc.DiscordRichPresence;
import org.apache.logging.log4j.core.jmx.Server;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.config.OfflinePresenceConfig;

import java.nio.channels.NotYetConnectedException;

public class OfflinePresenceHandler {

    public DiscordRichPresence assembleOfflinePresence(boolean ingame) {
        DiscordRichPresence p = new DiscordRichPresence();

        ServerEnum currentServer = ServerEnum.DEFAULT;
        String currentIP = Minecord.INSTANCE.connection.getConnectedIp().toLowerCase();
        boolean useServerData = Minecord.INSTANCE.config.getOfflinePresence().isAllowServerSet();
        OfflinePresenceConfig presenceConfig = Minecord.INSTANCE.config.getOfflinePresence();
        String details = presenceConfig.getDetails();

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

        p.details = parseData(presenceConfig.getDetails(), currentServer == ServerEnum.DEFAULT ? null : currentServer, ingame);
        p.state = parseData(presenceConfig.getState(), currentServer == ServerEnum.DEFAULT ? null : currentServer, ingame);
        p.smallImageKey = presenceConfig.getImageSmall().getKey();
        p.smallImageText = parseData(presenceConfig.getImageSmallText(), currentServer == ServerEnum.DEFAULT ? null : currentServer, ingame);

        if(presenceConfig.getImageLarge() == OfflinePresenceConfig.OfflineImagesLarge.SET_BY_IP){
            p.largeImageKey = currentServer.getKey();
            p.largeImageText = currentServer.getName();
        }else{
            p.largeImageKey = presenceConfig.getImageLarge().getKey();
            p.largeImageText = parseData(presenceConfig.getImageLargeText(), currentServer, ingame);
        }

        return p;
    }

    private String parseData(String details, ServerEnum server, boolean ingame) {
        String result = "";

        if(details.toLowerCase().contains("%ip%")) {
            if(!ingame)
                result = details.replace("%ip%", "the Main Menu");
            else if(server != null)
                result = details.replace("%ip%", server.getName());
            else
                result = details.replace("%ip%", Minecord.INSTANCE.connection.getConnectedIp());
        }

        if(details.toLowerCase().contains("%player%")) {
            result = details.replace("%player%", Minecord.INSTANCE.profile.getDisplayName());
        }

        return result.equalsIgnoreCase("") ? details : result;
    }

}
