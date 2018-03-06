package org.minecord.minecord;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Minecord.MODID, name = "Minecord Configuration")
public class MinecordConfig{

    @Config.Name("General Settings")
    @Config.Comment("General Settings regarding Minecord")
    public static final GeneralSettings general = new GeneralSettings();

    @Config.Name("Offline Presence")
    @Config.Comment("Customization of the Presence when Minecord has no server connection.")
    public static final OfflineSettings offline = new OfflineSettings();

    public static class GeneralSettings{

        @Config.Name("Enable Toasts")
        @Config.Comment("Whether or not a toast message appears when a MinecordThingy happens.")
        public boolean allowToasts = true;
    }

    public static class OfflineSettings{

        @Config.Name("Enable Offline Presence")
        @Config.Comment("Whether or not a configurable presence is displayed, when Minecord has no server connection.")
        public boolean offlinePresenceEnabled = true;

        @Config.Name("Presence State")
        @Config.Comment("Text showed in the state of the presence, when Minecord has no server connection.")
        public String offlineState = "Idling";

        @Config.Name("Presence Details")
        @Config.Comment("Text showed in the details of the presence, when Minecord has no server connection.")
        public String offlineDetails = "Waiting for a server connection.";

        @Config.Name("Small Image")
        @Config.Comment("Small round image shown in the presence, when Minecord has no server connection.")
        public OfflineImagesSmall offlineImageSmall = OfflineImagesSmall.RED;

        @Config.Name("Small Image Text")
        @Config.Comment("Hover text of the small round image in the presence, when Minecord has no server connection.")
        public String offlineImageSmallText = "No Minecord Connection is established atm.";
    }


    public enum OfflineImagesSmall{
        COMMAND_BLOCK("command_block"),
        CRAFTING_TABLE("crafting_table_front"),
        DIAMOND("diamond"),
        DIAMOND_SWORD("diamond_sword"),
        REDSTONE("redstone_dust"),
        ENDER_EYE("ender_eye"),
        TNT("tnt"),
        RED("concrete_red"),
        YELLOW("concrete_yellow"),
        GREEN("concrete_yellow");

        private final String key;

        OfflineImagesSmall(String key){
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
