package me.zer0pxl.cb.furniture.block.type;

import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;

import java.util.List;

public class ActivatableBlock extends FurnitureHorizontalBlock {

	public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

	public ActivatableBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(ActivatableBlock.ACTIVATED);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(ActivatableBlock.ACTIVATED, false);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta % 4).withProperty(ActivatableBlock.ACTIVATED, meta > 3);
	}

	@Override
	public int getMetaFromState(IBlockState blockState) {
		int meta = super.getMetaFromState(blockState);
		if (blockState.getValue(ActivatableBlock.ACTIVATED))
			meta += 4;

		return meta;
	}

}
