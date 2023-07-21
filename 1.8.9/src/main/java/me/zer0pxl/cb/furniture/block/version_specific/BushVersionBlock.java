package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ModBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BushVersionBlock extends PlantyVersionBlock {

	public BushVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
		if (worldIn.isRemote || player.getCurrentEquippedItem() == null || player.getCurrentEquippedItem().getItem() != Items.shears) {
			super.harvestBlock(worldIn, player, pos, state, te);
			return;
		}

		player.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
		spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1));
	}

}
