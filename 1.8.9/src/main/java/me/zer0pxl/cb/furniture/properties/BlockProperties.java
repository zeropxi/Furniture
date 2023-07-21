package me.zer0pxl.cb.furniture.properties;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockProperties {

	public final Material material;
	public final MapColor materialColor;
	public final Block.SoundType soundType;
	public final float resistance;
	public final float hardness;

	private BlockProperties(Material material, MapColor materialColor, Block.SoundType soundType, float resistance, float hardness) {
		this.material = material;
		this.materialColor = materialColor;
		this.soundType = soundType;
		this.resistance = resistance;
		this.hardness = hardness;
	}

	public static BlockProperties fromJson(JsonObject object) {
		Material material = BlockPropertyConverter.getMaterial(object.get("material").getAsString());

		JsonElement materialColor = object.get("materialColor");
		MapColor mapColor = materialColor == null ? material.getMaterialMapColor() : BlockPropertyConverter.getMapColor(materialColor.getAsString());

		Block.SoundType soundType = BlockPropertyConverter.getSoundType(object.get("soundType").getAsString());
		Hardness hardness = BlockPropertyConverter.getHardness(object.get("hardness"));

		return new BlockProperties(material, mapColor, soundType, hardness.hardness, hardness.resistance);
	}

	public static class Hardness {

		private final float hardness;
		private final float resistance;

		public Hardness(float hardness, float resistance) {
			this.hardness = hardness;
			this.resistance = resistance;
		}

	}

}
