package org.minecord.minecord.messaging;

import com.google.gson.*;
import io.netty.buffer.ByteBuf;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.MinecordConfig;
import org.minecord.minecord.MinecordToast;

import java.nio.charset.Charset;

@SideOnly(Side.CLIENT)
public class PacketMinecordInClearPresence implements IMessage {

    private int discriminator;

    public PacketMinecordInClearPresence() {}

    public int getDiscriminator() {
        return discriminator;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        discriminator = Minecord.INSTANCE.packetHandler.getDiscriminator();
    }

    @Override
    public void toBytes(ByteBuf buf){}

    public static class Handler implements IMessageHandler<PacketMinecordInClearPresence, IMessage>{

        @Override
        public IMessage onMessage(PacketMinecordInClearPresence message, MessageContext ctx) {
            System.out.println("MINECORD|RP [" + message.getDiscriminator() + "] - Received Presence Clearance!");

            if(MinecordConfig.allowToasts)
                Minecraft.getMinecraft().getToastGui().add(new MinecordToast(MinecordToast.Icons.YOU_WIN, new TextComponentString("Presence cleared!"), null));

            Minecord.INSTANCE.discordUtil.clearPresence();
            return null;
        }
    }
}
