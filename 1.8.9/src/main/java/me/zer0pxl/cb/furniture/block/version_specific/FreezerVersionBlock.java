package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.multi.FreezerBlock;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class FreezerVersionBlock extends VersionBlock {

	public FreezerVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.isAirBlock(pos.up());
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos.up(), (((FreezerBlock) getModBlock()).getFridgeBlock().getBlockHandle()).getDefaultState().withProperty(FurnitureHorizontalBlock.DIRECTION, placer.getHorizontalFacing()));
	}

	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		IBlockState upState = worldIn.getBlockState(pos.up());

		if (Util.getCustomBlock(upState) instanceof FreezerBlock) {
			worldIn.setBlockState(pos.up(), Blocks.air.getDefaultState(), 35);
			worldIn.playAuxSFXAtEntity(player, 2001, pos.up(), getStateId(upState));
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

}
