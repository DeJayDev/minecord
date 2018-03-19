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
import org.minecord.minecord.config.GeneralConfig;
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
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        success = json.get("success").getAsBoolean();
    }

    //NOT NEEDED; RECEIVE ONLY!
    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketMinecordInConnectResponse, IMessage> {

        @Override
        public IMessage onMessage(PacketMinecordInConnectResponse response, MessageContext ctx) {
            if(!response.success){
                if(Minecord.INSTANCE.config.getGeneral().isEnableToasts())
                Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.CONNECT_FAILURE, new TextComponentString("Connection failure!"), null));
            }else{
                if(Minecord.INSTANCE.config.getGeneral().isEnableToasts())
                    Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.CONNECT_SUCCESS, new TextComponentString("Successfully connected!"), null));
                Minecord.INSTANCE.connection.setConnected(true);
                Minecord.INSTANCE.discordUtil.clearPresence();
            }
            System.out.println("Message: " + response.success);
            return null;
        }
    }
}
