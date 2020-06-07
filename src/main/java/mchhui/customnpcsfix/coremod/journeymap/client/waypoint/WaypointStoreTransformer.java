package mchhui.customnpcsfix.coremod.journeymap.client.waypoint;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class WaypointStoreTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("journeymap.client.waypoint.WaypointStore")) {
            FMLLog.getLogger().warn("[Transforming:journeymap.client.waypoint.WaypointStore]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            for (MethodNode method : classNode.methods) {
                if (method.name.equals("load") && method.access == Opcodes.ACC_PUBLIC) {
                    for (AbstractInsnNode node : method.instructions.toArray()) {
                        if (node.getOpcode() == Opcodes.PUTFIELD) {
                            if (((FieldInsnNode) node).name.equals("loaded")) {
                                InsnList list = new InsnList();
                                list.add(new LabelNode());
                                list.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraftforge/common/MinecraftForge", "EVENT_BUS",
                                        "Lnet/minecraftforge/fml/common/eventhandler/EventBus;"));
                                list.add(new TypeInsnNode(Opcodes.NEW,
                                        "mchhui/customnpcsfix/api/event/JMapWayPointLoadedEvent"));
                                list.add(new InsnNode(Opcodes.DUP));
                                list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,
                                        "mchhui/customnpcsfix/api/event/JMapWayPointLoadedEvent", "<init>", "()V", false));
                                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                        "net/minecraftforge/fml/common/eventhandler/EventBus", "post",
                                        "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                                list.add(new InsnNode(Opcodes.POP));
                                method.instructions.insert(node, list);
                            }
                        }
                    }
                }
            }
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            FMLLog.getLogger().warn("[Transformed:journeymap.client.waypoint.WaypointStore]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }

}
