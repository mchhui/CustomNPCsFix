package mchhui.customnpcsfix.listener.client;

import java.util.List;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import mchhui.customnpcsfix.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import noppes.npcs.entity.EntityNPCInterface;

public class PlayerListener {
    private static int tick;

    @SubscribeEvent
    public void onTick(ClientTickEvent event) {
        if (!Config.AutoReaddNPCToChunk) {
            return;
        }
        if (event.phase != Phase.END) {
            return;
        }
        tick++;
        if (tick % 20 != 0) {
            return;
        }
        World world = Minecraft.getMinecraft().theWorld;
        if(world==null) {
            return;
        }
        List<Entity> list = world.getLoadedEntityList();
        for (Entity entity : list) {
            if (!(entity instanceof EntityNPCInterface)) {
                continue;
            }
            if (entity.isDead) {
                continue;
            }
            boolean flag = false;
            Chunk chunk = world.getChunkFromChunkCoords(entity.chunkCoordX, entity.chunkCoordZ);
            List[] entityLists = chunk.entityLists;
            for (List entityList : entityLists) {
                if (entityList.contains(entity)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                chunk.addEntity(entity);
            }
        }
    }
}
