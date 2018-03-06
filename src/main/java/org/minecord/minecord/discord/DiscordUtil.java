package org.minecord.minecord.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.minecord.minecord.MinecordConfig;

import java.util.Timer;
import java.util.TimerTask;

public final class DiscordUtil {

    private final String APP_ID = "378408502181363713";

    public DiscordCallbackTask callbackTask = new DiscordCallbackTask();

    public DiscordEventHandlers eventHandler;

    public DiscordEventHandlers getEventHandler(){
        return eventHandler;
    }

    public DiscordCallbackTask getCallbackTask() {
        return callbackTask;
    }

    public DiscordUtil(){
        eventHandler = new DiscordEventHandlers();
    }

    public void initializeDiscord(){
        DiscordRPC.discordInitialize(APP_ID, eventHandler, true);
    }

    public void registerExecutablePath(String command){
        DiscordRPC.discordRegister(APP_ID, command);
    }

    public void updatePresence(DiscordRichPresence p){
        DiscordRPC.discordUpdatePresence(p);
    }

    public void clearPresence() { DiscordRPC.discordClearPresence(); }

    public void runCallbackTask(){
        Timer t = new Timer();
        t.schedule(callbackTask, 0, 50);
        System.out.println("Started CallbackTask.");
    }

    public void stopCallbackTask(){
        callbackTask.cancel();
        System.out.println("Stopped CallbackTask.");
    }

    public DiscordRichPresence assembleOfflinePresence(){
        DiscordRichPresence p = new DiscordRichPresence();
        p.state = MinecordConfig.offline.offlineState;
        p.details = MinecordConfig.offline.offlineDetails;
        p.smallImageKey = MinecordConfig.offline.offlineImageSmall.getKey();
        p.smallImageText = MinecordConfig.offline.offlineImageSmallText;
        p.instance = 1;
        return p;
    }


    public static class DiscordCallbackTask extends TimerTask{
        public void run(){
            DiscordRPC.discordRunCallbacks();
        }

        public void stop(){
            this.cancel();
        }
    }
}
