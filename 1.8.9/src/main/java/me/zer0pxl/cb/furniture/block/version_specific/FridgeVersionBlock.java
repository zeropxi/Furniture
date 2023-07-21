package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.multi.FreezerBlock;
import me.zer0pxl.cb.furniture.block.multi.FridgeBlock;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class FridgeVersionBlock extends VersionBlock {

	public FridgeVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		IBlockState downState = worldIn.getBlockState(pos.down());

		if (Util.getCustomBlock(downState) instanceof FreezerBlock) {
			worldIn.setBlockState(pos.down(), Blocks.air.getDefaultState(), 35);
			worldIn.playAuxSFXAtEntity(player, 2001, pos.down(), getStateId(downState));
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	public Item getItem(World worldIn, BlockPos pos) {
		return Item.getItemFromBlock(((FridgeBlock) getModBlock()).getFreezerBlock().getBlockHandle());
	}

}
