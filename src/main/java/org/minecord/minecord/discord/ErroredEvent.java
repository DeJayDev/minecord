package org.minecord.minecord.discord;

import com.google.gson.JsonObject;
import net.arikia.dev.drpc.callbacks.ErroredCallback;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.messaging.PacketMinecordOutEvent;

@SideOnly(Side.CLIENT)
public class ErroredEvent implements ErroredCallback {

    @Override
    public void apply(int code, String message) {
        JsonObject payload = new JsonObject();
        payload.addProperty("errorCode", code);
        payload.addProperty("errorMessage", message);
        PacketMinecordOutEvent packet = new PacketMinecordOutEvent(payload, PacketMinecordOutEvent.EventType.ERRORED_EVENT);
        Minecord.INSTANCE.packetHandler.sendEventMessage(packet);
        System.out.println("ErroredEvent was fired, message dispatched. Message: " + packet.getJson().toString());
    }
}
