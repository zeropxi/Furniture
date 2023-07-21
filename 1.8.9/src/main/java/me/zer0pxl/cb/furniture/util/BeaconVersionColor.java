package me.zer0pxl.cb.furniture.util;

import me.zer0pxl.cb.furniture.block.beacon.BeaconColor;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;

public enum BeaconVersionColor {

	DEFAULT(BeaconColor.DEFAULT, EnumDyeColor.BLUE),
	ORANGE(BeaconColor.ORANGE, EnumDyeColor.ORANGE),
	PURPLE(BeaconColor.PURPLE, EnumDyeColor.PURPLE),
	BLACK(BeaconColor.BLACK, EnumDyeColor.BLACK),
	RAINBOW();

	private final BeaconColor color;
	private final float[] rgbColor;
	private final boolean isRainbow;

	BeaconVersionColor(BeaconColor beaconColor, EnumDyeColor EnumDyeColor) {
		color = beaconColor;
		rgbColor = EntitySheep.getDyeRgb(EnumDyeColor);
		isRainbow = false;
	}

	BeaconVersionColor() {
		this.color = BeaconColor.RAINBOW;
		this.rgbColor = EntitySheep.getDyeRgb(EnumDyeColor.BLACK);
		this.isRainbow = true;
	}

	public static BeaconVersionColor of(BeaconColor beaconColor) {
		for (BeaconVersionColor value : values())
			if (value.color.equals(beaconColor))
				return value;

		return BeaconVersionColor.DEFAULT;
	}

	public float[] getRgbColor() {
		return rgbColor;
	}

	public boolean isRainbow() {
		return isRainbow;
	}

}
