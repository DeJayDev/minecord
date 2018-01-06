package org.minecord.minecord;

import net.minecraft.command.CommandHandler;
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
import org.minecord.minecord.messaging.TestMessage;

import java.util.UUID;

@Mod(modid = Minecord.MODID, version = Minecord.VERSION, useMetadata = true)
public class Minecord
{
    public static final String MODID = "minecord";
    public static final String VERSION = "1.0";
    public final UUID HYPIXELAPI = UUID.fromString("ca5a9eac-3456-4ffe-8482-4fa430498b97");

    //DiscordEventHandlers discord = new DiscordEventHandlers();

    public static SimpleNetworkWrapper network;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e){
        SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("Channel");
        network.registerMessage(TestMessage.Handler.class, TestMessage.class, 0, Side.CLIENT);
        System.out.println("Channel registered as CLIENT Side with 0");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        /*DiscordRPC.DiscordInitialize("378408502181363713", discord,true);
        discord.ready = new ReadyClass();
        DiscordRichPresence rich = new DiscordRichPresence();
        rich.details = "Playing Minecraft!";
        rich.largeImageKey = "grass";
        rich.largeImageText = "Idling";
        rich.smallImageKey = "dejay";
        Double partyVal = Math.random() * 10;
        rich.partyId = "minecord-" + partyVal.toString();
        rich.partyMax = 10;
        rich.partySize = 1;
        rich.joinSecret = "minecord-join";
        rich.matchSecret = "minecord-match";
        rich.state = null;
        rich.instance = 1;
        DiscordRPC.DiscordUpdatePresence(rich);*/
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        CommandHandler commandManager = (CommandHandler) event.getServer().getCommandManager();
        commandManager.registerCommand(new MinecordCommand());
    }

    //Literally stolen from Semx11's HypixelListener in Autotip.
    @SubscribeEvent
    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {

    }


}