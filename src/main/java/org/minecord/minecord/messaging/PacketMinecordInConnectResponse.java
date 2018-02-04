package org.minecord.minecord.messaging;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.MinecordConfig;
import org.minecord.minecord.gui.GuiMinecordToast;

import java.nio.charset.Charset;

@SideOnly(Side.CLIENT)
public class PacketMinecordInConnectResponse implements IMessage {

    private boolean success;

    public PacketMinecordInConnectResponse() {}


    @Override
    public void fromBytes(ByteBuf buf){
        byte[] messageBytes = new byte[buf.capacity()];
        buf.getBytes(0, messageBytes);
        String message = new String(messageBytes, Charset.forName("UTF-8"));
        System.out.println("Message: " + message);
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        success = json.get("success").getAsBoolean();
        Minecord.INSTANCE.packetHandler.setDiscriminator(json.get("discriminator").getAsInt());
    }

    //NOT NEEDED; RECEIVE ONLY!
    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketMinecordInConnectResponse, IMessage> {

        @Override
        public IMessage onMessage(PacketMinecordInConnectResponse response, MessageContext ctx) {
            if(!response.success){
                if(MinecordConfig.allowToasts)
                Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.CONNECT_FAILURE, new TextComponentString("Connection failure!"), null));
            }else{
                Minecord.INSTANCE.packetHandler.registerPresenceMessage();
                if(MinecordConfig.allowToasts)
                    Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.CONNECT_SUCCESS, new TextComponentString("Successfully connected!"), new TextComponentString("Discriminator: " + Minecord.INSTANCE.packetHandler.getDiscriminator())));
            }
            return null;
        }
    }
}
