package mchhui.customnpcsfix.listener;

import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.Server;
import mchhui.customnpcsfix.api.event.OnPlayerGetAllQuestWaypointEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.event.QuestEvent.QuestCompletedEvent;
import noppes.npcs.api.event.QuestEvent.QuestStartEvent;
import noppes.npcs.api.event.QuestEvent.QuestTurnedInEvent;
import noppes.npcs.api.handler.data.IQuest;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.*;

public class QuestListener {

    @SubscribeEvent
    public void onPlayerGetAll(OnPlayerGetAllQuestWaypointEvent event) {
        Server.sendClearWaypoint(event.player);
        if (!Config.EnabledQuestWaypoint) {
            return;
        }
        IPlayer<EntityPlayerMP> player = (IPlayer<EntityPlayerMP>) NpcAPI.Instance().getIEntity(event.player);
        for (IQuest quest : player.getActiveQuests()) {
            Server.sendAddWaypoint(event.player, (Quest) quest);
        }
    }

    @SubscribeEvent
    public void onQuestStart(QuestStartEvent event) {
        if (!Config.EnabledQuestWaypoint) {
            return;
        }
        Server.sendAddWaypoint(event.player.getMCEntity(), (Quest) event.quest);
    }

    @SubscribeEvent
    public void onQuestCompleted(QuestCompletedEvent event) {
        if (!Config.EnabledQuestWaypoint) {
            return;
        }
        Server.sendRemoveWaypoint(event.player.getMCEntity(), (Quest) event.quest);
    }

    @SubscribeEvent
    public void onQuestTurnedIn(QuestTurnedInEvent event) {
        if (!Config.EnabledQuestWaypoint) {
            return;
        }
        Server.sendRemoveWaypoint(event.player.getMCEntity(), (Quest) event.quest);
    }
}
