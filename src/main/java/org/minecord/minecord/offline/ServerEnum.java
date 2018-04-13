package org.minecord.minecord.offline;

public enum ServerEnum {
    HYPIXEL("Hypixel", "hypixel"),
    HIVE("HiveMC", "hive"),
    MINEPLEX("Mineplex", "mineplex"),
    PERILOUS("Perilous", "peril"),
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
