package mchhui.customnpcsfix.util;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;

import journeymap.client.model.Waypoint;
import journeymap.client.render.texture.TextureImpl;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class HueihueaJMapWaypoint extends Waypoint{
    public static HashMap<String, TextureImpl> icons = new HashMap<String, TextureImpl>();
    private String pointIcon;
    
    public HueihueaJMapWaypoint(String name, BlockPos pos, Color color, Type type, Integer currentDimension, String icon) {
        super(name, pos.getX(), pos.getY(), pos.getZ(), true, color.getRed(), color.getGreen(), color.getBlue(), type,
                "NPCFIX", currentDimension, Arrays.asList(currentDimension));
        this.pointIcon = icon;
        this.setPersistent(true);
    }
    
    @Override
    public TextureImpl getTexture() {
        if (!icons.containsKey(pointIcon)) {
            icons.put(pointIcon, new TextureImpl(new ResourceLocation(pointIcon)));
        }
        return icons.get(this.pointIcon);
    }
}
