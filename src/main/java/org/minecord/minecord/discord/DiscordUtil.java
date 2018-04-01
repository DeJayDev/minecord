package org.minecord.minecord.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.ServerEnum;
import org.minecord.minecord.config.OfflinePresenceConfig;
import org.minecord.minecord.utils.Multithreading;

import java.util.concurrent.TimeUnit;

public final class DiscordUtil {

    private final String APP_ID = "378408502181363713";

    public DiscordEventHandlers eventHandler;

    public DiscordUtil(){
        eventHandler = new DiscordEventHandlers();
        eventHandler.ready = new ReadyEvent();
        eventHandler.disconnected = new DisconnectedEvent();
        eventHandler.errored = new ErroredEvent();
        eventHandler.joinGame = new JoinGameEvent();
        eventHandler.joinRequest = new JoinRequestEvent();
        eventHandler.spectateGame = new SpectateGameEvent();
        initializeDiscord();
        runCallbackTask();
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
        p.state = Minecord.INSTANCE.config.getOfflinePresence().getState();
        p.largeImageText = Minecord.INSTANCE.config.getOfflinePresence().getImageLargeText();
        p.smallImageKey = Minecord.INSTANCE.config.getOfflinePresence().getImageSmall().getKey();
        p.smallImageText = Minecord.INSTANCE.config.getOfflinePresence().getImageSmallText();
        //p.instance = 1;

        ServerEnum connectedServer = ServerEnum.DEFAULT;
        String ip = Minecord.INSTANCE.connection.getConnectedIp();
        if(ip.contains("hive")){
            connectedServer = ServerEnum.HIVE;
        }else if(ip.contains("hypixel")){
            connectedServer = ServerEnum.HYPIXEL;
        }else if(ip.contains("mineplex")){
            connectedServer = ServerEnum.MINEPLEX;
        }else if(ip.equals("")){
            connectedServer = ServerEnum.SINGLEPLAYER;
        }


        String details = Minecord.INSTANCE.config.getOfflinePresence().getDetails();
        p.details = details.contains("%ip") ? (connectedServer == ServerEnum.DEFAULT ? details.replace("%ip", ip) : details.replace("%ip", connectedServer.getName())) : details;
        OfflinePresenceConfig.OfflineImagesLarge large = Minecord.INSTANCE.config.getOfflinePresence().getImageLarge();
        p.largeImageKey = large == OfflinePresenceConfig.OfflineImagesLarge.SET_BY_IP ? connectedServer.getKey() : large.getKey();


        if(!ingame){
            p.details = details.contains("%ip") ? details.replace("%ip", "the Main Menu") : details;
            p.largeImageKey = large == OfflinePresenceConfig.OfflineImagesLarge.SET_BY_IP ? OfflinePresenceConfig.OfflineImagesLarge.GRASS.getKey() : large.getKey();
        }

        return p;
    }

    public void runCallbackTask(){
        Multithreading.schedule(() -> DiscordRPC.discordRunCallbacks(), 0L, 500L, TimeUnit.MILLISECONDS);
    }
}
