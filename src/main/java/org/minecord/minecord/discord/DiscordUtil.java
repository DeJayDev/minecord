package org.minecord.minecord.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.MinecordConfig;
import org.minecord.minecord.ServerEnum;
import org.minecord.minecord.Utils;

import java.util.concurrent.TimeUnit;

public final class DiscordUtil {

    private final String APP_ID = "378408502181363713";

    public DiscordEventHandlers eventHandler;

    public DiscordUtil(){
        eventHandler = new DiscordEventHandlers();
    }

    public void initializeDiscord(){
        DiscordRPC.discordInitialize(APP_ID, eventHandler, true);
    }

    @SuppressWarnings("unused")
    public void registerExecutablePath(String command){
        DiscordRPC.discordRegister(APP_ID, command);
    }

    public void updatePresence(DiscordRichPresence p){
        DiscordRPC.discordUpdatePresence(p);
    }

    public void clearPresence() { DiscordRPC.discordClearPresence(); }

    public DiscordRichPresence assembleOfflinePresence(boolean ingame){
        DiscordRichPresence p = new DiscordRichPresence();
        p.state = MinecordConfig.offline.offlineState;
        p.largeImageText = MinecordConfig.offline.getOfflineImageLargeText;
        p.smallImageKey = MinecordConfig.offline.offlineImageSmall.getKey();
        p.smallImageText = MinecordConfig.offline.offlineImageSmallText;
        p.instance = 1;

        ServerEnum connectedServer = ServerEnum.DEFAULT;
        String ip = Minecord.INSTANCE.connectedIp;
        if(ip.contains("hive")){
            connectedServer = ServerEnum.HIVE;
        }else if(ip.contains("hypixel")){
            connectedServer = ServerEnum.HYPIXEL;
        }else if(ip.contains("mineplex")){
            connectedServer = ServerEnum.MINEPLEX;
        }else if(ip.equals("")){
            connectedServer = ServerEnum.SINGLEPLAYER;
        }


        String details = MinecordConfig.offline.offlineDetails;
        p.details = details.contains("%ip") ? (connectedServer == ServerEnum.DEFAULT ? details.replace("%ip", ip) : details.replace("%ip", connectedServer.getName())) : details;
        MinecordConfig.OfflineImagesLarge large = MinecordConfig.offline.offlineImageLarge;
        p.largeImageKey = large == MinecordConfig.OfflineImagesLarge.SET_BY_IP ? connectedServer.getKey() : large.getKey();


        if(!ingame){
            p.details = details.contains("%ip") ? details.replace("%ip", "the Main Menu") : details;
            p.largeImageKey = large == MinecordConfig.OfflineImagesLarge.SET_BY_IP ? MinecordConfig.OfflineImagesLarge.GRASS.getKey() : large.getKey();
        }

        return p;
    }

    public void runCallbackTask(){
        Utils.schedule(() -> {
            DiscordRPC.discordRunCallbacks();
            System.out.println("RAN CALLBACK!");
            }, 0L, 500L, TimeUnit.MILLISECONDS);
    }
}
