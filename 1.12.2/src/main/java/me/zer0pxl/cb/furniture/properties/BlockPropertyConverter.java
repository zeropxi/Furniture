package me.zer0pxl.cb.furniture.properties;

import com.google.gson.JsonElement;
import me.zer0pxl.cb.furniture.util.Reflector;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import java.util.Arrays;
import java.util.List;

public class BlockPropertyConverter {

	private static final List<String> COLORS = Arrays.asList("AIR", "GRASS", "SAND", "WOOL", "TNT", "ICE", "IRON", "FOLIAGE", "SNOW", "CLAY", "DIRT", "STONE", "WATER", "WOOD", "QUARTZ", "ADOBE", "MAGENTA", "LIGHT_BLUE", "YELLOW", "LIME", "PINK", "GRAY", "LIGHT_GRAY", "CYAN", "PURPLE", "BLUE", "BROWN", "GREEN", "RED", "BLACK", "GOLD", "DIAMOND", "LAPIS", "EMERALD", "OBSIDIAN", "NETHERRACK", "WHITE_TERRACOTTA", "ORANGE_TERRACOTTA", "MAGENTA_TERRACOTTA", "LIGHT_BLUE_TERRACOTTA", "YELLOW_TERRACOTTA", "LIME_TERRACOTTA", "PINK_TERRACOTTA", "GRAY_TERRACOTTA", "LIGHT_GRAY_TERRACOTTA", "CYAN_TERRACOTTA", "PURPLE_TERRACOTTA", "BLUE_TERRACOTTA", "BROWN_TERRACOTTA", "GREEN_TERRACOTTA", "RED_TERRACOTTA", "BLACK_TERRACOTTA", "CRIMSON_NYLIUM", "CRIMSON_STEM", "CRIMSON_HYPHAE", "WARPED_NYLIUM", "WARPED_STEM", "WARPED_HYPHAE", "WARPED_WART");

	public static final Material STONE = new Material(MapColor.STONE);
	public static final Material WOOD = new Material(MapColor.WOOD);
	public static final Material GOLD = new Material(MapColor.GOLD);
	public static final Material WOOL = new Material(MapColor.CLOTH);

	public static MapColor getMapColor(String name) {
		int index = COLORS.indexOf(name);
		if (index == -1)
			return null;

		MapColor color = MapColor.COLORS[index];
		return color == null ? MapColor.STONE : color;
	}

	public static Material getMaterial(String name) {
		switch (name) {
			case "WOOD":           return WOOD;
			case "STONE":          return STONE;
			case "STONE_ORIGINAL": return Material.ROCK;
			case "WOOL":           return WOOL;
			case "IRON":           return Material.IRON;
			case "ANVIL":          return Material.ANVIL;
			case "LEAVES":         return Material.LEAVES;
			case "GLASS":          return Material.GLASS;
			case "GOLD":           return GOLD;
			default:               return Material.WOOD;
		}
	}

	public static SoundType getSoundType(String name) {
		switch (name) {
			case "WOOD":  return SoundType.WOOD;
			case "ANVIL": return SoundType.ANVIL;
			case "CLOTH": return SoundType.CLOTH;
			case "METAL": return SoundType.METAL;
			case "PLANT": return SoundType.GROUND;
			case "GLASS": return SoundType.GLASS;
			default:      return SoundType.STONE;
		}
	}

	public static BlockProperties.Hardness getHardness(JsonElement element) {
		if (element == null)
			return new BlockProperties.Hardness(2f, 5f);

		switch (element.getAsString()) {
			case "LEAVES":      return new BlockProperties.Hardness(0.2f, 1.0f);
			case "SEA_LANTERN": return new BlockProperties.Hardness(0.3f, 1.5f);
			case "CARPET":      return new BlockProperties.Hardness(0.1f, 1 / 6f);
			case "IRON_BLOCK":  return new BlockProperties.Hardness(5.0f, 10.0f);
			case "GRASS":       return new BlockProperties.Hardness(0.6f, 3.0f);
			case "LOG":         return new BlockProperties.Hardness(2.0f, 10.0f);
			case "PLANT":       return new BlockProperties.Hardness(0.0f, 0.0f);
			default:            return new BlockProperties.Hardness(2.0f, 5.0f);
		}
	}

	static {
		Reflector.invoke(STONE, "setBurning", "func_76226_g");
	}

}
