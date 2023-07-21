package me.zer0pxl.cb.furniture.block.plants;

import me.zer0pxl.cb.furniture.block.type.DefaultHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;

public class PlantHorizontalBlock extends DefaultHorizontalBlock {

	public PlantHorizontalBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public String getVersionBlockClass() {
		return "PlantVersionBlock";
	}

}
