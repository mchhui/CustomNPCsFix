package mchhui.customnpcsfix.command;

import java.io.File;
import java.io.IOException;
import java.util.List;

import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.CustomNPCsFix;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.util.JsonException;
import noppes.npcs.util.NBTJsonUtil;

public class CommandHueihuea extends CommandBase {

    @Override
    public String getCommandName() {
        return "hueihuea";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        // TODO 自动生成的方法存根
        return "/hueihuea help";
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        switch (args.length) {
        case 1:
            return this.getListOfStringsMatchingLastWord(args, "help", "reload", "version");
        case 2:
            return this.getListOfStringsMatchingLastWord(args, "reserialize");
        case 3:
            return this.getListOfStringsMatchingLastWord(args, "playerdata");
        }
        return super.addTabCompletionOptions(sender, args);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            args = new String[] { "help" };
        }
        if (args.length == 1) {
            if (args[0].equals("help")) {
                sender.addChatMessage(new ChatComponentText("/hueihuea reserialize playerdata 把所有playerdata重新序列化"));
                sender.addChatMessage(new ChatComponentText("/hueihuea reload 重载配置"));
                return;
            }
            if (args[0].equals("reload")) {
                Config.reload();
                sender.addChatMessage(new ChatComponentText("已重载配置"));
                return;
            }
            if (args[0].equals("version")) {
                sender.addChatMessage(new ChatComponentText("CustomNPCsFix " + CustomNPCsFix.VERSION));
                sender.addChatMessage(new ChatComponentText("BY. HUEIHUEA"));
                return;
            }
        }
        if (args.length == 2) {
            if (args[0].equals("reserialize") && args[1].equals("playerdata")) {
                File[] files = PlayerDataController.instance.getSaveDir().listFiles();
                for (File file : files) {
                    if (!file.isDirectory() && file.getName().endsWith(".json")) {
                        try {
                            NBTJsonUtil.SaveFile(file, NBTJsonUtil.LoadFile(file));
                        } catch (IOException | JsonException e) {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }
                    }
                }
                sender.addChatMessage(new ChatComponentText("已重新序列化玩家档案"));
                return;
            }
        }
    }

}
