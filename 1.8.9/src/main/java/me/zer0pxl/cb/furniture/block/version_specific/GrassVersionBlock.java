package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ModBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.util.Random;

import static net.minecraft.block.BlockDirt.DirtType.DIRT;
import static net.minecraft.block.BlockDirt.VARIANT;

public class GrassVersionBlock extends VersionBlock {

	public GrassVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(VARIANT, DIRT), rand, fortune);
	}

	@Override
	public boolean isFullCube() {
		return true;
	}

}
