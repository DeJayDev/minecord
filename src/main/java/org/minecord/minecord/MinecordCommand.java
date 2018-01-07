package org.minecord.minecord;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MinecordCommand extends CommandBase{

    @Override
    public String getName() {
        return "minecord";
    }

    @Override
    public String getUsage(ICommandSender e) { return ""; }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
    }
}
