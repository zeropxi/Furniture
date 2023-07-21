package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ModBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

import java.util.Objects;

public abstract class ContainerVersionBlock extends VersionBlock implements ITileEntityProvider {

	public ContainerVersionBlock(ModBlock modBlock) {
		super(modBlock);
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (!(te instanceof IWorldNameable) || !((IWorldNameable) te).hasCustomName()) {
			super.harvestBlock(worldIn, player, pos, state, null, stack);
			return;
		}

		player.addStat(Objects.requireNonNull(StatList.getBlockStats(this)));
		player.addExhaustion(0.005f);
		if (worldIn.isRemote)
			return;

		int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
		Item item = this.getItemDropped(state, worldIn.rand, fortune);
		if (item == Items.AIR)
			return;

		ItemStack itemstack = new ItemStack(item, this.quantityDropped(worldIn.rand));
		itemstack.setStackDisplayName(((IWorldNameable)te).getName());
		ContainerVersionBlock.spawnAsEntity(worldIn, pos, itemstack);
	}

	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int eventID, int eventParam) {
		super.eventReceived(state, worldIn, pos, eventID, eventParam);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		return tileentity != null && tileentity.receiveClientEvent(eventID, eventParam);
	}

}
