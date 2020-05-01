package mchhui.customnpcsfix.api.event.xaerominimap;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import xaero.common.IXaeroMinimap;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.render.WaypointsIngameRenderer;

@Cancelable
public class RenderWaypointIngameEvent extends Event {
    public final WaypointsIngameRenderer renderer;
    public final float cameraAngleYaw;
    public final Vec3d lookVector;
    public final int lookVectorMultiplier;
    public final double eyesX;
    public final double eyesY;
    public final double eyesZ;
    public final Waypoint waypoint;
    public final IXaeroMinimap modMain;
    public final double radius;
    public final double d3;
    public final double d4;
    public final double d5;
    public final Entity entity;
    public final BufferBuilder bufferBuilder;
    public final Tessellator tessellator;
    public final boolean divideBy8;

    public RenderWaypointIngameEvent(WaypointsIngameRenderer renderer,float cameraAngleYaw, Vec3d lookVector, int lookVectorMultiplier, double eyesX,
            double eyesY, double eyesZ, Waypoint waypoint, IXaeroMinimap modMain, double radius, double d3, double d4,
            double d5, Entity entity, BufferBuilder bufferBuilder, Tessellator tessellator, boolean divideBy8) {
        this.renderer=renderer;
        this.cameraAngleYaw = cameraAngleYaw;
        this.lookVector = lookVector;
        this.lookVectorMultiplier = lookVectorMultiplier;
        this.eyesX = eyesX;
        this.eyesY = eyesY;
        this.eyesZ = eyesZ;
        this.waypoint = waypoint;
        this.modMain = modMain;
        this.radius = radius;
        this.d3 = d3;
        this.d4 = d4;
        this.d5 = d5;
        this.entity = entity;
        this.bufferBuilder = bufferBuilder;
        this.tessellator = tessellator;
        this.divideBy8 = divideBy8;
    }
}
