package mchhui.customnpcsfix.client.gui;

import java.lang.reflect.Field;

import mchhui.customnpcsfix.util.QuestHelper;
import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.gui.global.GuiQuestEdit;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.controllers.data.Quest;

public class HueihueaGuiQuestEdit extends GuiQuestEdit {
    private Quest quest;
    public HueihueaGuiQuestEdit(Quest quest) {
        super(quest);
        this.quest=quest;
    }

    @Override
    public void initGui() {
        super.initGui();
        addLabel(new GuiNpcLabel(1001, "quest.waypoint", guiLeft + 214, guiTop + 74));
        addButton(new GuiNpcButton(1001, guiLeft + 330, guiTop + 69, 50, 20, "selectServer.edit"));
    }

    @Override
    public void buttonEvent(GuiButton guibutton) {
        super.buttonEvent(guibutton);
        if(guibutton.id==1001) {
            setSubGui(new HueihueaGuiQuestWaypoint(QuestHelper.getQuestWaypoint(quest)));
        }
    }

    @Override
    public void subGuiClosed(SubGuiInterface subgui) {
        // TODO 自动生成的方法存根
        super.subGuiClosed(subgui);
    }
    
    public static HueihueaGuiQuestEdit create(GuiQuestEdit gui) {
        Class classGuiQuestEdit = GuiQuestEdit.class;
        Field fieldQuest = null;
        HueihueaGuiQuestEdit result = null;
        try {
            fieldQuest = classGuiQuestEdit.getDeclaredField("quest");
            fieldQuest.setAccessible(true);
            result = new HueihueaGuiQuestEdit((Quest) fieldQuest.get(gui));
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return result;
    }

}
