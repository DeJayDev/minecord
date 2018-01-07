package org.minecord.minecord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.messaging.JoinConnectMessage;
import org.minecord.minecord.messaging.JoinConnectResponse;

import java.util.UUID;

@Mod(modid = Minecord.MODID, version = Minecord.VERSION, useMetadata = true)
@SideOnly(Side.CLIENT)
public class Minecord {

    @Mod.Instance("minecord")
    public static Minecord INSTANCE;

    public static final String MODID = "minecord";
    public static final String VERSION = "0.1-ALPHA.0";
    //public final UUID HYPIXELAPI = UUID.fromString("ca5a9eac-3456-4ffe-8482-4fa430498b97");

    DiscordEventHandlers discord = new DiscordEventHandlers();

    public static SimpleNetworkWrapper CHANNEL_INIT;
    public static SimpleNetworkWrapper CHANNEL_PRESENCE;

    public static UUID UUID;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e){
        DiscordRPC.DiscordInitialize("378408502181363713", discord, true);
        CHANNEL_INIT = NetworkRegistry.INSTANCE.newSimpleChannel("MINECORD|INIT");
        CHANNEL_PRESENCE = NetworkRegistry.INSTANCE.newSimpleChannel("MINECORD|RP");

        CHANNEL_INIT.registerMessage(JoinConnectMessage.Handler.class, JoinConnectMessage.class, 0, Side.CLIENT);
        CHANNEL_INIT.registerMessage(JoinConnectResponse.Handler.class, JoinConnectResponse.class, 1, Side.CLIENT);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new Events()) ;
        DiscordRPC.DiscordInitialize("378408502181363713", discord,true);
        discord.ready = new ReadyClass();
        DiscordRichPresence rich = new DiscordRichPresence();
        rich.details = "Playing Minecraft!";
        rich.largeImageKey = "grass";
        rich.largeImageText = "Idling";
        rich.smallImageKey = "vatuu";
        Double partyVal = Math.random() * 10;
        rich.partyId = "minecord-" + partyVal.toString();
        rich.partyMax = 10;
        rich.partySize = 1;
        rich.joinSecret = "minecord-join";
        rich.matchSecret = "minecord-match";
        rich.state = null;
        rich.instance = 1;
        DiscordRPC.DiscordUpdatePresence(rich);
    }

    public static class Events{

        @SubscribeEvent
        public void loggedIn(EntityJoinWorldEvent e){
            if(!(e.getEntity() instanceof EntityPlayer))
                return;

            EntityPlayer player = (EntityPlayer)e.getEntity();
            UUID = player.getUniqueID();

            try {
                Minecord.CHANNEL_INIT.sendToServer(new JoinConnectMessage(UUID, VERSION));
                System.out.println("Send message to server. UUID: '" + UUID + "' Version: '" + VERSION + "'");
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }


    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        CommandHandler commandManager = (CommandHandler) event.getServer().getCommandManager();
        commandManager.registerCommand(new MinecordCommand());
        System.out.println("Registered command.");
    }

    public void registerPresenceMessage(int discriminator){
    }
}