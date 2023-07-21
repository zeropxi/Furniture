package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.FurnitureConnectingBlock;
import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.fence.UpgradedFenceBlock;
import me.zer0pxl.cb.furniture.block.fence.UpgradedGateBlock;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class UpgradedFenceVersionBlock extends VersionBlock {

	public UpgradedFenceVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		boolean north = canConnectToBlock(world, pos, EnumFacing.NORTH);
		boolean east = canConnectToBlock(world, pos, EnumFacing.EAST);
		boolean south = canConnectToBlock(world, pos, EnumFacing.SOUTH);
		boolean west = canConnectToBlock(world, pos, EnumFacing.WEST);
		boolean post = (!north || east || !south || west) && (north || !east || south || !west);

		return Util.withProperties(state, FurnitureConnectingBlock.NORTH, north, FurnitureConnectingBlock.EAST, east, FurnitureConnectingBlock.SOUTH, south, FurnitureConnectingBlock.WEST, west, UpgradedFenceBlock.POST, post);
	}

	private boolean canConnectToBlock(IBlockAccess world, BlockPos pos, EnumFacing direction) {
		BlockPos offsetPos = pos.offset(direction);
		IBlockState offsetState = world.getBlockState(offsetPos);
		if (Util.getCustomBlock(offsetState) == getModBlock())
			return true;

		if (!(Util.getCustomBlock(offsetState) instanceof UpgradedGateBlock))
			return offsetState.getBlock().isFullBlock();

		EnumFacing gateDirection = offsetState.getValue(FurnitureHorizontalBlock.DIRECTION);
		UpgradedGateBlock.DoorHingeSide hingeSide = offsetState.getValue(UpgradedGateBlock.HINGE);
		EnumFacing hingeFace = (hingeSide == UpgradedGateBlock.DoorHingeSide.LEFT) ? gateDirection.rotateYCCW() : gateDirection.rotateY();

		return direction == hingeFace.getOpposite() || (!offsetState.getValue(UpgradedGateBlock.DOUBLE) && direction.getAxis() != gateDirection.getAxis());
	}

	@Override
	public boolean isFence() {
		return true;
	}

	public int getLightOpacity() {
		return 1;
	}

}
