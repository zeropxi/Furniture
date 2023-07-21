package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
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

public class TallGrassVersionBlock extends PlantyVersionBlock {

	public TallGrassVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		BlockProperties properties = modBlock.getBlockProperties();

		if (properties.hardness == 0 && properties.resistance == 0)
			return rand.nextInt(8) == 0 ? Items.WHEAT_SEEDS : Items.AIR;

		return Item.getItemFromBlock(this);
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
