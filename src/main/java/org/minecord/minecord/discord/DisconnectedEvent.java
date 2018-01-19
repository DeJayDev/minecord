package org.minecord.minecord.discord;

import com.google.gson.JsonObject;
import net.arikia.dev.drpc.callbacks.DisconnectedCallback;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.messaging.PacketMinecordOutEvent;

@SideOnly(Side.CLIENT)
public class DisconnectedEvent implements DisconnectedCallback {

    @Override
    public void apply(int code, String message){
        JsonObject payload = new JsonObject();
        payload.addProperty("errorCode", code);
        payload.addProperty("errorMessage", message);
        PacketMinecordOutEvent packet = new PacketMinecordOutEvent(payload, PacketMinecordOutEvent.EventType.DISCONNECTED_EVENT);
        Minecord.INSTANCE.packetHandler.sendEventMessage(packet);
        System.out.println("DisconnectedEvent was fired, message dispatched. Message: " + packet.getJson().toString());
    }
}
