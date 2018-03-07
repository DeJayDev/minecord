package org.minecord.minecord;

import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.discord.*;
import org.minecord.minecord.gui.GuiMinecordToast;
import org.minecord.minecord.messaging.PacketHandler;
import org.minecord.minecord.messaging.PacketMinecordOutConnectRequest;

import java.util.UUID;

@SideOnly(Side.CLIENT)
@Mod(modid = Minecord.MODID, version = Minecord.VERSION, useMetadata = true, canBeDeactivated = true, clientSideOnly = true)
public class Minecord {

    @Mod.Instance("minecord")
    public static Minecord INSTANCE;

    public static final String MODID = "minecord";
    public static final String VERSION = "0.1";

    public static UUID UUID;

    public PacketHandler packetHandler;
    public DiscordUtil discordUtil;
    public DiscordRichPresence offlinePresence;

    public boolean isConnected = false;

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
        discordUtil.runCallbackTask();

        ConfigManager.load(MODID, Config.Type.INSTANCE);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new Events());
        offlinePresence = discordUtil.assembleOfflinePresence();
    }

    public void updateOfflinePresence(){
        offlinePresence = discordUtil.assembleOfflinePresence();
        discordUtil.updatePresence(offlinePresence);
        System.out.println("Updated offline presence.");
    }

    public void disconnect(){
        isConnected = false;
        if(MinecordConfig.general.allowToasts)
            Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.CONNECT_FAILURE, new TextComponentString("Disconnected!"), new TextComponentString("Terminated by server.")));
    }

    public static class Events{

        @SubscribeEvent
        public void loggedIn(EntityJoinWorldEvent e){
            if(!(e.getEntity() instanceof EntityPlayer))
                return;

            UUID = e.getEntity().getUniqueID();

            Minecord.INSTANCE.packetHandler.sendInitMessage(new PacketMinecordOutConnectRequest(UUID, VERSION));
            if(MinecordConfig.offline.offlinePresenceEnabled){
                Minecord.INSTANCE.discordUtil.updatePresence(Minecord.INSTANCE.offlinePresence);
                System.out.println("Setting offline presence.");
            }
        }

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
            if (e.getModID().equals(Minecord.MODID)) {
                ConfigManager.sync(Minecord.MODID, Config.Type.INSTANCE);
            }
        }

        @SubscribeEvent
        public static void postConfigChanged(ConfigChangedEvent.PostConfigChangedEvent e){
            if(e.getModID().equals(Minecord.MODID)){
                System.out.println("Updated offline presence.");
                Minecord.INSTANCE.updateOfflinePresence();
            }
        }
    }
}