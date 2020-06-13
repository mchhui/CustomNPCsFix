package mchhui.customnpcsfix.coremod;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("CustomNPCFixPlugin")
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.TransformerExclusions("mchhui.customnpcsfix.coremod.")
@IFMLLoadingPlugin.SortingIndex(1001)
public class CustomNPCFixPlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "mchhui.customnpcsfix.coremod.noppes.npcs.util.NBTJsonUtilTransformer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.roles.RoleCompanionTransformer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.client.PacketHandlerClientTransformer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.client.ClientTransFormer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.controllers.data.TransportLocationTransformer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.controllers.data.QuestTransformer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.client.renderer.RenderCustomNpcTansformer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.ServerTransformer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.api.wrapper.PlayerWrapperTransformer",
                "mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsGuiRendererTranformer",
                "mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render.WaypointsIngameRendererTranformer",
                "mchhui.customnpcsfix.coremod.journeymap.client.waypoint.WaypointStoreTransformer",
                "mchhui.customnpcsfix.coremod.com.mamiyaotaru.voxelmap.VoxelMapTransformer",
                "mchhui.customnpcsfix.coremod.net.minecraft.client.resources.ResourcePackRepositoryTransformer" };

    }

    @Override
    public String getModContainerClass() {
        // TODO 自动生成的方法存根
        return null;
    }

    @Override
    public String getSetupClass() {
        // TODO 自动生成的方法存根
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        // TODO 自动生成的方法存根

    }

    @Override
    public String getAccessTransformerClass() {
        // TODO 自动生成的方法存根
        return null;
    }

}
