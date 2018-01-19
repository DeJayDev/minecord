package org.minecord.minecord.discord;

import com.google.gson.JsonObject;
import net.arikia.dev.drpc.callbacks.SpectateGameCallback;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.messaging.PacketMinecordOutEvent;

@SideOnly(Side.CLIENT)
public class SpectateGameEvent implements SpectateGameCallback {

    @Override
    public void apply(String spectateSecret){
        JsonObject payload = new JsonObject();
        payload.addProperty("spectateSecret", spectateSecret);
        PacketMinecordOutEvent packet = new PacketMinecordOutEvent(payload, PacketMinecordOutEvent.EventType.SPECTATEGAME_EVENT);
        Minecord.INSTANCE.packetHandler.sendEventMessage(packet);
        System.out.println("SpectateGameEvent fired, message dispatched. Message: " + packet.getJson().toString());
    }
}
