package org.minecord.minecord.discord;

import com.google.gson.JsonObject;
import net.arikia.dev.drpc.DiscordJoinRequest;
import net.arikia.dev.drpc.callbacks.JoinRequestCallback;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.messaging.PacketMinecordOutEvent;

@SideOnly(Side.CLIENT)
public class JoinRequestEvent implements JoinRequestCallback {

    @Override
    public void apply(DiscordJoinRequest request) {
        JsonObject payload = new JsonObject();
        payload.addProperty("userId", request.userId);
        payload.addProperty("username", request.username);
        payload.addProperty("discriminator", request.discriminator);
        payload.addProperty("avatar", request.avatar);
        PacketMinecordOutEvent packet = new PacketMinecordOutEvent(payload, PacketMinecordOutEvent.EventType.JOINREQUEST_EVENT);
        Minecord.INSTANCE.packetHandler.sendEventMessage(packet);
        System.out.println("JoinRequest fired, message dispatched. Message: " + packet.getJson().toString());
    }
}
