import java.io.IOException;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import mchhui.customnpcsfix.NetListener.ListenData;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.Server;
import noppes.npcs.constants.EnumPacketServer;

public class TestMain {
    public static void main(String[] args) throws IOException {
        ListenData data=new ListenData();
        data.from=".*";
        System.out.println(data.matchedFrom("GUI_DATA"));
    }
}
