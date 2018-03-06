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
            presence.state = json.get("state").getAsString();
        if (json.has("details"))
            presence.details = json.get("details").getAsString();
        if (json.has("timestamps")) {
            JsonObject timestamps = json.getAsJsonObject("timestamps");
            if (timestamps.has("start"))
                presence.startTimestamp = timestamps.get("start").getAsLong();
            if (timestamps.has("end"))
                presence.startTimestamp = timestamps.get("end").getAsLong();
        }
        if (json.has("images")) {
            JsonObject images = json.getAsJsonObject("images");
            if (images.has("small")) {
                JsonObject small = images.getAsJsonObject("small");
                if (small.has("key"))
                    presence.smallImageKey = small.get("key").getAsString();
                if (small.has("text"))
                    presence.smallImageText = small.get("text").getAsString();
            }
            if (images.has("large")) {
                JsonObject large = images.getAsJsonObject("large");
                if (large.has("key"))
                    presence.largeImageKey = large.get("key").getAsString();
                if (large.has("text"))
                    presence.largeImageText = large.get("text").getAsString();
            }
        }
        if (json.has("party")) {
            JsonObject party = json.getAsJsonObject("party");
            if (party.has("id"))
                presence.partyId = party.get("id").getAsString();
            if (party.has("size"))
                presence.partySize = party.get("size").getAsInt();
            if (party.has("max"))
                presence.partyMax = party.get("max").getAsInt();
        }
        if (json.has("secrets")) {
            JsonObject secrets = json.getAsJsonObject("secrets");
            if (secrets.has("match"))
                presence.matchSecret = secrets.get("match").getAsString();
            if (secrets.has("spectate"))
                presence.spectateSecret = secrets.get("spectate").getAsString();
            if (secrets.has("join"))
                presence.joinSecret = secrets.get("join").getAsString();
        }
        if (json.has("instance"))
            presence.instance = json.get("instance").getAsBoolean() ? 1 : 0;

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
