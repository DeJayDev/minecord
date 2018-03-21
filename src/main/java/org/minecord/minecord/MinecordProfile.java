package org.minecord.minecord;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;

import java.util.Map;
import java.util.UUID;

public final class MinecordProfile {

    //Minecraft Stuff
    private final UUID uuid;
    private final String displayName;

    //Discord Stuff
    private final String userID;
    private final int discriminator;
    private final String username;
    private final String avatarBase64; //SoonTM

    //Minecord Stuff
    private boolean isRegistered;
    private final boolean isDonator;
    private Map<Cosmetics, Boolean> cosmetics;

    public static MinecordProfile create(UUID uuid){
        return new MinecordProfile(uuid);
    }

    private MinecordProfile(UUID uuid){
        this.uuid = uuid;
        this.displayName = Minecraft.getMinecraft().player.getName();

        /*TODO FETCH DATABASE ENTRY
          -------------------------
          if(valid){
            isRegistered = true;
            fetchDiscordDetails(JsonObject dbResponse);
            isDonator = dbResponse.isDonator == true ? true : false;
            parseCosmetics(JsonObject cosmetics);
          }else{
            isRegistered = false;
            createRegisterPopup();
          }
         */
        this.isRegistered = true;
        this.isDonator = true;

        this.userID = "1234567890987654321";
        this.discriminator = 1337;
        this.username = "Derpface McGee";
        this.avatarBase64 = "";

        parseCosmetics(null);
    }

    private void parseCosmetics(JsonObject dbCosmetics){
        for(Cosmetics c : Cosmetics.values()){
            cosmetics.put(c, true);
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
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

    public boolean isRegistered() {
        return isRegistered;
    }

    public boolean isDonator() {
        return isDonator;
    }

    public Map<Cosmetics, Boolean> getCosmetics() {
        return cosmetics;
    }

    public enum Cosmetics{
        BEANIE,
        CAP,
        HOODIE,
        BACKPACK,
        TOPHAT,
        HYPESQUAD_PIN,
        NITRO_PIN,
        PARTNER_PIN,
        DISDEV_PIN,
        DEV_PIN;
    }
}
