package org.minecord.minecord;

import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.minecord.minecord.config.ConfigHandler;
import org.minecord.minecord.discord.*;
import org.minecord.minecord.gui.GuiMinecordConfigGeneral;
import org.minecord.minecord.gui.GuiMinecordConfigPresence;
import org.minecord.minecord.gui.GuiMinecordToast;
import org.minecord.minecord.messaging.PacketHandler;
import org.minecord.minecord.messaging.PacketMinecordOutConnectRequest;
import org.minecord.minecord.utils.CapeUtil;
import org.minecord.minecord.utils.Multithreading;
import scala.collection.parallel.ParIterableLike;

import javax.swing.text.JTextComponent;
import java.io.File;
import java.util.UUID;

@SideOnly(Side.CLIENT)
@Mod(modid = Minecord.MODID, version = Minecord.VERSION, useMetadata = true, canBeDeactivated = true, clientSideOnly = true)
public class Minecord {

    @Mod.Instance("minecord")
    public static Minecord INSTANCE;

    public static final String MODID = "minecord";
    public static final String VERSION = "0.1";

    public static UUID UUID;

    public boolean isConnected = false;
    public PacketHandler packetHandler;
    public DiscordUtil discordUtil;
    public String connectedIp = "";


    private DiscordRichPresence offlinePresence;
    private ConfigHandler config;

    public ConfigHandler getConfigHandler(){
        return config;
    }

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

        config = new ConfigHandler(new File(e.getModConfigurationDirectory(), "Minecord.json"));
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new Events());
        offlinePresence = discordUtil.assembleOfflinePresence(false);
        updateOfflinePresence(false);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("Finalizing ConfigHandler. Saving contents to '" + Minecord.INSTANCE.getConfigHandler().getConfigFile().getName() + "'.");
                Minecord.INSTANCE.getConfigHandler().serialize();
            }
        });

        KeyBindings.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        checkCosmetics();
    }

    public void updateOfflinePresence(boolean ingame){
        if(config.getGeneral().isEnableToasts()) {
            offlinePresence = discordUtil.assembleOfflinePresence(ingame);
            discordUtil.updatePresence(offlinePresence);
            System.out.println("Updated offline presence.");
        }else{
            System.out.println("Offline Presence was disabled in the config.");
        }
    }

    public void disconnect(){
        isConnected = false;
        if(config.getGeneral().isEnableToasts())
            Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.CONNECT_FAILURE, new TextComponentString("Disconnected!"), new TextComponentString("Terminated by server.")));
        updateOfflinePresence(true);
    }

    private void checkCosmetics(){
        Minecraft.getMinecraft().gameSettings.setModelPartEnabled(EnumPlayerModelParts.CAPE, true);
        Minecraft.getMinecraft().getRenderManager().getSkinMap().forEach((s, p) -> {
            System.out.println(s);
            p.addLayer(new CapeUtil(p, CapeUtil.Players.VATUU));
        });
    }

    public static class Events{

        @SubscribeEvent
        public void joinedServer(FMLNetworkEvent.ClientConnectedToServerEvent e){
            ServerData currentServer = Minecraft.getMinecraft().getCurrentServerData();
            if(currentServer != null){
                if(currentServer.serverIP != null){
                    Minecord.INSTANCE.connectedIp = currentServer.serverIP;
                }else{
                    Minecord.INSTANCE.connectedIp = "";
                }
            }else{
                Minecord.INSTANCE.connectedIp = "";
            }

            Multithreading.runAsync(() -> {
                while (Minecraft.getMinecraft().player == null) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                UUID = Minecraft.getMinecraft().player.getUniqueID();
                Minecord.INSTANCE.packetHandler.sendInitMessage(new PacketMinecordOutConnectRequest(UUID, VERSION));
            });

            Minecord.INSTANCE.updateOfflinePresence(true);
            //TODO Analytics
        }

        @SubscribeEvent
        public void onPlayerLogOutEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
            Minecord.INSTANCE.isConnected = false;
            if(Minecord.INSTANCE.config.getGeneral().isEnableToasts())
                Minecraft.getMinecraft().getToastGui().add(new GuiMinecordToast(GuiMinecordToast.Icons.CONNECT_FAILURE, new TextComponentString("Disconnected!"), new TextComponentString("Terminated by server.")));
            Minecord.INSTANCE.updateOfflinePresence(false);
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
                Minecord.INSTANCE.updateOfflinePresence(true);
            }
        }

        @SubscribeEvent
        public void onKeyInput(InputEvent.KeyInputEvent e){
            if(KeyBindings.openMinecordConfig.isPressed()){
                Minecraft.getMinecraft().displayGuiScreen(new GuiMinecordConfigPresence());
            }
        }
    }

    public static class KeyBindings{
        public static KeyBinding openMinecordConfig;

        public static void init(){
            openMinecordConfig = new KeyBinding("Open Minecord Config", Keyboard.KEY_M, "key.categories.general");
            ClientRegistry.registerKeyBinding(openMinecordConfig);
        }
    }
}