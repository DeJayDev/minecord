package org.minecord.minecord.messaging;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.minecord.minecord.Minecord;

public class PacketHandler {

    private SimpleNetworkWrapper CHANNEL_INIT;
    private SimpleNetworkWrapper CHANNEL_PRESENCE;
    private SimpleNetworkWrapper CHANNEL_EVENT;

    public PacketHandler() {
        CHANNEL_INIT = NetworkRegistry.INSTANCE.newSimpleChannel("MINECORD|INIT");
        CHANNEL_PRESENCE = NetworkRegistry.INSTANCE.newSimpleChannel("MINECORD|RP");
        CHANNEL_EVENT = NetworkRegistry.INSTANCE.newSimpleChannel("MINECORD|EVENT");

        CHANNEL_INIT.registerMessage(PacketMinecordOutConnectRequest.Handler.class, PacketMinecordOutConnectRequest.class, 0, Side.CLIENT);
        CHANNEL_INIT.registerMessage(PacketMinecordInConnectResponse.Handler.class, PacketMinecordInConnectResponse.class, 1, Side.CLIENT);
        CHANNEL_EVENT.registerMessage(PacketMinecordOutEvent.Handler.class, PacketMinecordOutEvent.class, 0, Side.CLIENT);

        CHANNEL_PRESENCE.registerMessage(PacketMinecordInUpdatePresence.Handler.class, PacketMinecordInUpdatePresence.class, 0, Side.CLIENT);
        CHANNEL_PRESENCE.registerMessage(PacketMinecordInClearPresence.Handler.class, PacketMinecordInClearPresence.class, 1, Side.CLIENT);
        CHANNEL_PRESENCE.registerMessage(PacketMinecordInDisconnect.Handler.class, PacketMinecordInDisconnect.class, 2, Side.CLIENT);
        CHANNEL_PRESENCE.registerMessage(PacketMinecordInReconnectRequest.Handler.class, PacketMinecordInReconnectRequest.class, 3, Side.CLIENT);
    }


    public void sendInitMessage(PacketMinecordOutConnectRequest packet) {
        CHANNEL_INIT.sendToServer(packet);
    }

    public void sendEventMessage(PacketMinecordOutEvent packet) {
        if(Minecord.INSTANCE.isConnected)
            CHANNEL_EVENT.sendToServer(packet);
    }
}
