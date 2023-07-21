package me.zer0pxl.cb.furniture.block.multi;

import com.google.gson.JsonObject;
import me.zer0pxl.cb.furniture.ModBlocks;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.type.DefaultHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;

public class FridgeBlock extends DefaultHorizontalBlock {

	private ModBlock freezerBlock;

	public FridgeBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public void initBlockData(JsonObject blockData) {
		super.initBlockData(blockData);
		openable = true;
		freezerBlock = ModBlocks.getBlockByKey("cb:" + blockData.getAsJsonPrimitive("freezer").getAsString());
	}

	@Override
	public ModBlock getBlockToSupplyToItem() {
		return freezerBlock;
	}

	public ModBlock getFreezerBlock() {
		return freezerBlock;
	}

}
