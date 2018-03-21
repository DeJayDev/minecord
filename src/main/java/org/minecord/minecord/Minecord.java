package org.minecord.minecord;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
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

import java.io.File;

@SideOnly(Side.CLIENT)
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

    @EventHandler
    public void preInit(FMLPreInitializationEvent e){
        connection = new ConnectionHandler();
        packetHandler = new PacketHandler();
        discordUtil = new DiscordUtil();
        config = new ConfigHandler(new File(e.getModConfigurationDirectory(), "Minecord.json"));
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new Events());
        connection.setOfflinePresence(discordUtil.assembleOfflinePresence(false));
        connection.updateOfflinePresence(false);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Finalizing ConfigHandler. Saving contents to '" + Minecord.INSTANCE.config.getConfigFile().getName() + "'.");
            Minecord.INSTANCE.config.serialize();
        }));

        KeyBindings.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        checkCosmetics();
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
                    Minecord.INSTANCE.connection.setConnectedIp(currentServer.serverIP);
                }else{
                    Minecord.INSTANCE.connection.setConnectedIp("");
                }
            }else{
                Minecord.INSTANCE.connection.setConnectedIp("");
            }

            Multithreading.runAsync(() -> {
                while (Minecraft.getMinecraft().player == null) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                Minecord.INSTANCE.profile = MinecordProfile.create(Minecraft.getMinecraft().player.getUniqueID());
                Minecord.INSTANCE.packetHandler.sendInitMessage(new PacketMinecordOutConnectRequest(Minecord.INSTANCE.profile.getUuid(), VERSION));
            });

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
                Minecraft.getMinecraft().displayGuiScreen(new GuiMinecordConfigPresence(null));
            }else if(KeyBindings.openMinecordRegister.isPressed()){
                Minecraft.getMinecraft().displayGuiScreen(new GuiMinecordConfigGeneral());
            }
        }
    }

    public static class KeyBindings{

        public static KeyBinding openMinecordConfig;
        public static KeyBinding openMinecordRegister;

        public static void init(){
            openMinecordConfig = new KeyBinding("Open Minecord Config", Keyboard.KEY_M, "Minecord");
            openMinecordRegister = new KeyBinding("Open Minecord Registration", Keyboard.KEY_N, "Minecord");

            ClientRegistry.registerKeyBinding(openMinecordConfig);
            ClientRegistry.registerKeyBinding(openMinecordRegister);
        }
    }
}