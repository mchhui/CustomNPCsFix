package mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class WaypointsGuiRendererTranfromer implements IClassTransformer{

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("xaero.common.minimap.waypoints.render.WaypointsGuiRenderer")) {
            FMLLog.getLogger().warn("[Transforming:xaero.common.minimap.waypoints.render.WaypointsGuiRenderer]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            List<MethodNode> methods = classNode.methods;
            for (MethodNode method : methods) {
                if (method.name.equals("drawIconOnGUI")) {
                    InsnList list = new InsnList();
                    LabelNode label = new LabelNode();
                    list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                    list.add(new VarInsnNode(Opcodes.ALOAD, 2));
                    list.add(new VarInsnNode(Opcodes.ILOAD, 3));
                    list.add(new VarInsnNode(Opcodes.ILOAD, 4));
                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/api/EventHook",
                            "onDrawIconOnGUI", "(Lxaero/common/minimap/waypoints/render/WaypointsGuiRenderer;Lxaero/common/minimap/waypoints/Waypoint;Lxaero/common/settings/ModSettings;II)Z",
                            false));
                    list.add(new JumpInsnNode(Opcodes.IFEQ, label));
                    list.add(new InsnNode(Opcodes.RETURN));
                    list.add(label);
                    method.instructions.insert(method.instructions.getFirst(), list);
                }
            }
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            FMLLog.getLogger().warn("[Transformed:xaero.common.minimap.waypoints.render.WaypointsGuiRenderer]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }

}
