package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ModBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class PlantyVersionBlock extends VersionBlock {

	public PlantyVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public boolean canSustainBush(IBlockState state) {
		return (state.getBlock() instanceof VersionBlock && ((VersionBlock) state.getBlock()).getModBlock().isCanSustainBush())
				|| state.getBlock() == Blocks.grass
				|| state.getBlock() == Blocks.dirt
				|| state.getBlock() == Blocks.farmland;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && canSustainBush(worldIn.getBlockState(pos.down()));
	}

}
