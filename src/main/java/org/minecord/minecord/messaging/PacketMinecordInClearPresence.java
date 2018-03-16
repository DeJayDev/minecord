package org.minecord.minecord.messaging;

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

@SideOnly(Side.CLIENT)
public class PacketMinecordInClearPresence implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf){}

    public static class Handler implements IMessageHandler<PacketMinecordInClearPresence, IMessage>{

        @Override
        public IMessage onMessage(PacketMinecordInClearPresence message, MessageContext ctx) {
            if(!Minecord.INSTANCE.isConnected)
                return null;
            System.out.println("MINECORD|RP - Received Presence Clearance!");

            if(Minecord.INSTANCE.getConfigHandler().getGeneral().isEnableToasts())
                Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.YOU_WIN, new TextComponentString("Presence cleared!"), null));

            Minecord.INSTANCE.discordUtil.clearPresence();
            return null;
        }
    }
}
