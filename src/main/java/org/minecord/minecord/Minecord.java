package org.minecord.minecord;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;
import org.minecord.minecord.config.ConfigHandler;
import org.minecord.minecord.discord.*;
import org.minecord.minecord.gui.GuiMinecordConfig;
import org.minecord.minecord.gui.GuiMinecordToast;
import org.minecord.minecord.messaging.PacketHandler;
import org.minecord.minecord.messaging.PacketMinecordOutConnectRequest;
import org.minecord.minecord.offline.OfflinePresenceHandler;

import java.io.File;

@Mod(modid = Minecord.MODID, version = Minecord.VERSION, useMetadata = true, canBeDeactivated = true, clientSideOnly = true)
public class Minecord {

    @Mod.Instance("minecord")
    public static Minecord INSTANCE;

    public static final String MODID = "minecord";
    public static final String VERSION = "0.1";

    public MinecordProfile profile;
    public ConnectionHandler connection;
    public PacketHandler packetHandler;
    public ConfigHandler config;
    public DiscordUtil discordUtil;
    public OfflinePresenceHandler presenceHandler;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e){
        connection = new ConnectionHandler();
        packetHandler = new PacketHandler();
        discordUtil = new DiscordUtil();
        presenceHandler = new OfflinePresenceHandler();
        config = new ConfigHandler(new File(e.getModConfigurationDirectory(), "Minecord.json"));
        profile = MinecordProfile.create(Minecraft.getMinecraft().getSession().getProfile().getId());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new Events());
        connection.setOfflinePresence(presenceHandler.assembleOfflinePresence(false));
        connection.updateOfflinePresence(false);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Finalizing ConfigHandler. Saving contents to '" + Minecord.INSTANCE.config.getConfigFile().getName() + "'.");
            Minecord.INSTANCE.config.serialize();
        }));

        KeyBindings.init();
    }

    public static class Events{

        @SubscribeEvent
        public void joinedServer(FMLNetworkEvent.ClientConnectedToServerEvent e){
            ServerData currentServer = Minecraft.getMinecraft().getCurrentServerData();
            if(currentServer != null){
                if(currentServer.serverIP != null){
                    Minecord.INSTANCE.connection.setConnectedIp(currentServer.serverIP);
                }else{
                    Minecord.INSTANCE.connection.setConnectedIp("");
                }
            }else{
                Minecord.INSTANCE.connection.setConnectedIp("");
            }

            Minecord.INSTANCE.packetHandler.sendInitMessage(new PacketMinecordOutConnectRequest(Minecord.INSTANCE.profile.getUuid(), VERSION));
            Minecord.INSTANCE.connection.updateOfflinePresence(true);
            //TODO Analytics
        }

        @SubscribeEvent
        public void onPlayerLogOutEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
            Minecord.INSTANCE.connection.setConnected(false);
            if(Minecord.INSTANCE.config.getGeneral().isEnableToasts())
                Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.CONNECT_FAILURE, new TextComponentString("Disconnected!"), new TextComponentString("Terminated by server.")));
            Minecord.INSTANCE.connection.updateOfflinePresence(false);
        }

        @SubscribeEvent
        public void onKeyInput(InputEvent.KeyInputEvent e){
            if(KeyBindings.openMinecordConfig.isPressed()){
                Minecraft.getMinecraft().displayGuiScreen(new GuiMinecordConfig(null));
            }
        }
    }

    public static class KeyBindings{

        public static KeyBinding openMinecordConfig;

        public static void init(){
            openMinecordConfig = new KeyBinding("Open Minecord Config", Keyboard.KEY_M, I18n.format("key.categories.minecord"));

            ClientRegistry.registerKeyBinding(openMinecordConfig);
        }
    }
}