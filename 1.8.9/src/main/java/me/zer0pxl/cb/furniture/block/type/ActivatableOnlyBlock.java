package me.zer0pxl.cb.furniture.block.type;

import me.zer0pxl.cb.furniture.properties.BlockProperties;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;

import java.util.List;

public class ActivatableOnlyBlock extends DefaultBlock {

	public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

	public ActivatableOnlyBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(ACTIVATED);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(ACTIVATED, false);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean activated = meta > 3;
		return this.getDefaultState().withProperty(ACTIVATED, activated);
	}

	@Override
	public int getMetaFromState(IBlockState blockState) {
		return blockState.getValue(ACTIVATED) ? 0 : 4;
	}

}
