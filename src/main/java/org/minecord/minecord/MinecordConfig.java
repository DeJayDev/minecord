package org.minecord.minecord;

import net.minecraftforge.common.config.Config;

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

        @SuppressWarnings("unused")
        @Config.Name("Enable Analytics")
        @Config.Comment("Whether or not Minecord is allowed to collect information like MC Version, Mod Version, Forge Version, UUID, if it's standalone or not, etc.")
        public boolean allowAnalytics = true;
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
        public String offlineDetails = "Playing on %ip";

        @Config.Name("Large Image")
        @Config.Comment("Large square image shown in the presence, when Minecord has no server connection.")
        public OfflineImagesLarge offlineImageLarge = OfflineImagesLarge.SET_BY_IP;

        @Config.Name("Large Image Text")
        @Config.Comment("Hover text of the large square image in the presence, when Minecord has no server connection.")
        public String getOfflineImageLargeText = "This is a large image.";

        @Config.Name("Small Image")
        @Config.Comment("Small round image shown in the presence, when Minecord has no server connection. (Only visible when the large image is visible.)")
        public OfflineImagesSmall offlineImageSmall = OfflineImagesSmall.RED;

        @Config.Name("Small Image Text")
        @Config.Comment("Hover text of the small round image in the presence, when Minecord has no server connection. (Only visible when the large image is visible.)")
        public String offlineImageSmallText = "No Minecord Connection is established atm.";
    }


    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public enum OfflineImagesLarge{
        SET_BY_IP(""),
        GRASS("grass"),
        HYPIXEL("hypixel"),
        HIVE("hive"),
        MINEPLEX("mineplex"),
        WORLD_CHAOS("chaos"),
        WORLD_DELIGHT("delight"),
        WORLD_ISLES("isles"),
        WORLD_LUCK("luck"),
        WORLD_DROUGHT("drought"),
        WORLD_MADNESS("madness"),
        WORLD_WATER("water");

        private final String key;

        OfflineImagesLarge(String key){
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
