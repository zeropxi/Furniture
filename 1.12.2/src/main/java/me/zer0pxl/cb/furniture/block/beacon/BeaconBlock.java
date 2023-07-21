package me.zer0pxl.cb.furniture.block.beacon;

import com.google.gson.JsonObject;
import me.zer0pxl.cb.furniture.block.type.DefaultBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import net.minecraft.block.properties.IProperty;

import java.util.List;

public class BeaconBlock extends DefaultBlock {

	private BeaconColor beaconColor;

	@Override
	public void initBlockData(JsonObject blockData) {
		super.initBlockData(blockData);
		beaconColor = (blockData.has("beaconColor")
				? BeaconColor.valueOf(blockData.get("beaconColor").getAsString().toUpperCase())
				: BeaconColor.DEFAULT);
	}

	public BeaconBlock(BlockProperties properties) {
		super(properties);
		openable = true;
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
	}

	public BeaconColor getBeaconColor() {
		return this.beaconColor;
	}

}
