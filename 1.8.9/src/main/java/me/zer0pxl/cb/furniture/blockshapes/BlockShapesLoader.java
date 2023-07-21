package me.zer0pxl.cb.furniture.blockshapes;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class BlockShapesLoader {

	private static final byte HIGHEST_SHAPES_VERSION = 13;
	public static final CompletableFuture<BlockShapes> SHAPES_FUTURE = new CompletableFuture<>();
	public static BlockShapes READ_SHAPES = new BlockShapes();

	static {
		new Thread(() -> {
			BlockShapes blockShapes = new BlockShapes();
			for (int i = 1; i <= HIGHEST_SHAPES_VERSION; ++i) {
				try (InputStream is = BlockShapesLoader.class.getResourceAsStream("/assets/cb/shapes_v" + i + ".dat");
				     DataInputStream dataInputStream = new DataInputStream(Objects.requireNonNull(is))) {
					blockShapes = blockShapes.mergeShapes(BlockShapes.read(dataInputStream));
				} catch (IOException t) {
					t.printStackTrace();
				}
			}

			READ_SHAPES = blockShapes;
			SHAPES_FUTURE.complete(READ_SHAPES);
		}).start();
	}

}
