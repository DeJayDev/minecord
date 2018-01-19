package org.minecord.minecord;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.discord.*;
import org.minecord.minecord.messaging.PacketHandler;
import org.minecord.minecord.messaging.PacketMinecordOutConnectRequest;

import java.util.UUID;

@SideOnly(Side.CLIENT)
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

        discordUtil.eventHandler.ready = new ReadyEvent();
        discordUtil.eventHandler.disconnected = new DisconnectedEvent();
        discordUtil.eventHandler.errored = new ErroredEvent();
        discordUtil.eventHandler.joinGame = new JoinGameEvent();
        discordUtil.eventHandler.joinRequest = new JoinRequestEvent();
        discordUtil.eventHandler.spectateGame = new SpectateGameEvent();
        discordUtil.initializeDiscord();

        ConfigManager.getModConfigClasses(MODID);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new Events()) ;
        discordUtil.initializeDiscord();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e){
        discordUtil.initializeDiscord();
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