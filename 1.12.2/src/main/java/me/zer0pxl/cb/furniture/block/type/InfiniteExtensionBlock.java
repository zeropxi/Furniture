package me.zer0pxl.cb.furniture.block.type;

import me.zer0pxl.cb.furniture.block.FurnitureConnectingBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class InfiniteExtensionBlock extends FurnitureConnectingBlock {

	public InfiniteExtensionBlock(BlockProperties properties) {
		super(properties);
	}

	@Override
	public IBlockState getActualState(IBlockState state, BlockPos blockPosition, IBlockAccess world) {
		boolean north = world.getBlockState(blockPosition.north()).getBlock() == getBlockHandle();
		boolean east = world.getBlockState(blockPosition.east()).getBlock() == getBlockHandle();
		boolean south = world.getBlockState(blockPosition.south()).getBlock() == getBlockHandle();
		boolean west = world.getBlockState(blockPosition.west()).getBlock() == getBlockHandle();

		return Util.withProperties(state, NORTH, north, EAST, east, SOUTH, south, WEST, west);
	}

}
