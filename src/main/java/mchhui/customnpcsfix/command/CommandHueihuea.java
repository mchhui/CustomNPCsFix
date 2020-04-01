package mchhui.customnpcsfix.command;

import java.io.File;
import java.io.IOException;
import java.util.List;

import mchhui.customnpcsfix.Config;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.util.NBTJsonUtil;

public class CommandHueihuea extends CommandBase {

    @Override
    public String getName() {
        return "hueihuea";
    }

    @Override
    public String getUsage(ICommandSender p_71518_1_) {
        // TODO 自动生成的方法存根
        return "/hueihuea help";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
            BlockPos targetPos) {
        switch (args.length) {
        case 1:
            return this.getListOfStringsMatchingLastWord(args, "help","reload");
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            args = new String[] { "help" };
        }
        if (args.length == 1) {
            if (args[0].equals("help")) {
                sender.sendMessage(new TextComponentString("/hueihuea reload 重载配置"));
                return;
            }
            if(args[0].equals("reload")) {
                Config.reload();
                sender.sendMessage(new TextComponentString("已重载配置"));
                return;
            }
        }
    }

}
