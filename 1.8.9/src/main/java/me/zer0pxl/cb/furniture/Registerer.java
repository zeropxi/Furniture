package me.zer0pxl.cb.furniture;

import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.version_specific.CustomItemBlock;
import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import me.zer0pxl.cb.furniture.blockshapes.BlockShapesLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Registerer {
	private static final Map<VersionBlock, ModBlock> blocks = new HashMap<>();
	private static final Method addMethod;

	public static void registerBlocks() {
		for (Map.Entry<VersionBlock, ModBlock> entry : blocks.entrySet()) {
			VersionBlock block = entry.getKey();
			BlockShapesLoader.SHAPES_FUTURE.whenComplete((a, b) -> block.addShapes());
			ResourceLocation loc = new ResourceLocation("cb", block.getUnlocalizedName().substring(5));

			try {
				addMethod.invoke(GameData.getBlockRegistry(), entry.getValue().getBlockKey().getId(), loc, block);
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void registerRenders() {
		ModBlocks.getBlocks().forEach((key, block) -> {
			if (!block.hasItem())
				return;

			Block mcBlock = block.getBlockHandle();
			Block suppliedBlock = block.getBlockToSupplyToItem().getBlockHandle();
			CustomItemBlock item = new CustomItemBlock(mcBlock, suppliedBlock);
			item.setUnlocalizedName(mcBlock.getUnlocalizedName());

			ResourceLocation loc = new ResourceLocation("cb", mcBlock.getUnlocalizedName().substring(5));
			try {
				addMethod.invoke(GameData.getItemRegistry(), key.getId(), loc, item);
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}

			ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
			ModelResourceLocation modelResourceLocation = new ModelResourceLocation(key.getLocation(), "inventory");
			mesher.register(item, 0, modelResourceLocation);
		});
	}

	static {
		ModBlocks.getBlocks().forEach((key, block) -> {
			Class<VersionBlock> versionBlockClass;
			VersionBlock.setRegisteringBlock(block);
			try {
				versionBlockClass = block.getVersionBlockClass() != null ? (Class<VersionBlock>) Class.forName("me.zer0pxl.cb.furniture.block.version_specific." + block.getVersionBlockClass()) : VersionBlock.class;
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("VersionBlockClass for " + key.getLocation() + " does not exist!");
			}

			VersionBlock mcBlock;
			try {
				mcBlock = versionBlockClass.getConstructor(ModBlock.class).newInstance(block);
			} catch (Throwable t) {
				System.err.println(block.getBlockKey().getLocation() + "threw " + t.getClass().getSimpleName());
				t.printStackTrace();
				return;
			}

			if (block.getLightLevel() > 0)
				mcBlock.setLightLevel(block.getLightLevel());

			mcBlock.setUnlocalizedName(key.getLocation().split(":")[1]);
			mcBlock.setCreativeTab(FurnitureTab.INSTANCE);
			block.setBlockHandle(mcBlock);
			blocks.put(mcBlock, block);
		});

		try {
			addMethod = FMLControlledNamespacedRegistry.class.getDeclaredMethod("add", int.class, ResourceLocation.class, Object.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		addMethod.setAccessible(true);

	}

}
