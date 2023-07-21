package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.ReplaceableBlock;
import me.zer0pxl.cb.furniture.block.small.CoffeeTableBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class CoffeeTableVersionBlock extends VersionBlock implements ReplaceableBlock {

	public CoffeeTableVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState state = worldIn.getBlockState(pos);

		return state.getBlock() == this
				? state.withProperty(CoffeeTableBlock.TALL, true)
				: super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
	}

	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		if (worldIn.isRemote)
			return;

		for (int quantity = state.getValue(CoffeeTableBlock.TALL) ? 2 : 1, j = 0; j < quantity; ++j) {
			if (worldIn.rand.nextFloat() <= chance) {
				Item item = getItemDropped(state, worldIn.rand, fortune);
				if (item != null)
					spawnAsEntity(worldIn, pos, new ItemStack(item, 1, damageDropped(state)));
			}
		}
	}

}
