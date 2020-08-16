package mchhui.customnpcsfix.client.gui;

import mchhui.customnpcsfix.controllers.data.Waypoint;
import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class HueihueaGuiQuestWaypoint extends SubGuiInterface implements ITextfieldListener {
    public static boolean isFromDIM = false;
    public Waypoint point;

    public HueihueaGuiQuestWaypoint(Waypoint point) {
        this.point = point;
        setBackground("menubg.png");
        xSize = 256;
        ySize = 216;
        closeOnEsc = true;
    }

    @Override
    public void initGui() {
        super.initGui();
        if (isFromDIM) {
            addLabel(new GuiNpcLabel(4, "quest.waypoint.dim", guiLeft + 4, guiTop + 20));
        } else {
            addLabel(new GuiNpcLabel(4, "quest.waypoint.worldname", guiLeft + 4, guiTop + 20));
        }
        addLabel(new GuiNpcLabel(5, "quest.waypoint.setname", guiLeft + 4, guiTop + 50));
        addLabel(new GuiNpcLabel(6, "quest.waypoint.x", guiLeft + 4, guiTop + 80));
        addLabel(new GuiNpcLabel(7, "quest.waypoint.y", guiLeft + 4, guiTop + 110));
        addLabel(new GuiNpcLabel(8, "quest.waypoint.z", guiLeft + 4, guiTop + 140));

        if(isFromDIM) {
            addTextField(new GuiNpcTextField(4, this, fontRenderer, guiLeft + 130, guiTop + 15, 100, 20, String.valueOf(point.worldDIM)));
        }else {
            addTextField(new GuiNpcTextField(4, this, fontRenderer, guiLeft + 130, guiTop + 15, 100, 20, point.worldName));
        }
        addTextField(new GuiNpcTextField(5, this, fontRenderer, guiLeft + 130, guiTop + 45, 100, 20, point.setName));
        addTextField(new GuiNpcTextField(6, this, fontRenderer, guiLeft + 130, guiTop + 75, 100, 20,
                String.valueOf(point.x)));
        addTextField(new GuiNpcTextField(7, this, fontRenderer, guiLeft + 130, guiTop + 105, 100, 20,
                String.valueOf(point.y)));
        addTextField(new GuiNpcTextField(8, this, fontRenderer, guiLeft + 130, guiTop + 135, 100, 20,
                String.valueOf(point.z)));

        addButton(new GuiNpcButton(66, guiLeft + 20, guiTop + 190, 100, 20, "gui.done"));
        addButton(new GuiNpcButton(67, guiLeft + 20 + 100 + 16, guiTop + 190, 100, 20,
                ((point.isEnabled) ? "quest.waypoint.disable" : "quest.waypoint.enable")));
    }

    @Override
    public void buttonEvent(GuiButton guibutton) {
        super.buttonEvent(guibutton);
        if (guibutton.id == 66) {
            close();
        }
        if (guibutton.id == 67) {
            point.isEnabled = !point.isEnabled;
            initGui();
        }
    }

    @Override
    public void unFocused(GuiNpcTextField textField) {
        if (textField.getId() == 4) {
            if (isFromDIM) {
                point.worldDIM = Integer.valueOf(textField.getText());
            } else {
                point.worldName = textField.getText();
            }
        }
        if (textField.getId() == 5) {
            point.setName = textField.getText();
        }
        if (textField.getId() == 6) {
            point.x = getInt(textField.getText());
        }
        if (textField.getId() == 7) {
            point.y = getInt(textField.getText());
        }
        if (textField.getId() == 8) {
            point.z = getInt(textField.getText());
        }
    }

    private static int getInt(String str) {
        int i = 0;
        try {
            i=Integer.valueOf(str);
        } catch (NumberFormatException err) {

        }
        return i;
    }

}
