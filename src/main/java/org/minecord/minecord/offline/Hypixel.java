package org.minecord.minecord.offline;

import net.arikia.dev.drpc.DiscordRichPresence;

public class Hypixel {

    public static DiscordRichPresence getCurrentState(){
        //TODO THIS
        return null;
    }

    public static boolean requiresUpdateInterval(){
        return false;
    }

    private enum GameModeUpdate{
        GAMEMODE_1(false, 2),
        GAMEMODE_2(true, 5);

        public boolean update;
        public long interval; //Recommended Update interval in seconds.

        GameModeUpdate(boolean update, long interval){
            this.update = update;
            this.interval = interval;
        }
    }
}
