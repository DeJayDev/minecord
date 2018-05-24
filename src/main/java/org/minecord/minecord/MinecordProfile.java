package org.minecord.minecord;

import net.minecraft.client.Minecraft;

import java.nio.channels.NotYetConnectedException;
import java.util.UUID;

public final class MinecordProfile {

    public boolean discordLoaded = false;
    private DiscordUser discord;

    private final UUID uuid;
    private final String displayName;

    public static MinecordProfile create(UUID uuid){
        return new MinecordProfile(uuid);
    }

    private MinecordProfile(UUID uuid){
        this.uuid = uuid;
        this.displayName = Minecraft.getMinecraft().getSession().getUsername();
        System.out.println(uuid + " | " + displayName);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public DiscordUser getDiscordInformation() throws NotYetConnectedException{
        if(discordLoaded)
            return discord;
        else
            throw new NotYetConnectedException();
    }

    public void setDiscordInformation(DiscordUser user){
        this.discord = user;
        discordLoaded = true;
    }

    public static class DiscordUser{

        private String userID;
        private int discriminator;
        private String username;
        private String avatarBase64;

        public DiscordUser(String id, int discrim, String user, String avatar){
            this.userID = id;
            this.discriminator = discrim;
            this.username = user;
            this.avatarBase64 = avatar;
        }

        public String getUserID() {
            return userID;
        }

        public int getDiscriminator() {
            return discriminator;
        }

        public String getUsername() {
            return username;
        }

        public String getAvatarBase64() {
            return avatarBase64;
        }
    }
}
