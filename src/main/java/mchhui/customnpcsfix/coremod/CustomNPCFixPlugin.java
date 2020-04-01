package mchhui.customnpcsfix.coremod;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("CustomNPCFixPlugin")
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions("mchhui.customnpcsfix.coremod.")
@IFMLLoadingPlugin.SortingIndex(1001)
public class CustomNPCFixPlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { 
                "mchhui.customnpcsfix.coremod.noppes.npcs.util.NBTJsonUtilTransformer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.client.PacketHandlerClientTransfromer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.client.renderer.RenderNPCHumanMaleTansfromer",
                "mchhui.customnpcsfix.coremod.noppes.npcs.scripted.ScriptPlayerTransfromer",
                };
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
