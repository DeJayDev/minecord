package org.minecord.minecord;

public enum ServerEnum {
    HYPIXEL("Hypixel", MinecordConfig.OfflineImagesLarge.HYPIXEL.getKey()),
    HIVE("HiveMC", MinecordConfig.OfflineImagesLarge.HIVE.getKey()),
    MINEPLEX("Mineplex", MinecordConfig.OfflineImagesLarge.MINEPLEX.getKey()),
    DEFAULT("", "grass"),
    SINGLEPLAYER("Singleplayer", "grass");

    private final String name;
    private final String key;

    ServerEnum(String name, String key){
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
