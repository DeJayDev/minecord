package org.minecord.minecord.messaging;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.Charset;
import java.util.UUID;

public class    PacketMinecordOutConnectRequest implements IMessage {

    private String text;

    public PacketMinecordOutConnectRequest() {}

    public PacketMinecordOutConnectRequest(UUID uuid, String version){
        JsonObject json = new JsonObject();
        json.addProperty("version", version);
        json.addProperty("uuid", uuid.toString());
        text = json.toString();
    }

    //NOT NEEDED; OUT ONLY!
    @Override
    public void fromBytes(ByteBuf buf){}

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(text.getBytes(Charset.forName("UTF-8")));
    }

    //NOT NEEDED; OUT ONLY!
    public static class Handler implements IMessageHandler<PacketMinecordOutConnectRequest, IMessage>{

        @Override
        public IMessage onMessage(PacketMinecordOutConnectRequest message, MessageContext ctx) {
            System.out.println("Send connection request.");
            return null;
        }
    }
}
