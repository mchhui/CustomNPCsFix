import mchhui.customnpcsfix.Config;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.util.NBTJsonUtil;

public class TestMain {
    public static void main(String[] args) {
        Config.FastJsonDeserialize=true;
        NBTTagCompound nbt=new NBTTagCompound();
        nbt.setString("test", "he\nllo");
        nbt.setTag("test1",new NBTTagCompound());
        nbt.getCompoundTag("test1").setString("test2", "1");
        System.out.println(mchhui.customnpcsfix.util.NBTJsonUtil.Convert(NBTJsonUtil.Convert(nbt)));
    }
}
