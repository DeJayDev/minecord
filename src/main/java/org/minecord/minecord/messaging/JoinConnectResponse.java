package org.minecord.minecord.messaging;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;

import java.nio.charset.Charset;

@SideOnly(Side.CLIENT)
public class JoinConnectResponse implements IMessage {

    private boolean success;
    private int discriminator;

    public JoinConnectResponse(boolean success, int discriminator){
        this.success = success;
        this.discriminator = discriminator;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        JsonObject json = new JsonParser().parse(buf.readCharSequence(buf.array().length, Charset.forName("UTF-8")).toString()).getAsJsonObject();
        success = json.get("success").getAsBoolean();
        discriminator = json.get("discriminator").getAsInt();
    }

    //NOT NEEDED; RECEIVE ONLY!
    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<JoinConnectResponse, IMessage> {

        @Override
        public IMessage onMessage(JoinConnectResponse response, MessageContext ctx) {
            if(!response.success){
                System.out.println("Server denied Minecord Connection!");
            }else{
                Minecord.INSTANCE.registerPresenceMessage(response.discriminator);
                System.out.println("Server accepted Minecord Connection with discriminator " + response.discriminator + ".");
            }
            return null;
        }
    }
}
