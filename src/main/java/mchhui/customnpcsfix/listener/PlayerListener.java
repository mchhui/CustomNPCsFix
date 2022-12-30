package mchhui.customnpcsfix.listener;

import java.util.HashSet;

import mchhui.customnpcsfix.Config;
import mchhui.customnpcsfix.util.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import noppes.npcs.NpcDamageSource;
import noppes.npcs.NpcDamageSourceInderect;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.entity.EntityNPCInterface;

public class PlayerListener {
    public HashSet<String> toCheckList=new HashSet<String>();
    
    //on entity attacked player
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerAttack(LivingAttackEvent event) {
        boolean flag = false;
        if (!Config.AutoUseNPCDamageSource) {
            return;
        }

        if (event.getSource() instanceof NpcDamageSource || event.getSource() instanceof NpcDamageSourceInderect) {
            return;
        }

        if (event.getSource() instanceof EntityDamageSourceIndirect) {
            flag = true;
        } else if (!(event.getSource() instanceof EntityDamageSource)) {
            return;
        }

        if (!(event.getEntityLiving() instanceof EntityPlayerMP)) {
            return;
        }

        if (!(event.getSource().getTrueSource() instanceof EntityNPCInterface)) {
            return;
        }

        EntityPlayerMP player = (EntityPlayerMP) event.getEntityLiving();
        if (!flag) {
            player.attackEntityFrom(
                    new NpcDamageSource(event.getSource().getDamageType(), event.getSource().getTrueSource()),
                    event.getAmount());
        } else {
            player.attackEntityFrom(
                    new NpcDamageSourceInderect(event.getSource().getDamageType(),
                            event.getSource().getImmediateSource(), event.getSource().getTrueSource()),
                    event.getAmount());
        }

    }

    @SubscribeEvent
    public void pickUp(EntityItemPickupEvent event) {
        if (!Config.EffectiveCollectItemQuest) {
            return;
        }
        toCheckList.add(event.getEntityPlayer().getName());
    }
    
    @SubscribeEvent
    public void playerTick(PlayerTickEvent event) {
        if(event.phase!=Phase.END||event.side!=Side.SERVER) {
            return;
        }
        if(toCheckList.contains(event.player.getName())) {
            PlayerData.get(event.player).questData.checkQuestCompletion(event.player, 0);
            toCheckList.remove(event.player.getName());
        }
    }
}
