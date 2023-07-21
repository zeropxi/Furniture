package me.zer0pxl.cb.asm.transformer;

import me.zer0pxl.cb.furniture.optifine_patches.OptifineBlockModelRenderer;
import me.zer0pxl.cb.furniture.util.Reflector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

import static me.zer0pxl.cb.furniture.util.Reflector.getMapped;

public class BlockModelRendererTransformer implements Opcodes {

	public static byte[] transform(String transformedName, byte[] basicClass) {
		if (!transformedName.equals("net.minecraft.client.renderer.BlockModelRenderer"))
			return basicClass;

		try {
			Class.forName("optifine.OptiFineForgeTweaker");
		} catch (ClassNotFoundException e) {
			return basicClass;
		}

		ClassNode classNode = new ClassNode();
		ClassReader reader = new ClassReader(basicClass);
		reader.accept(classNode, 0);

		for (String methodName : new String[] {"renderModelSmooth", "renderModelFlat"}) {
			MethodNode method = getMethod(methodName, classNode);
			ListIterator<AbstractInsnNode> it = method.instructions.iterator();

			InsnList insns = new InsnList();
			insns.add(new VarInsnNode(ALOAD, 2));
			insns.add(new VarInsnNode(ALOAD, 3));
			insns.add(new VarInsnNode(ALOAD, 4));
			insns.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(OptifineBlockModelRenderer.class), "getModel", getMapped("(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;)Lnet/minecraft/client/resources/model/IBakedModel;", "(Lboq;Lalz;Lcj;)Lboq;"), false));
			insns.add(new VarInsnNode(ASTORE, 2));
			method.instructions.insertBefore(it.next(), insns);

			while (it.hasNext()) {
				AbstractInsnNode node = it.next();
				if (node.getOpcode() != INVOKEINTERFACE)
					continue;

				MethodInsnNode insn = (MethodInsnNode) node;
				if (!(insn.owner.equals(getMapped("net/minecraft/client/resources/model/IBakedModel", "boq")) &&
						insn.name.equals(getMapped("getFaceQuads", "a")) &&
						insn.desc.equals(getMapped("(Lnet/minecraft/util/EnumFacing;)Ljava/util/List;", "(Lcq;)Ljava/util/List;"))))
					continue;

				insns = new InsnList();
				insns.add(new VarInsnNode(ALOAD, 2));
				insns.add(new VarInsnNode(ALOAD, 3));
				insns.add(new VarInsnNode(ALOAD, 4));
				insns.add(new VarInsnNode(ALOAD, 14));
				insns.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(OptifineBlockModelRenderer.class), "getQuads", Reflector.getMapped("(Ljava/util/List;Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Ljava/util/List;", "(Ljava/util/List;Lboq;Lalz;Lcj;Lcq;)Ljava/util/List;"), false));
				method.instructions.insert(node, insns);
			}
		}

		ClassWriter writer = new ClassWriter(3);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	private static MethodNode getMethod(String name, ClassNode classNode) {
		String desc = "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;Lnet/minecraft/client/renderer/WorldRenderer;Z)Z";
		String mappedDesc = Reflector.getMapped(desc, "(Ladq;Lboq;Lalz;Lcj;Lbfd;Z)Z");
		return classNode.methods.stream()
				.filter(m -> m.name.equals(name) && m.desc.equals(mappedDesc))
				.findFirst()
				.orElseThrow(() -> new NoSuchMethodError("Could not find " + name + desc + " / " + name + mappedDesc + "!"));
	}

}
