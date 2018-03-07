package org.minecord.minecord.messaging;

import com.google.gson.*;
import io.netty.buffer.ByteBuf;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.MinecordConfig;
import org.minecord.minecord.gui.GuiMinecordToast;

import java.nio.charset.Charset;

@SideOnly(Side.CLIENT)
public class PacketMinecordInUpdatePresence implements IMessage {

    private DiscordRichPresence richPresence;
    private String jsonRaw;

    public PacketMinecordInUpdatePresence() {}

    public DiscordRichPresence getPresence() {
        return richPresence;
    }

    public String getJsonRaw() {
        return jsonRaw;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        byte[] messageBytes = new byte[buf.capacity()];
        buf.getBytes(0, messageBytes);
        jsonRaw = new String(messageBytes, Charset.forName("UTF-8"));
        JsonObject json = new JsonParser().parse(jsonRaw).getAsJsonObject();

        DiscordRichPresence presence = new DiscordRichPresence();
        if (json.has("state"))
            presence.setState(json.get("state").getAsString());
        if (json.has("details"))
            presence.setDetails(json.get("details").getAsString());
        if (json.has("timestamps")) {
            JsonObject timestamps = json.getAsJsonObject("timestamps");
            if (timestamps.has("start"))
                presence.setStartTimestamp(timestamps.get("start").getAsLong());
            if (timestamps.has("end"))
                presence.setEndTimestamp(timestamps.get("end").getAsLong());
        }
        if (json.has("images")) {
            JsonObject images = json.getAsJsonObject("images");
            if (images.has("small")) {
                JsonObject small = images.getAsJsonObject("small");
                if (small.has("key"))
                    presence.setSmallImageKey(small.get("key").getAsString());
                if (small.has("text"))
                    presence.setSmallImageText(small.get("text").getAsString());
            }
            if (images.has("large")) {
                JsonObject large = images.getAsJsonObject("large");
                if (large.has("key"))
                    presence.setLargeImageKey(large.get("key").getAsString());
                if (large.has("text"))
                    presence.setLargeImageText(large.get("text").getAsString());
            }
        }
        if (json.has("party")) {
            JsonObject party = json.getAsJsonObject("party");
            if (party.has("id"))
                presence.setPartyId(party.get("id").getAsString());
            if (party.has("size"))
                presence.setPartySize(party.get("size").getAsInt());
            if (party.has("max"))
                presence.setPartyMax(party.get("max").getAsInt());
        }
        if (json.has("secrets")) {
            JsonObject secrets = json.getAsJsonObject("secrets");
            if (secrets.has("match"))
                presence.setMatchSecret(secrets.get("match").getAsString());
            if (secrets.has("spectate"))
                presence.setSpectateSecret(secrets.get("spectate").getAsString());
            if (secrets.has("join"))
                presence.setJoinSecret(secrets.get("join").getAsString());
        }
        if (json.has("instance"))
            presence.setInstance(json.get("instance").getAsBoolean() ? 1 : 0);

        richPresence = presence;
    }

    @Override
    public void toBytes(ByteBuf buf){}

    public static class Handler implements IMessageHandler<PacketMinecordInUpdatePresence, IMessage>{

        @Override
        public IMessage onMessage(PacketMinecordInUpdatePresence message, MessageContext ctx) {
            if(!Minecord.INSTANCE.isConnected)
                return null;
            System.out.println("MINECORD|RP - Received Presence Update! Raw Message: \"" + message.getJsonRaw() + "\"");

            if(MinecordConfig.general.allowToasts)
                Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.YOU_WIN, new TextComponentString("Presence updated!"), null));

            Minecord.INSTANCE.discordUtil.updatePresence(message.getPresence());
            return null;
        }
    }
}
