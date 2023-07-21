package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.block.ReplaceableBlock;
import me.zer0pxl.cb.furniture.block.small.CoffeeTableBlock;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CustomItemBlock extends ItemBlock {

	private final Block mcBlock;
	private final Block suppliedBlock;

	public CustomItemBlock(Block mcBlock, Block suppliedBlock) {
		super(suppliedBlock);
		this.mcBlock = mcBlock;
		this.suppliedBlock = suppliedBlock;
		setTranslationKey(mcBlock.getTranslationKey());
		setRegistryName(mcBlock.getRegistryName());
	}

	public Block getBlock() {
		return mcBlock;
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack;
		if (!(this.suppliedBlock instanceof VersionBlock))
			return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);

		VersionBlock versionBlock = (VersionBlock)this.suppliedBlock;
		if (this.suppliedBlock instanceof CoffeeTableVersionBlock)
			return this.onItemUseCoffeeTable(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);

		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		if (!block.isReplaceable(worldIn, pos))
			pos = pos.offset(facing);

		if ((itemstack = player.getHeldItem(hand)).isEmpty() || !player.canPlayerEdit(pos, facing, itemstack) || !worldIn.mayPlace(this.block, pos, false, facing, null))
			return EnumActionResult.FAIL;

		int i = this.getMetadata(itemstack.getMetadata());
		IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player);
		if (!versionBlock.getModBlock().isValidPosition(iblockstate1, worldIn, pos))
			return EnumActionResult.FAIL;

		if (worldIn.setBlockState(pos, iblockstate1, 11)) {
			iblockstate1 = worldIn.getBlockState(pos);
			if (iblockstate1.getBlock() == this.block) {
				CustomItemBlock.setTileEntityNBT(worldIn, player, pos, itemstack);
				this.block.onBlockPlacedBy(worldIn, pos, iblockstate1, player, itemstack);
				if (player instanceof EntityPlayerMP)
					CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, itemstack);
			}

			SoundType soundtype = this.block.getSoundType();
			worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
			itemstack.shrink(1);
		}
		return EnumActionResult.SUCCESS;
	}

	private EnumActionResult onItemUseCoffeeTable(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if ((!(block instanceof CoffeeTableVersionBlock)
				|| iblockstate.getValue(CoffeeTableBlock.TALL))
				&& !block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(facing);
			if (worldIn.getBlockState(pos).getBlock() != Blocks.AIR)
				return EnumActionResult.FAIL;
		}

		ItemStack itemstack = player.getHeldItem(hand);
		if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && this.mayPlace(worldIn, this.block, pos, facing)) {
			int i = this.getMetadata(itemstack.getMetadata());
			IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player);
			if (worldIn.setBlockState(pos, iblockstate1, 11)) {
				iblockstate1 = worldIn.getBlockState(pos);
				if (iblockstate1.getBlock() == this.block) {
					CustomItemBlock.setTileEntityNBT(worldIn, player, pos, itemstack);
					this.block.onBlockPlacedBy(worldIn, pos, iblockstate1, player, itemstack);
					if (player instanceof EntityPlayerMP) {
						CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)((Object)player), pos, itemstack);
					}
				}
				SoundType soundtype = this.block.getSoundType();
				worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
				itemstack.shrink(1);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}

	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		if (!(mcBlock instanceof CoffeeTableVersionBlock))
			return super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);

		IBlockState state = worldIn.getBlockState(pos);
		Block block = state.getBlock();

		if (block == Blocks.SNOW_LAYER) {
			side = EnumFacing.UP;
		} else {
			if (stack.getItem() instanceof ItemBlock
					&& block instanceof CoffeeTableVersionBlock
					&& !state.getValue(CoffeeTableBlock.TALL)
					&& !((ItemBlock)stack.getItem()).getBlock().equals(block))
				return false;


			if ((!(block instanceof CoffeeTableVersionBlock)
					|| state.getValue(CoffeeTableBlock.TALL))
					&& !block.isReplaceable(worldIn, pos))
				pos = pos.offset(side);
		}

		return mayPlace(worldIn, this.block, pos, side);
	}

	private boolean mayPlace(World world, Block blockIn, BlockPos pos, EnumFacing sidePlacedOn) {
		IBlockState iblockstate = world.getBlockState(pos);
		AxisAlignedBB bb = blockIn.getDefaultState().getCollisionBoundingBox(world, pos);

		if (bb != Block.NULL_AABB && !world.checkNoEntityCollision(bb.offset(pos), null))
			return false;

		if (iblockstate.getMaterial() == Material.CIRCUITS && blockIn == Blocks.ANVIL)
			return true;

		boolean replaceable = iblockstate.getBlock() instanceof ReplaceableBlock || iblockstate.getMaterial().isReplaceable();
		return replaceable && blockIn.canPlaceBlockOnSide(world, pos, sidePlacedOn);
	}

}
