package org.minecord.minecord.messaging;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.MinecordConfig;
import org.minecord.minecord.MinecordToast;

import javax.annotation.Nullable;
import java.nio.charset.Charset;

@SideOnly(Side.CLIENT)
public class PacketMinecordOutEvent implements IMessage{

    private JsonObject json;
    private EventType type;

    public PacketMinecordOutEvent() {}

    public PacketMinecordOutEvent(@Nullable JsonObject payload, EventType type){
        this.type = type;
        json = new JsonObject();
        json.addProperty("eventType", type.toString());

        if(payload != null)
            json.add("payload", payload);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if(MinecordConfig.allowToasts)
            Minecraft.getMinecraft().getToastGui().add(new MinecordToast(MinecordToast.Icons.CONNECT_SUCCESS, new TextComponentString("Event dispatched!"), new TextComponentString("Type: " + type.toString())));
        if(json != null)
            buf.writeBytes(json.toString().getBytes(Charset.forName("UTF-8")));
    }

    //NOT NEEDED; OUT ONLY!
    @Override
    public void fromBytes(ByteBuf buf){}

    public JsonObject getJson() {
        return json;
    }

    //NOT NEEDED; OUT ONLY!
    public static class Handler implements IMessageHandler<PacketMinecordOutEvent, IMessage>{

        @Override
        public IMessage onMessage(PacketMinecordOutEvent message, MessageContext ctx) {
            return null;
        }
    }

    public enum EventType{

        READY_EVENT,
        ERRORED_EVENT,
        JOINGAME_EVENT,
        JOINREQUEST_EVENT,
        SPECTATEGAME_EVENT,
        DISCONNECTED_EVENT
    }
}
