package org.minecord.minecord.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.minecord.minecord.utils.Multithreading;

import java.util.concurrent.TimeUnit;

public final class DiscordUtil {

    private final String APP_ID = "378408502181363713";

    public DiscordEventHandlers eventHandler;

    public DiscordUtil(){
        initializeDiscord();
        runCallbackTask();
    }

    public void initializeDiscord(){
        eventHandler = new DiscordEventHandlers.Builder().
                setReadyEventHandler(new ReadyEvent()).
                setDisconnectedEventHandler(new DisconnectedEvent()).
                setErroredEventHandler(new ErroredEvent()).
                setJoinGameEventHandler(new JoinGameEvent()).
                setJoinRequestEventHandler(new JoinRequestEvent()).
                setSpectateGameEventHandler(new SpectateGameEvent()).
                build();
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

    public void runCallbackTask(){
        Multithreading.schedule(() -> {
            DiscordRPC.discordRunCallbacks();
            }, 0L, 500L, TimeUnit.MILLISECONDS);
    }
}
