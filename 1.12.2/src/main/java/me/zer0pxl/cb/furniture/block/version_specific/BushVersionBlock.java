package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ModBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Random;

public class BushVersionBlock extends PlantyVersionBlock {

	public BushVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && this.canSustainBush(worldIn.getBlockState(pos.down()));
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (worldIn.isRemote || stack.getItem() != Items.SHEARS) {
			super.harvestBlock(worldIn, player, pos, state, te, stack);
			return;
		}

		player.addStat(Objects.requireNonNull(StatList.getBlockStats(this)));
		spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1));
	}

}
