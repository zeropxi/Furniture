package me.zer0pxl.cb.furniture.block.general;

import me.zer0pxl.cb.furniture.block.FurnitureConnectingBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Arrays;
import java.util.List;

public class TrampolineBlock extends FurnitureConnectingBlock {

	public static final PropertyBool CORNER_NORTH_WEST = PropertyBool.create("corner_north_west");
	public static final PropertyBool CORNER_NORTH_EAST = PropertyBool.create("corner_north_east");
	public static final PropertyBool CORNER_SOUTH_EAST = PropertyBool.create("corner_south_east");
	public static final PropertyBool CORNER_SOUTH_WEST = PropertyBool.create("corner_south_west");

	public TrampolineBlock(BlockProperties properties) {
		super(properties);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return Util.withSameValueProperties(super.getDefaultState(defaultState), false, CORNER_NORTH_WEST, CORNER_NORTH_EAST, CORNER_SOUTH_EAST, CORNER_SOUTH_WEST);
	}

	@Override
	public IBlockState getActualState(IBlockState state, BlockPos pos, IBlockAccess world) {
		boolean north = isBlockHandle(world, pos.north());
		boolean east = isBlockHandle(world, pos.east());
		boolean south = isBlockHandle(world, pos.south());
		boolean west = isBlockHandle(world, pos.west());

		boolean cornerNorthWest = north && west && !isBlockHandle(world, pos.north().west());
		boolean cornerNorthEast = north && east && !isBlockHandle(world, pos.north().east());
		boolean cornerSouthEast = south && east && !isBlockHandle(world, pos.south().east());
		boolean cornerSouthWest = south && west && !isBlockHandle(world, pos.south().west());

		return Util.withProperties(state,
				NORTH, north,
				EAST, east,
				SOUTH, south,
				WEST, west,
				CORNER_NORTH_WEST, cornerNorthWest,
				CORNER_NORTH_EAST, cornerNorthEast,
				CORNER_SOUTH_EAST, cornerSouthEast,
				CORNER_SOUTH_WEST, cornerSouthWest
		);
	}

	private boolean isBlockHandle(IBlockAccess world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() == getBlockHandle();
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.addAll(Arrays.asList(CORNER_NORTH_WEST, CORNER_NORTH_EAST, CORNER_SOUTH_EAST, CORNER_SOUTH_WEST));
	}

}
