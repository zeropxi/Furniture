package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.FurnitureConnectingBlock;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class HedgeVersionBlock extends VersionBlock {

	public HedgeVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		boolean north = canConnectToBlock(world, pos, EnumFacing.NORTH);
		boolean east = canConnectToBlock(world, pos, EnumFacing.EAST);
		boolean south = canConnectToBlock(world, pos, EnumFacing.SOUTH);
		boolean west = canConnectToBlock(world, pos, EnumFacing.WEST);

		return Util.withProperties(state,
				FurnitureConnectingBlock.NORTH, north,
				FurnitureConnectingBlock.EAST, east,
				FurnitureConnectingBlock.SOUTH, south,
				FurnitureConnectingBlock.WEST, west
		);
	}

	private boolean canConnectToBlock(IBlockAccess world, BlockPos pos, EnumFacing direction) {
		BlockPos offsetPos = pos.offset(direction);
		IBlockState offsetState = world.getBlockState(offsetPos);

		return offsetState.getBlock() == this || offsetState.isFullBlock();
	}

	public int getLightOpacity(IBlockState state) {
		return 1;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID;
	}

	@Override
	public PathNodeType getPathNodeType(IBlockState state) {
		return PathNodeType.FENCE;
	}

}
