package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.multi.FreezerBlock;
import me.zer0pxl.cb.furniture.block.multi.FridgeBlock;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FridgeVersionBlock extends VersionBlock {

	public FridgeVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		IBlockState downState = worldIn.getBlockState(pos.down());

		if (Util.getCustomBlock(downState) instanceof FreezerBlock) {
			worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 35);
			worldIn.playEvent(player, 2001, pos.down(), getStateId(downState));
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(((FridgeBlock) this.getModBlock()).getFreezerBlock().getBlockHandle());
	}

}
