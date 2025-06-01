 package mchhui.customnpcsfix.command;

import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import noppes.npcs.EventHooks;
import noppes.npcs.Server;
import noppes.npcs.command.CmdQuest;
import noppes.npcs.constants.EnumPacketClient;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.QuestData;
/**
 * @see mchhui.customnpcsfix.proxy.CommonProxy.onServerStart
 * */
public class CmdQuestFix extends CmdQuest{
    @SubCommand(desc = "Start a quest", usage = "<player> <quest>", permission = 2)
    public void start(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
      int questid;
      String playername = args[0];
      try {
        questid = Integer.parseInt(args[1]);
      } catch (NumberFormatException ex) {
        throw new CommandException("QuestID must be an integer", new Object[0]);
      } 
      List<PlayerData> data = PlayerDataController.instance.getPlayersData(sender, playername);
      if (data.isEmpty())
        throw new CommandException("Unknow player '%s'", new Object[] { playername }); 
      Quest quest = QuestController.instance.quests.get(Integer.valueOf(questid));
      if (quest == null)
        throw new CommandException("Unknown QuestID", new Object[0]); 
      for (PlayerData playerdata : data) {
        QuestData questdata = new QuestData(quest);
        playerdata.questData.activeQuests.put(Integer.valueOf(questid), questdata);
        EventHooks.onQuestStarted(playerdata.scriptData, quest);
        playerdata.save(true);
        Server.sendData((EntityPlayerMP)playerdata.player, EnumPacketClient.MESSAGE, new Object[] { "quest.newquest", quest.title, Integer.valueOf(2) });
        Server.sendData((EntityPlayerMP)playerdata.player, EnumPacketClient.CHAT, new Object[] { "quest.newquest", ": ", quest.title });
      } 
    }
}
