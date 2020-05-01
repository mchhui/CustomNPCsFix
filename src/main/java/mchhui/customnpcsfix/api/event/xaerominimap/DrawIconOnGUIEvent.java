package mchhui.customnpcsfix.api.event.xaerominimap;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.render.WaypointsGuiRenderer;
import xaero.common.settings.ModSettings;

@Cancelable
public class DrawIconOnGUIEvent extends Event{
    public final WaypointsGuiRenderer renderer;
    public final Waypoint waypoint;
    public final ModSettings settings;
    public final int drawX;
    public final int drawY;
    public DrawIconOnGUIEvent(WaypointsGuiRenderer renderer,Waypoint waypoint, ModSettings settings, int drawX, int drawY) {
        this.renderer=renderer;
        this.waypoint=waypoint;
        this.settings=settings;
        this.drawX=drawX;
        this.drawY=drawY;
    }
}
