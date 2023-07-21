package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.FurnitureBlock;
import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.general.TrapDoorBlock;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static net.minecraft.util.EnumFacing.UP;

public class TrapDoorVersionBlock extends VersionBlock {

	public TrapDoorVersionBlock(ModBlock modBlock) {
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

	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return !worldIn.getBlockState(pos).getValue(FurnitureBlock.OPEN);
	}

	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (worldIn.isRemote)
			return;

		boolean powered = worldIn.isBlockPowered(pos);
		if ((!powered && !neighborBlock.canProvidePower()) || state.getValue(FurnitureBlock.OPEN) != powered)
			return;

		worldIn.setBlockState(pos, state.withProperty(FurnitureBlock.OPEN, powered), 2);
		playSound(null, worldIn, pos, powered);
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean open = !state.getValue(FurnitureBlock.OPEN);
		state = state.withProperty(FurnitureBlock.OPEN, open);

		worldIn.setBlockState(pos, state, 2);
		playSound(playerIn, worldIn, pos, open);
		return true;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState iblockstate = getDefaultState();

		if (facing.getAxis().isHorizontal()) {
			iblockstate = Util.withProperties(iblockstate,
					FurnitureHorizontalBlock.DIRECTION, facing,
					FurnitureBlock.OPEN, false,
					TrapDoorBlock.HALF, hitY > 0.5f ? TrapDoorBlock.DoorHalf.TOP : TrapDoorBlock.DoorHalf.BOTTOM
			);
		} else {
			iblockstate = Util.withProperties(iblockstate,
					FurnitureHorizontalBlock.DIRECTION, placer.getHorizontalFacing().getOpposite(),
					FurnitureBlock.OPEN, false,
					TrapDoorBlock.HALF, facing == UP ? TrapDoorBlock.DoorHalf.BOTTOM : TrapDoorBlock.DoorHalf.TOP
			);
		}

		return worldIn.isBlockPowered(pos) ? iblockstate.withProperty(FurnitureBlock.OPEN, true) : iblockstate;
	}

	protected void playSound(EntityPlayer player, World worldIn, BlockPos pos, boolean open) {
		worldIn.playAuxSFXAtEntity(player, open ? 1003 : 1006, pos, 0);
	}

}