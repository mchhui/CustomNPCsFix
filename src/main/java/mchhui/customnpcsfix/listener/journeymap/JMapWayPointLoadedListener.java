package mchhui.customnpcsfix.listener.journeymap;

import mchhui.customnpcsfix.Client;
import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.api.event.JMapWayPointLoadedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class JMapWayPointLoadedListener {
    @SubscribeEvent
    public void onJMapWayPointLoaded(JMapWayPointLoadedEvent event) {
        if (Config.QuestWaypointJMapMode) {
            Client.getAllQuestWaypoint();
        }
    }
}