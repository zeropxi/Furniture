package me.zer0pxl.cb.asm.transformer;

import me.zer0pxl.cb.furniture.util.ItemRenderer;
import me.zer0pxl.cb.furniture.util.Reflector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class RenderImplementationTransformer implements Opcodes {

	public static byte[] transform(String transformedName, byte[] basicClass) {
		if (!transformedName.equals("net.labymod.core_implementation.mc18.RenderImplementation"))
			return basicClass;

		ClassNode classNode = new ClassNode();
		ClassReader reader = new ClassReader(basicClass);
		reader.accept(classNode, 0);

		MethodNode method = classNode.methods.stream()
				.filter(m -> m.name.equals("renderItemIntoGUI"))
				.findFirst()
				.orElseThrow(() -> new NoSuchMethodError("Could not find RenderImplementation.renderItemIntoGUI!"));

		InsnList insns = method.instructions;
		insns.clear();
		insns.add(new VarInsnNode(ALOAD, 1));
		insns.add(new VarInsnNode(DLOAD, 2));
		insns.add(new InsnNode(D2I));
		insns.add(new VarInsnNode(DLOAD, 4));
		insns.add(new InsnNode(D2I));
		insns.add(new VarInsnNode(ALOAD, 0));
		insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/Gui", "field_73735_i", "F"));
		insns.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ItemRenderer.class), "renderItemIntoGUI", Reflector.getMapped("(Lnet/minecraft/item/ItemStack;IIF)V", "(Lzx;IIF)V"), false));
		insns.add(new InsnNode(RETURN));

		ClassWriter writer = new ClassWriter(3);
		classNode.accept(writer);
		return writer.toByteArray();
	}

}
