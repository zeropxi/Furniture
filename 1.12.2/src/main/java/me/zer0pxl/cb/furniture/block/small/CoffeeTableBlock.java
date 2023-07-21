package me.zer0pxl.cb.furniture.block.small;

import me.zer0pxl.cb.furniture.block.FurnitureBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Arrays;
import java.util.List;

public class CoffeeTableBlock extends FurnitureBlock {

	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool TALL = PropertyBool.create("tall");

	public CoffeeTableBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return Util.withSameValueProperties(defaultState, false, NORTH, EAST, SOUTH, WEST, TALL);
	}

	@Override
	public IBlockState getActualState(IBlockState state, BlockPos blockPosition, IBlockAccess world) {
		boolean tall = state.getValue(TALL);

		boolean north = isCoffeeTable(world, blockPosition, EnumFacing.NORTH, tall);
		boolean east = isCoffeeTable(world, blockPosition, EnumFacing.EAST, tall);
		boolean south = isCoffeeTable(world, blockPosition, EnumFacing.SOUTH, tall);
		boolean west = isCoffeeTable(world, blockPosition, EnumFacing.WEST, tall);

		return Util.withProperties(state, NORTH, north, EAST, east, SOUTH, south, WEST, west);
	}

	private boolean isCoffeeTable(IBlockAccess world, BlockPos source, EnumFacing EnumFacing, boolean tall) {
		IBlockState state = world.getBlockState(source.offset(EnumFacing));
		return Util.getCustomBlock(state) == this && state.getValue(TALL) == tall;
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.addAll(Arrays.asList(NORTH, EAST, SOUTH, WEST, TALL));
	}

	@Override
	public int getMetaFromState(IBlockState blockState) {
		return blockState.getValue(TALL) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return (meta == 1) ? getDefaultState().withProperty(TALL, true) : getDefaultState();
	}

}