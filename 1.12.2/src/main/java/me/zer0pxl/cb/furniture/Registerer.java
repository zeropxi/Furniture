package me.zer0pxl.cb.furniture;

import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.version_specific.CustomItemBlock;
import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import me.zer0pxl.cb.furniture.blockshapes.BlockShapesLoader;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Registerer {
	private static final Map<VersionBlock, ModBlock> blocks = new HashMap<>();

	private static final Method addMethod;

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Map.Entry<VersionBlock, ModBlock> entry : blocks.entrySet()) {
			register(event.getRegistry(), entry.getValue().getBlockKey().getId(), entry.getKey());
			BlockShapesLoader.SHAPES_FUTURE.whenComplete((a, b) -> entry.getKey().addShapes());
		}
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		ModBlocks.getBlocks().forEach((key, block) -> {
			if (!block.hasItem())
				return;

			Block mcBlock = block.getBlockHandle();
			Block suppliedBlock = block.getBlockToSupplyToItem().getBlockHandle();
			CustomItemBlock item = new CustomItemBlock(mcBlock, suppliedBlock);

			register(event.getRegistry(), key.getId(), item);

			ModelResourceLocation mrl = new ModelResourceLocation(key.getLocation(), "inventory");
			ModelLoader.setCustomModelResourceLocation(item, 0, mrl);
		});
	}

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		int i = 1337;
		for (String s : new String[] {"blinds.open", "blinds.close", "diving_board.bounce"}) {
			ResourceLocation resLoc = new ResourceLocation("cb:block." + s);
			register(event.getRegistry(), i, new SoundEvent(resLoc).setRegistryName(resLoc));
		}
	}

	@SubscribeEvent
	public void registerBlockColorHandlers(ColorHandlerEvent.Block event) {
		registerBlockColorHandler(event, (state, reader, pos, i) -> i == 1 ? 0xCCCCCC : 0, "white_picket_fence", "orange_picket_fence", "magenta_picket_fence", "light_blue_picket_fence", "yellow_picket_fence", "lime_picket_fence", "pink_picket_fence", "gray_picket_fence", "light_gray_picket_fence", "cyan_picket_fence", "purple_picket_fence", "blue_picket_fence", "brown_picket_fence", "green_picket_fence", "red_picket_fence", "black_picket_fence", "white_picket_gate", "orange_picket_gate", "magenta_picket_gate", "light_blue_picket_gate", "yellow_picket_gate", "lime_picket_gate", "pink_picket_gate", "gray_picket_gate", "light_gray_picket_gate", "cyan_picket_gate", "purple_picket_gate", "blue_picket_gate", "brown_picket_gate", "green_picket_gate", "red_picket_gate", "black_picket_gate", "post_box");
		registerBlockColorHandler(event, (state, reader, pos, i) -> i == 1 ? 0xBBBBBB : 0, "stripped_oak_crate", "stripped_spruce_crate", "stripped_birch_crate", "stripped_jungle_crate", "stripped_acacia_crate", "stripped_dark_oak_crate", "stripped_oak_kitchen_counter", "stripped_spruce_kitchen_counter", "stripped_birch_kitchen_counter", "stripped_jungle_kitchen_counter", "stripped_acacia_kitchen_counter", "stripped_dark_oak_kitchen_counter", "stripped_oak_kitchen_drawer", "stripped_spruce_kitchen_drawer", "stripped_birch_kitchen_drawer", "stripped_jungle_kitchen_drawer", "stripped_acacia_kitchen_drawer", "stripped_dark_oak_kitchen_drawer", "stripped_oak_kitchen_sink_light", "stripped_spruce_kitchen_sink_light", "stripped_birch_kitchen_sink_light", "stripped_jungle_kitchen_sink_light", "stripped_acacia_kitchen_sink_light", "stripped_dark_oak_kitchen_sink_light", "stripped_oak_kitchen_sink_dark", "stripped_spruce_kitchen_sink_dark", "stripped_birch_kitchen_sink_dark", "stripped_jungle_kitchen_sink_dark", "stripped_acacia_kitchen_sink_dark", "stripped_dark_oak_kitchen_sink_dark");
		registerBlockColorHandler(event, (state, reader, pos, i) -> i == 1 ? 0x999999 : 0, "stripped_oak_park_bench", "stripped_spruce_park_bench", "stripped_birch_park_bench", "stripped_jungle_park_bench", "stripped_acacia_park_bench", "stripped_dark_oak_park_bench");
		registerBlockColorHandler(event, (state, reader, pos, i) -> i == 1 ? 0xCCCCCC : 0, "fridge_light", "freezer_light", "fridge_dark", "freezer_dark");
		registerBlockColorHandler(event, (state, reader, pos, i) -> 0x619961, "spruce_hedge");
		registerBlockColorHandler(event, (state, reader, pos, i) -> 8431445, "birch_hedge");
		registerBlockColorHandler(event, (state, reader, pos, i) -> reader != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(reader, pos) : ColorizerFoliage.getFoliageColorBasic(), "oak_hedge", "jungle_hedge", "acacia_hedge", "dark_oak_hedge");
	}

	@SubscribeEvent
	public void registerItemColorHandlers(ColorHandlerEvent.Item event) {
		registerItemColorHandler(event, (stack, i) -> i == 1 ? 0xCCCCCC : 0, "white_picket_fence", "orange_picket_fence", "magenta_picket_fence", "light_blue_picket_fence", "yellow_picket_fence", "lime_picket_fence", "pink_picket_fence", "gray_picket_fence", "light_gray_picket_fence", "cyan_picket_fence", "purple_picket_fence", "blue_picket_fence", "brown_picket_fence", "green_picket_fence", "red_picket_fence", "black_picket_fence", "white_picket_gate", "orange_picket_gate", "magenta_picket_gate", "light_blue_picket_gate", "yellow_picket_gate", "lime_picket_gate", "pink_picket_gate", "gray_picket_gate", "light_gray_picket_gate", "cyan_picket_gate", "purple_picket_gate", "blue_picket_gate", "brown_picket_gate", "green_picket_gate", "red_picket_gate", "black_picket_gate", "post_box");
		registerItemColorHandler(event, (stack, i) -> i == 1 ? 0xBBBBBB : 0, "stripped_oak_crate", "stripped_spruce_crate", "stripped_birch_crate", "stripped_jungle_crate", "stripped_acacia_crate", "stripped_dark_oak_crate", "stripped_oak_kitchen_counter", "stripped_spruce_kitchen_counter", "stripped_birch_kitchen_counter", "stripped_jungle_kitchen_counter", "stripped_acacia_kitchen_counter", "stripped_dark_oak_kitchen_counter", "stripped_oak_kitchen_drawer", "stripped_spruce_kitchen_drawer", "stripped_birch_kitchen_drawer", "stripped_jungle_kitchen_drawer", "stripped_acacia_kitchen_drawer", "stripped_dark_oak_kitchen_drawer", "stripped_oak_kitchen_sink_light", "stripped_spruce_kitchen_sink_light", "stripped_birch_kitchen_sink_light", "stripped_jungle_kitchen_sink_light", "stripped_acacia_kitchen_sink_light", "stripped_dark_oak_kitchen_sink_light", "stripped_oak_kitchen_sink_dark", "stripped_spruce_kitchen_sink_dark", "stripped_birch_kitchen_sink_dark", "stripped_jungle_kitchen_sink_dark", "stripped_acacia_kitchen_sink_dark", "stripped_dark_oak_kitchen_sink_dark");
		registerItemColorHandler(event, (stack, i) -> i == 1 ? 0x999999 : 0, "stripped_oak_park_bench", "stripped_spruce_park_bench", "stripped_birch_park_bench", "stripped_jungle_park_bench", "stripped_acacia_park_bench", "stripped_dark_oak_park_bench");
		registerItemColorHandler(event, (stack, i) -> i == 1 ? 0xCCCCCC : 0, "fridge_light", "freezer_light", "fridge_dark", "freezer_dark");
		registerItemColorHandler(event, (stack, i) -> {
			IBlockState state = ((ItemBlock) stack.getItem()).getBlock().getDefaultState();
			return Minecraft.getMinecraft().getBlockColors().getColor(state, null, null);
		}, "oak_hedge", "spruce_hedge", "birch_hedge", "jungle_hedge", "acacia_hedge", "dark_oak_hedge");
	}

	private static void registerBlockColorHandler(ColorHandlerEvent.Block event, IBlockColor iBlockColor, String... blocks) {
		event.getBlockColors().registerBlockColorHandler(iBlockColor, Arrays.stream(blocks).map(s -> ModBlocks.getBlockByKey("cb:" + s).getBlockHandle()).toArray(Block[]::new));
	}

	private static void registerItemColorHandler(ColorHandlerEvent.Item event, IItemColor iItemColor, String... blocks) {
		event.getItemColors().registerItemColorHandler(iItemColor, Arrays.stream(blocks).map(s -> ModBlocks.getBlockByKey("cb:" + s).getBlockHandle()).toArray(Block[]::new));
	}

	private static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, int id, T object) {
		try {
			addMethod.invoke(registry, id, object);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	static {
		ModBlocks.getBlocks().forEach((key, block) -> {
			VersionBlock mcBlock;
			Class<VersionBlock> versionBlockClass;
			VersionBlock.setRegisteringBlock(block);
			try {
				versionBlockClass = block.getVersionBlockClass() != null ? (Class<VersionBlock>) Class.forName("me.zer0pxl.cb.furniture.block.version_specific." + block.getVersionBlockClass()) : VersionBlock.class;
			} catch (ClassNotFoundException e) {
				System.err.println("Error during registration of " + key.getLocation());
				throw new RuntimeException("VersionBlockClass for " + key.getLocation() + " does not exist!");
			}

			try {
				mcBlock = versionBlockClass.getConstructor(ModBlock.class).newInstance(block);
			} catch (Throwable t) {
				System.err.println(block.getBlockKey().getLocation() + "threw " + t.getClass().getSimpleName());
				t.printStackTrace();
				return;
			}

			if (block.getLightLevel() > 0.0f)
				mcBlock.setLightLevel(block.getLightLevel());

			mcBlock.setRegistryName(key.getLocation());
			mcBlock.setTranslationKey(key.getLocation().split(":")[1]);
			mcBlock.setCreativeTab(FurnitureTab.INSTANCE);
			block.setBlockHandle(mcBlock);
			blocks.put(mcBlock, block);
		});

		try {
			addMethod = ForgeRegistry.class.getDeclaredMethod("add", int.class, IForgeRegistryEntry.class);
			addMethod.setAccessible(true);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

}
