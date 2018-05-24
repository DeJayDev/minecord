package org.minecord.minecord.discord;

import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.minecord.minecord.Minecord;
import org.minecord.minecord.MinecordProfile;
import org.minecord.minecord.messaging.PacketMinecordOutEvent;

@SideOnly(Side.CLIENT)
public class ReadyEvent implements ReadyCallback {

    @Override
    public void apply(DiscordUser user){
        PacketMinecordOutEvent packet = new PacketMinecordOutEvent(null, PacketMinecordOutEvent.EventType.READY_EVENT);
        Minecord.INSTANCE.packetHandler.sendEventMessage(packet);
        Minecord.INSTANCE.connection.setDiscord(true);
        System.out.println("ReadyEvent was fired, message dispatched. Message: " + packet.getJson().toString() + " | Username: " + user.username);

        MinecordProfile.DiscordUser discord = new MinecordProfile.DiscordUser(user.userId, user.discriminator, user.username, user.avatar);
        Minecord.INSTANCE.profile.setDiscordInformation(discord);
    }
}
