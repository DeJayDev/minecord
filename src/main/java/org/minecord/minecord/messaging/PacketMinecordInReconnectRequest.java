package org.minecord.minecord.messaging;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.minecord.minecord.Minecord;

public class PacketMinecordInReconnectRequest implements IMessage{

    public void fromBytes(ByteBuf buf) {}

    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketMinecordInReconnectRequest, IMessage>{

        @Override
        public IMessage onMessage(PacketMinecordInReconnectRequest message, MessageContext ctx) {
            if(Minecord.INSTANCE.connection.checkConnectionServer())
               Minecord.INSTANCE.connection.disconnect();

            Minecord.INSTANCE.packetHandler.sendInitMessage(new PacketMinecordOutConnectRequest(Minecord.INSTANCE.profile.getUuid(), Minecord.VERSION));
            return null;
        }
    }
}
