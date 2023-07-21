package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.general.StairsBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import static net.minecraft.util.EnumFacing.DOWN;
import static net.minecraft.util.EnumFacing.UP;

public class StairsVersionBlock extends VersionBlock {

	public StairsVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState iblockstate = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(StairsBlock.SHAPE, StairsBlock.EnumShape.STRAIGHT);
		return iblockstate.withProperty(StairsBlock.HALF, facing != DOWN && (facing == UP || hitY <= 0.5) ? StairsBlock.EnumHalf.BOTTOM : StairsBlock.EnumHalf.TOP);
	}

}
