package me.zer0pxl.cb.furniture.block;

import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;

import java.util.Arrays;
import java.util.List;

public class FurnitureConnectingBlock extends FurnitureBlock {

	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	public FurnitureConnectingBlock(BlockProperties properties) {
		super(properties);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return Util.withSameValueProperties(super.getDefaultState(defaultState), false, NORTH, EAST, SOUTH, WEST);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.addAll(Arrays.asList(NORTH, EAST, SOUTH, WEST));
	}

}
