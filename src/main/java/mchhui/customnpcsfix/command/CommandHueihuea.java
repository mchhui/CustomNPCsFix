package mchhui.customnpcsfix.command;

import java.io.File;
import java.io.IOException;
import java.util.List;

import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.NetListener;
import mchhui.customnpcsfix.NetListener.ListenData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
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
            return this.getListOfStringsMatchingLastWord(args, "help", "reload", "listennet");
        }
        if (args.length >= 1) {
            if (args[0].equals("listennet")) {
                return this.getListOfStringsMatchingLastWord(args, "-from:", "-to:", "-prinstack", "-type:",
                        "-timeout:");
            }
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
                sender.sendMessage(new TextComponentString(
                        "/hueihuea listennet -from:<String> -to:<String> -type:<String> -timeout:<int> -printstack 监听NPC收发包活动"));
                sender.sendMessage(new TextComponentString("/hueihuea reload 重载配置"));
                return;
            }
            if (args[0].equals("reload")) {
                Config.reload();
                sender.sendMessage(new TextComponentString("已重载配置"));
                return;
            }
        }
        if (args.length >= 1) {
            if (args[0].equals("listennet")) {
                EntityPlayerMP player = null;
                if (!(sender instanceof EntityPlayerMP)) {
                    sender.sendMessage(new TextComponentString("该指令仅玩家使用"));
                    return;
                }
                player = (EntityPlayerMP) sender;
                boolean flag = NetListener.isListening(player);
                ListenData data = new ListenData();
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("-printstack")) {
                        data.printStack = true;
                    }
                    if (args[i].startsWith("-from:")) {
                        data.from = args[i].replaceFirst("-from:", "");
                    }
                    if (args[i].startsWith("-to:")) {
                        data.to = args[i].replaceFirst("-to:", "");
                    }
                    if (args[i].startsWith("-type:")) {
                        data.type = args[i].replaceFirst("-type:", "");
                    }
                    if (args[i].startsWith("-timeout:")) {
                        data.timeout = Integer.valueOf(args[i].replaceFirst("-timeout:", ""));
                    }
                }
                if (flag) {
                    sender.sendMessage(new TextComponentString("停止监听网络活动"));
                    NetListener.unListen(player);
                } else {
                    sender.sendMessage(new TextComponentString("开始监听网络活动"));
                    NetListener.listen(player, data);
                }
            }
        }
    }

}
