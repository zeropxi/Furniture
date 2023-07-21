package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.multi.DivingBoardBlock;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DivingBoardVersionBlock extends VersionBlock {

	public DivingBoardVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		EnumFacing direction = state.getValue(FurnitureHorizontalBlock.DIRECTION);
		DivingBoardBlock.DivingBoardPart part = state.getValue(DivingBoardBlock.PART);
		BlockPos otherPos = pos.offset(part == DivingBoardBlock.DivingBoardPart.BASE ? direction : direction.getOpposite());
		IBlockState otherBlockState = worldIn.getBlockState(otherPos);

		if (otherBlockState.getBlock() == this && otherBlockState.getValue(DivingBoardBlock.PART) != part) {
			worldIn.setBlockState(otherPos, Blocks.air.getDefaultState(), 35);
			worldIn.playAuxSFXAtEntity(player, 2001, otherPos, getStateId(otherBlockState));
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos.offset(placer.getHorizontalFacing()), Util.withProperties(getDefaultState(),
				DivingBoardBlock.PART, DivingBoardBlock.DivingBoardPart.BOARD,
				FurnitureHorizontalBlock.DIRECTION, placer.getHorizontalFacing()
		), 3);
	}

}
