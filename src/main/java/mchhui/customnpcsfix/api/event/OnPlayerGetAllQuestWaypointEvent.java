package mchhui.customnpcsfix.api.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.Event;

public class OnPlayerGetAllQuestWaypointEvent extends Event{
    public EntityPlayerMP player;
    public OnPlayerGetAllQuestWaypointEvent(EntityPlayerMP player) {
        this.player=player;
    }
}
