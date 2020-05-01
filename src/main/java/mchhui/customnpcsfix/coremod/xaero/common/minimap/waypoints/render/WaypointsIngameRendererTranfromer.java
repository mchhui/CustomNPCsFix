package mchhui.customnpcsfix.coremod.xaero.common.minimap.waypoints.render;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class WaypointsIngameRendererTranfromer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("xaero.common.minimap.waypoints.render.WaypointsIngameRenderer")) {
            FMLLog.getLogger().warn("[Transforming:xaero.common.minimap.waypoints.render.WaypointsIngameRenderer]");
            ClassNode classNode = new ClassNode(Opcodes.ASM5);
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);
            List<MethodNode> methods = classNode.methods;
            for (MethodNode method : methods) {
                if (method.name.equals("renderWaypointIngame")) {
                    if (!method.desc.equals(
                            "(FLnet/minecraft/util/math/Vec3d;IDDDLxaero/common/minimap/waypoints/Waypoint;Lxaero/common/IXaeroMinimap;DDDDLnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/client/renderer/Tessellator;Z)V")) {
                        FMLLog.log.warn("CustomNPCsFix EnabledQuestWaypoint can't work in this version of xaero's minimap");
                        return basicClass;
                    }
                    for (AbstractInsnNode node : method.instructions.toArray()) {
                        if (node.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                            if (((MethodInsnNode) node).name.equals("drawIconInWorld")) {
                                InsnList list = new InsnList();
                                LabelNode label = new LabelNode();
                                list.add(label);
                                list.add(new VarInsnNode(Opcodes.ALOAD, 0));//renderer
                                list.add(new VarInsnNode(Opcodes.FLOAD, 1));//cameraAngleYaw
                                list.add(new VarInsnNode(Opcodes.ALOAD, 2));//lookVector
                                list.add(new VarInsnNode(Opcodes.ILOAD, 3));//lookVectorMultiplier
                                list.add(new VarInsnNode(Opcodes.DLOAD, 4));//eyesX
                                list.add(new VarInsnNode(Opcodes.DLOAD, 6));//eyesY
                                list.add(new VarInsnNode(Opcodes.DLOAD, 8));//eyesZ
                                list.add(new VarInsnNode(Opcodes.ALOAD, 10));//waypoint
                                list.add(new VarInsnNode(Opcodes.ALOAD, 11));//modMain
                                list.add(new VarInsnNode(Opcodes.DLOAD, 12));//radius
                                list.add(new VarInsnNode(Opcodes.DLOAD, 14));//d3
                                list.add(new VarInsnNode(Opcodes.DLOAD, 16));//d4
                                list.add(new VarInsnNode(Opcodes.DLOAD, 18));//d5
                                list.add(new VarInsnNode(Opcodes.ALOAD, 20));//entity
                                list.add(new VarInsnNode(Opcodes.ALOAD, 21));//bufferBuilder
                                list.add(new VarInsnNode(Opcodes.ALOAD, 22));//tessellator
                                list.add(new VarInsnNode(Opcodes.ILOAD, 23));//divideBy8
                                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mchhui/customnpcsfix/api/EventHook",
                                        "onRenderWaypointIngame",
                                        "(Lxaero/common/minimap/waypoints/render/WaypointsIngameRenderer;FLnet/minecraft/util/math/Vec3d;IDDDLxaero/common/minimap/waypoints/Waypoint;Lxaero/common/IXaeroMinimap;DDDDLnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/client/renderer/Tessellator;Z)Z",
                                        false));
                                method.instructions.insert(node, list);
                            }
                        }
                    }
                }
            }
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            FMLLog.getLogger().warn("[Transformed:xaero.common.minimap.waypoints.render.WaypointsIngameRenderer]");
            return classWriter.toByteArray();
        }
        return basicClass;
    }

}
