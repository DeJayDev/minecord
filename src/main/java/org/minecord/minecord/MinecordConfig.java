package org.minecord.minecord;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Minecord.MODID)
public class MinecordConfig {

    @Config.Comment("Wether or not Minecord is allowed to send ToastMessages while ingame.")
    public static boolean allowToasts = true;

    @Mod.EventBusSubscriber(modid = Minecord.MODID)
    private static class EventHandler{

        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event){
            if(event.getModID().equals(Minecord.MODID)){
                ConfigManager.sync(Minecord.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
