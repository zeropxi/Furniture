package me.zer0pxl.cb.furniture.block.version_specific;

import com.google.common.collect.ImmutableMap;
import me.zer0pxl.cb.furniture.ModBlocks;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Random;

public class LeavesVersionBlock extends VersionBlock {

	private static final Map<String, String> BLOCK_TO_ITEM = ImmutableMap.of(
			"tile.white", "pink_sapling01",
			"tile.pink", "pink_sapling01",
			"tile.purple", "purple_sapling01",
			"tile.blue", "blue_sapling01"
	);

	public LeavesVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		BlockProperties properties = modBlock.getBlockProperties();
		if (properties.hardness == 0.2f && properties.resistance == 1)
			return getItemFromBlock();

		return super.getItemDropped(state, rand, fortune);
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
		if (worldIn.isRemote || player.getCurrentEquippedItem() == null || player.getCurrentEquippedItem().getItem() != Items.shears) {
			super.harvestBlock(worldIn, player, pos, state, te);
			return;
		}

		player.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
		spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1));
	}

	private Item getItemFromBlock() {
		for (Map.Entry<String, String> entry : BLOCK_TO_ITEM.entrySet())
			if (getUnlocalizedName().startsWith(entry.getKey()))
				return Item.getItemFromBlock(ModBlocks.getBlockByKey("cb:" + entry.getValue()).getBlockHandle());

		return Item.getItemFromBlock(this);
	}

	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		if (worldIn.isRemote)
			return;

		int dropChance = 20;
		if (fortune > 0)
			dropChance = Math.min(dropChance - 2 << fortune, 10);

		if (worldIn.rand.nextInt(dropChance) != 0)
			return;

		Item item = getItemDropped(state, worldIn.rand, fortune);
		spawnAsEntity(worldIn, pos, new ItemStack(item, 1, damageDropped(state)));
	}

	@Override
	public boolean isFullCube() {
		return true;
	}

}
