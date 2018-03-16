package org.minecord.minecord.config;

public class OfflinePresenceConfig {

    private boolean allowServerSet = true;

    private String details = "Playing on %ip";

    private String state = "No server connection";

    private OfflineImagesLarge imageLarge = OfflineImagesLarge.SET_BY_IP;

    private String imageLargeText = "Username: %player";

    private OfflineImagesSmall imageSmall = OfflineImagesSmall.GREEN;

    private String imageSmallText = "No connection.";

    public boolean isAllowServerSet() {
        return allowServerSet;
    }

    public void setAllowServerSet(boolean allowServerSet) {
        this.allowServerSet = allowServerSet;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public OfflineImagesLarge getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(OfflineImagesLarge imageLarge) {
        this.imageLarge = imageLarge;
    }

    public OfflineImagesSmall getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(OfflineImagesSmall imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getImageLargeText() {
        return imageLargeText;
    }

    public void setImageLargeText(String imageLargeText) {
        this.imageLargeText = imageLargeText;
    }

    public String getImageSmallText() {
        return imageSmallText;
    }

    public void setImageSmallText(String imageSmallText) {
        this.imageSmallText = imageSmallText;
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
        SET_BY_IP("", "Set by IP"),
        GRASS("grass", "Grass Block"),
        HYPIXEL("hypixel", "Hypixel"),
        HIVE("hive", "Hive MC"),
        MINEPLEX("mineplex", "Mineplex"),
        WORLD_CHAOS("chaos", "Chaos Biome"),
        WORLD_DELIGHT("delight", "Delight Biome"),
        WORLD_ISLES("isles", "Isles Biome"),
        WORLD_LUCK("luck", "Luck Biome"),
        WORLD_DROUGHT("drought", "Drought Biome"),
        WORLD_MADNESS("madness", "Madness Biome"),
        WORLD_WATER("water", "Ocean Biome");

        private final String key;
        private final String readable;

        OfflineImagesLarge(String key, String readable){
            this.key = key;
            this.readable = readable;
        }

        public String getKey() {
            return key;
        }

        public String getReadable() {
            return readable;
        }
    }
}
