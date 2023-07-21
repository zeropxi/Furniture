package me.zer0pxl.cb.furniture.block;

import com.google.gson.JsonObject;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;

import java.util.List;

public class FurnitureBlock extends ModBlock {

	public static final PropertyBool OPEN = PropertyBool.create("open");
	protected boolean openable;

	public FurnitureBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public void initBlockData(JsonObject blockData) {
		super.initBlockData(blockData);
		openable = blockData.has("openable") && blockData.get("openable").getAsBoolean();
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		if (openable)
			properties.add(OPEN);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		if (openable)
			defaultState = defaultState.withProperty(OPEN, false);

		return defaultState;
	}

}
