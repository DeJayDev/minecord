package org.minecord.minecord;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import net.minecraft.entity.player.EntityPlayer;

public class MinecordCommand extends CommandBase{

    @Override
    public String getCommandName() {
        return "minecord";
    }

    @Override
    public String getCommandUsage(ICommandSender sender){
        return "Send Minecord Data?";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args){
        if(sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;

            player.addChatMessage(new ChatComponentText("Sent Discord a Detail of: " + rich.state));
            player.addChatMessage(new ChatComponentText("Check console for the raw sauce"));
            System.out.println(rich);

        }
    }

}
