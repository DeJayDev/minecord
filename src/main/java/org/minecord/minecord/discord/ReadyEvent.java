package org.minecord.minecord.discord;

import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.messaging.PacketMinecordOutEvent;

@SideOnly(Side.CLIENT)
public class ReadyEvent implements ReadyCallback {

    @Override
    public void apply(){
        PacketMinecordOutEvent packet = new PacketMinecordOutEvent(null, PacketMinecordOutEvent.EventType.READY_EVENT);
        Minecord.INSTANCE.packetHandler.sendEventMessage(packet);
        Minecord.INSTANCE.connection.setDiscord(true);
        System.out.println("ReadyEvent was fired, message dispatched. Message: " + packet.getJson().toString());
    }
}
