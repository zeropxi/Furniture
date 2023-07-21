package me.zer0pxl.cb.furniture.blockshapes;

import net.minecraft.util.math.AxisAlignedBB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockShape {

	public static final BlockShape DEFAULT_SHAPE = new BlockShape(Collections.singletonList(new AxisAlignedBB(0, 0, 0, 1, 1, 1)), false, null);
	private final List<AxisAlignedBB> shape;
	private final boolean hasCollisionShape;
	private final List<AxisAlignedBB> collisionShapes;

	public BlockShape(List<AxisAlignedBB> shape, boolean hasCollisionShape, List<AxisAlignedBB> collisionShapes) {
		this.shape = shape;
		this.hasCollisionShape = hasCollisionShape;
		this.collisionShapes = collisionShapes;
	}

	public BlockShape clone() {
		return new BlockShape(clone(shape), hasCollisionShape, clone(collisionShapes));
	}

	public static List<AxisAlignedBB> clone(List<AxisAlignedBB> input) {
		if (input == null)
			return new ArrayList<>();

		ArrayList<AxisAlignedBB> output = new ArrayList<>();
		for (AxisAlignedBB inputBox : input)
			output.add(new AxisAlignedBB(inputBox.minX, inputBox.minY, inputBox.minZ, inputBox.maxX, inputBox.maxY, inputBox.maxZ));

		return output;
	}

	public List<AxisAlignedBB> getShape() {
		return shape;
	}

	public boolean isHasCollisionShape() {
		return hasCollisionShape;
	}

	public List<AxisAlignedBB> getCollisionShapes() {
		return collisionShapes;
	}

}