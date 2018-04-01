package org.minecord.minecord;

public enum GameTypeEnum {
    LOBBY("Lobby", false),
    LIMBO("Limbo", false),
    GAME("Playing", true);

    private final String name;
    private final Boolean autoUpdate;

    GameTypeEnum(String name, Boolean autoUpdate){
        this.name = name;
        this.autoUpdate = autoUpdate;
    }

    public String getName() {
        return name;
    }

    public Boolean getAutoUpdate() {
        return autoUpdate;
    }
}
