package me.zer0pxl.cb.furniture.blockshapes;

import net.minecraft.util.AxisAlignedBB;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

public class BlockShapes {

	private final Map<String, Map<String, BlockShape>> shapes = new HashMap<>();

	public BlockShapes clone() {
		BlockShapes newShapes = new BlockShapes();
		for (Map.Entry<String, Map<String, BlockShape>> shapeEntry : shapes.entrySet())
			newShapes.getShapes().put(shapeEntry.getKey(), cloneBlocksShapes(shapeEntry.getValue()));

		return newShapes;
	}

	public static Map<String, BlockShape> cloneBlocksShapes(Map<String, BlockShape> shapes) {
		HashMap<String, BlockShape> stateShapes = new HashMap<>();
		for (Map.Entry<String, BlockShape> stringBlockShapeEntry : shapes.entrySet())
			stateShapes.put(stringBlockShapeEntry.getKey(), stringBlockShapeEntry.getValue().clone());

		return stateShapes;
	}

	public Map<String, BlockShape> getShapes(String block) {
		return shapes.getOrDefault(block, Collections.emptyMap());
	}

	public BlockShapes mergeShapes(BlockShapes otherShapes) {
		shapes.putAll(otherShapes.getShapes());
		return this;
	}

	public static BlockShapes read(DataInputStream dis) throws IOException {
		BlockShapes blockShapes = new BlockShapes();
		int shapesSize = dis.readInt();

		for (int i = 0; i < shapesSize; ++i) {
			String blockKey = dis.readUTF();
			HashMap<String, BlockShape> shapesMap = new HashMap<>();
			int states = dis.readInt();
			for (int j = 0; j < states; ++j) {
				String state = dis.readUTF();
				List<AxisAlignedBB> shapes = BlockShapes.readShapes(dis);
				boolean hasCollisionShapes = dis.readBoolean();
				List<AxisAlignedBB> collisionShapes = hasCollisionShapes ? BlockShapes.readShapes(dis) : null;
				shapesMap.put(state, new BlockShape(shapes, hasCollisionShapes, collisionShapes));
			}
			blockShapes.getShapes().put(blockKey, shapesMap);
		}

		return blockShapes;
	}

	private static List<AxisAlignedBB> readShapes(DataInputStream dis) throws IOException {
		ArrayList<AxisAlignedBB> shapes = new ArrayList<>();
		int size = dis.readInt();
		for (int i = 0; i < size; ++i) {
			AxisAlignedBB axisAlignedBB = new AxisAlignedBB(dis.readDouble(), dis.readDouble(), dis.readDouble(), dis.readDouble(), dis.readDouble(), dis.readDouble());
			shapes.add(axisAlignedBB);
		}

		return shapes;
	}

	public Map<String, Map<String, BlockShape>> getShapes() {
		return this.shapes;
	}

}
