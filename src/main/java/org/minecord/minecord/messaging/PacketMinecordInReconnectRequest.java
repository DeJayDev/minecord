package org.minecord.minecord.messaging;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.MinecordConfig;

public class PacketMinecordInReconnectRequest implements IMessage{

    public void fromBytes(ByteBuf buf) {}

    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketMinecordInReconnectRequest, IMessage>{

        @Override
        public IMessage onMessage(PacketMinecordInReconnectRequest message, MessageContext ctx) {
            if(Minecord.INSTANCE.isConnected)
               Minecord.INSTANCE.disconnect();

            Minecord.INSTANCE.packetHandler.sendInitMessage(new PacketMinecordOutConnectRequest(Minecord.UUID, Minecord.VERSION));
            if(MinecordConfig.offline.offlinePresenceEnabled){
                Minecord.INSTANCE.discordUtil.updatePresence(Minecord.INSTANCE.offlinePresence);
                System.out.println("Setting offline presence.");
            }
            return null;
        }
    }
}
