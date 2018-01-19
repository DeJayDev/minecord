package org.minecord.minecord.discord;

import com.google.gson.JsonObject;
import net.arikia.dev.drpc.callbacks.JoinGameCallback;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.messaging.PacketMinecordOutEvent;

@SideOnly(Side.CLIENT)
public class JoinGameEvent implements JoinGameCallback {

    @Override
    public void apply(String joinSecret) {
        JsonObject payload = new JsonObject();
        payload.addProperty("joinSecret", joinSecret);
        PacketMinecordOutEvent packet = new PacketMinecordOutEvent(payload, PacketMinecordOutEvent.EventType.JOINGAME_EVENT);
        Minecord.INSTANCE.packetHandler.sendEventMessage(packet);
        System.out.println("JoinGameEvent was fired, message dispatched. Message: " + packet.getJson().toString());
    }
}
