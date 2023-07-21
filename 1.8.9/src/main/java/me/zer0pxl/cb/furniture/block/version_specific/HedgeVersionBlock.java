package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.FurnitureConnectingBlock;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;

public class HedgeVersionBlock extends VersionBlock {

	public HedgeVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		boolean north = canConnectToBlock(world, pos, EnumFacing.NORTH);
		boolean east = canConnectToBlock(world, pos, EnumFacing.EAST);
		boolean south = canConnectToBlock(world, pos, EnumFacing.SOUTH);
		boolean west = canConnectToBlock(world, pos, EnumFacing.WEST);

		return Util.withProperties(state,
				FurnitureConnectingBlock.NORTH, north,
				FurnitureConnectingBlock.EAST, east,
				FurnitureConnectingBlock.SOUTH, south,
				FurnitureConnectingBlock.WEST, west
		);
	}

	private boolean canConnectToBlock(IBlockAccess world, BlockPos pos, EnumFacing direction) {
		BlockPos offsetPos = pos.offset(direction);
		IBlockState offsetState = world.getBlockState(offsetPos);

		return offsetState.getBlock() == this || offsetState.getBlock().isFullBlock();
	}

	public int getLightOpacity() {
		return 1;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics ? EnumWorldBlockLayer.CUTOUT : EnumWorldBlockLayer.SOLID;
	}

	@Override
	public boolean isFence() {
		return true;
	}

	public int getBlockColor() {
		return ColorizerFoliage.getFoliageColor(0.5, 1.0);
	}

	public int getRenderColor(IBlockState state) {
		return ColorizerFoliage.getFoliageColorBasic();
	}

	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		return BiomeColorHelper.getFoliageColorAtPos(worldIn, pos);
	}

}
