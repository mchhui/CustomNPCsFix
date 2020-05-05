package mchhui.customnpcsfix.listener;

import mchhui.customnpcsfix.Config;
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
import noppes.npcs.NpcDamageSource;
import noppes.npcs.NpcDamageSourceInderect;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.entity.EntityNPCInterface;

public class PlayerListener {
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
        if (!(event.getEntityPlayer() instanceof EntityPlayerMP)) {
            return;
        }
        if (!event.getEntityPlayer().world.isRemote) {
            int i = event.getEntityPlayer().inventory.getFirstEmptyStack();
            boolean flag = false;
            if (i == -1) {
                for (int index = 0; index < event.getEntityPlayer().inventory.getSizeInventory(); index++) {
                    ItemStack is = event.getEntityPlayer().inventory.getStackInSlot(index);
                    if (canMergeStacks(is, event.getItem().getItem())) {
                        flag = true;
                        i = index;
                    }
                }
            }
            if (i == -1) {
                return;
            }

            if (flag) {
                event.getEntityPlayer().inventory.getStackInSlot(i)
                        .setCount(event.getEntityPlayer().inventory.getStackInSlot(i).getCount()
                                + event.getItem().getItem().getCount());
            } else {
                event.getEntityPlayer().inventory.setInventorySlotContents(i, event.getItem().getItem().copy());
            }

            PlayerData.get(event.getEntityPlayer()).questData.checkQuestCompletion(event.getEntityPlayer(), 0);
            event.getEntityPlayer().inventory.getStackInSlot(i)
                    .setCount(event.getEntityPlayer().inventory.getStackInSlot(i).getCount()
                            - event.getItem().getItem().getCount());
            ((EntityPlayerMP) event.getEntityPlayer()).sendSlotContents(event.getEntityPlayer().inventoryContainer, i,
                    event.getEntityPlayer().inventory.getStackInSlot(i));
        }
    }

    private boolean canMergeStacks(ItemStack stack1, ItemStack stack2) {
        return !stack1.isEmpty() && this.stackEqualExact(stack1, stack2) && stack1.isStackable()
                && stack1.getCount() < stack1.getMaxStackSize() && stack1.getCount() < 64;
    }

    private boolean stackEqualExact(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem()
                && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata())
                && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
}
