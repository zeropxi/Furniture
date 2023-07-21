package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.small.BlindsBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import static net.minecraft.util.EnumFacing.DOWN;
import static net.minecraft.util.EnumFacing.UP;

public class BlindsVersionBlock extends VersionBlock {

	public BlindsVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
		toggleBlinds(world, pos, !state.getValue(BlindsBlock.OPEN), state.getValue(FurnitureHorizontalBlock.DIRECTION), 7);

		if (!world.isRemote)
			world.playSoundEffect(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, "cb:block.blinds." + (state.getValue(BlindsBlock.OPEN) ? "close" : "open"), 0.5f, world.rand.nextFloat() * 0.1f + 0.8f);

		return true;
	}

	private void toggleBlinds(World world, BlockPos pos, boolean targetOpen, EnumFacing targetDirection, int depth) {
		if (depth <= 0)
			return;

		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() != this)
			return;

		boolean open = state.getValue(BlindsBlock.OPEN);
		EnumFacing direction = state.getValue(FurnitureHorizontalBlock.DIRECTION);

		if (open == targetOpen || !direction.equals(targetDirection))
			return;

		world.setBlockState(pos, state.withProperty(BlindsBlock.OPEN, targetOpen), 3);
		toggleBlinds(world, pos.offset(targetDirection.rotateY()), targetOpen, targetDirection, depth - 1);
		toggleBlinds(world, pos.offset(targetDirection.rotateYCCW()), targetOpen, targetDirection, depth - 1);
		toggleBlinds(world, pos.offset(UP), targetOpen, targetDirection, depth - 1);
		toggleBlinds(world, pos.offset(DOWN), targetOpen, targetDirection, depth - 1);
	}

}
