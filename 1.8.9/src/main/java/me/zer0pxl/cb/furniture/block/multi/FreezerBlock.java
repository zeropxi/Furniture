package me.zer0pxl.cb.furniture.block.multi;

import com.google.gson.JsonObject;
import me.zer0pxl.cb.furniture.ModBlocks;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.type.DefaultHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;

public class FreezerBlock extends DefaultHorizontalBlock {

	private ModBlock fridgeBlock;

	public FreezerBlock(BlockProperties properties) {
		super(properties);
	}

	@Override
	public void initBlockData(JsonObject blockData) {
		super.initBlockData(blockData);
		openable = true;
		fridgeBlock = ModBlocks.getBlockByKey("cb:" + blockData.getAsJsonPrimitive("fridge").getAsString());
	}

	@Override
	public boolean hasItem() {
		return false;
	}

	public ModBlock getFridgeBlock() {
		return fridgeBlock;
	}

}
