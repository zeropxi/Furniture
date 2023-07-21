package me.zer0pxl.cb.furniture.properties;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockProperties {

	public final Material material;
	public final MapColor materialColor;
	public final SoundType soundType;
	public final float resistance;
	public final float hardness;
	public final boolean fullCube;

	private BlockProperties(Material material, MapColor materialColor, SoundType soundType, float resistance, float hardness, boolean fullCube) {
		this.material = material;
		this.materialColor = materialColor;
		this.soundType = soundType;
		this.resistance = resistance;
		this.hardness = hardness;
		this.fullCube = fullCube;
	}

	public static BlockProperties fromJson(JsonObject object) {
		Material material = BlockPropertyConverter.getMaterial(object.get("material").getAsString());

		JsonElement materialColor = object.get("materialColor");
		MapColor mapColor = materialColor == null ? material.getMaterialMapColor() : BlockPropertyConverter.getMapColor(materialColor.getAsString());

		SoundType soundType = BlockPropertyConverter.getSoundType(object.get("soundType").getAsString());
		Hardness hardness = BlockPropertyConverter.getHardness(object.get("hardness"));
		boolean fullCube = object.has("fullCube") && object.get("fullCube").getAsBoolean();

		return new BlockProperties(material, mapColor, soundType, hardness.hardness, hardness.resistance, fullCube);
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
