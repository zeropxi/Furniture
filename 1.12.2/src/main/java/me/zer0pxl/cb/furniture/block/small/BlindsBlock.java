package me.zer0pxl.cb.furniture.block.small;

import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class BlindsBlock extends FurnitureHorizontalBlock {

	public static final PropertyBool OPEN = PropertyBool.create("open");
	public static final PropertyBool EXTENSION = PropertyBool.create("extension");

	public BlindsBlock(BlockProperties properties) {
		super(properties);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(OPEN, true).withProperty(EXTENSION, false);
	}

	@Override
	public IBlockState getActualState(IBlockState state, BlockPos blockPosition, IBlockAccess world) {
		IBlockState aboveState = world.getBlockState(blockPosition.up());
		boolean isExtension = Util.getCustomBlock(aboveState) == this && aboveState.getValue(DIRECTION) == state.getValue(DIRECTION);
		return state.withProperty(EXTENSION, isExtension);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(OPEN);
		properties.add(EXTENSION);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean open = meta > 3;
		if (meta > 3)
			meta -= 4;

		return this.getDefaultState().withProperty(DIRECTION, Util.getHorizontal(meta & 3)).withProperty(OPEN, open);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int direction = state.getValue(DIRECTION).ordinal() - 2;
		if (state.getValue(OPEN))
			direction += 4;

		return direction;
	}

}