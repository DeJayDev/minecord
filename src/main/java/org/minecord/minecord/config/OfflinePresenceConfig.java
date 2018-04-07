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
        COMMAND_BLOCK("command_block", "Command Block"),
        CRAFTING_TABLE("crafting_table_front", "Crafting Table"),
        DIAMOND("diamond", "Diamond"),
        DIAMOND_SWORD("diamond_sword", "Diamond Sword"),
        REDSTONE("redstone_dust", "Redstone Dust"),
        ENDER_EYE("ender_eye", "Ender Eye"),
        TNT("tnt", "TNT"),
        RED("concrete_red", "Red"),
        YELLOW("concrete_yellow", "Yellow"),
        GREEN("concrete_green", "Green");

        private final String key;
        private final String readable;

        OfflineImagesSmall(String key, String readable){
            this.key = key;
            this.readable = readable;
        }

        public String getKey() {
            return key;
        }

        public String getReadable() {
            return readable;
        }

        public static OfflineImagesSmall getNext(OfflineImagesSmall se) {
            int index = 0;
            for(int i = 0; i < values().length; i++) {
                if(values()[i] == se) {
                    index = i;
                    break;
                }
            }
            if(index + 1 < values().length)
                return values()[index + 1];
            return values()[0];
        }
    }

    @SuppressWarnings("unused")
    public enum OfflineImagesLarge{
        SET_BY_IP("", "Set by IP"),
        GRASS("grass", "Grass Block"),
        HYPIXEL("hypixel", "Hypixel"),
        HIVE("hive", "HiveMC"),
        MINEPLEX("mineplex", "Mineplex"),
        PERILOUS("peril", "PerilousMC"),
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

        public static OfflineImagesLarge getNext(OfflineImagesLarge se) {
            int index = 0;
            for(int i = 0; i < values().length; i++) {
                if(values()[i] == se) {
                    index = i;
                    break;
                }
            }
            if(index + 1 < values().length)
                return values()[index + 1];
            return values()[0];
        }
    }
}
