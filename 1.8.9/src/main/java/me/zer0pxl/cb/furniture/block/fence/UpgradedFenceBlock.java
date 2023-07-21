package me.zer0pxl.cb.furniture.block.fence;

import me.zer0pxl.cb.furniture.block.FurnitureConnectingBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;

import java.util.List;

public class UpgradedFenceBlock extends FurnitureConnectingBlock {

	public static final PropertyBool POST = PropertyBool.create("post");

	public UpgradedFenceBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(POST, false);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(POST);
	}

}
