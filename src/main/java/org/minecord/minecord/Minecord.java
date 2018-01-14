package org.minecord.minecord;

import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.minecord.minecord.discord.DiscordUtil;
import org.minecord.minecord.discord.ReadyClass;
import org.minecord.minecord.messaging.PacketHandler;
import org.minecord.minecord.messaging.PacketMinecordOutConnectRequest;
;
import java.util.UUID;

@Mod(modid = Minecord.MODID, version = Minecord.VERSION, useMetadata = true)
public class Minecord {

    @Mod.Instance("minecord")
    public static Minecord INSTANCE;

    public static final String MODID = "minecord";
    public static final String VERSION = "0.1-ALPHA.0";
    //public final UUID HYPIXELAPI = UUID.fromString("ca5a9eac-3456-4ffe-8482-4fa430498b97");

    public PacketHandler packetHandler;
    public DiscordUtil discordUtil;

    public static UUID UUID;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e){
        packetHandler = new PacketHandler();
        discordUtil = new DiscordUtil();

        discordUtil.eventHandler.ready = new ReadyClass();
        discordUtil.initializeDiscord();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new Events()) ;
    }

    public static class Events{

        @SubscribeEvent
        public void loggedIn(EntityJoinWorldEvent e){
            if(!(e.getEntity() instanceof EntityPlayer))
                return;

            UUID = e.getEntity().getUniqueID();

            Minecord.INSTANCE.packetHandler.sendInitMessage(new PacketMinecordOutConnectRequest(UUID, VERSION));
        }
    }
}