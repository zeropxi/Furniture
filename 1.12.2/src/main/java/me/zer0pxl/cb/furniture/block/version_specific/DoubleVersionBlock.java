package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.type.DoubleBlock;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static me.zer0pxl.cb.furniture.block.type.DoubleBlock.DoublePart.LEFT;
import static me.zer0pxl.cb.furniture.block.type.DoubleBlock.DoublePart.RIGHT;
import static me.zer0pxl.cb.furniture.block.type.DoubleBlock.PART;

public class DoubleVersionBlock extends VersionBlock {

	public DoubleVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		EnumFacing direction = state.getValue(FurnitureHorizontalBlock.DIRECTION);
		DoubleBlock.DoublePart part = state.getValue(PART);
		EnumFacing leftDirection = direction.rotateYCCW();
		BlockPos otherPosition = pos.offset(part == LEFT ? leftDirection.getOpposite() : leftDirection);
		IBlockState otherBlockState = worldIn.getBlockState(otherPosition);

		if (otherBlockState.getBlock() == this && otherBlockState.getValue(PART) != part) {
			worldIn.setBlockState(otherPosition, Blocks.AIR.getDefaultState(), 3);
			worldIn.playEvent(player, 2001, otherPosition, getStateId(otherBlockState));
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		EnumFacing targetDirection = placer.getHorizontalFacing();
		EnumFacing leftDirection = targetDirection.rotateYCCW();

		worldIn.setBlockState(pos.offset(leftDirection.getOpposite()), Util.withProperties(getDefaultState(),
				PART, RIGHT,
				FurnitureHorizontalBlock.DIRECTION, targetDirection
		), 3);
	}

}
