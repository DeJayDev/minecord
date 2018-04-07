package org.minecord.minecord.offline;

import org.minecord.minecord.config.OfflinePresenceConfig;

public enum ServerEnum {
    HYPIXEL("Hypixel", OfflinePresenceConfig.OfflineImagesLarge.HYPIXEL.getKey()),
    HIVE("HiveMC", OfflinePresenceConfig.OfflineImagesLarge.HIVE.getKey()),
    MINEPLEX("Mineplex", OfflinePresenceConfig.OfflineImagesLarge.MINEPLEX.getKey()),
    PERILOUS("Perilous", OfflinePresenceConfig.OfflineImagesLarge.PERILOUS.getKey()),
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
