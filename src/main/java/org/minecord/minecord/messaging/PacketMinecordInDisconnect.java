package org.minecord.minecord.messaging;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.minecord.minecord.Minecord;

public class PacketMinecordInDisconnect implements IMessage {

    public void fromBytes(ByteBuf buf) {}

    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketMinecordInDisconnect, IMessage>{

        public IMessage onMessage(PacketMinecordInDisconnect message, MessageContext ctx) {
            if (Minecord.INSTANCE.connection.checkConnectionServer()) {
                Minecord.INSTANCE.connection.disconnect();
            }
            return null;
        }
    }
}
