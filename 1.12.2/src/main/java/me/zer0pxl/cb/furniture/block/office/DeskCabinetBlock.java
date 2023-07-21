package me.zer0pxl.cb.furniture.block.office;

import me.zer0pxl.cb.furniture.properties.BlockProperties;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;

import java.util.List;

public class DeskCabinetBlock extends DeskBlock {

	public static final PropertyBool OPEN = PropertyBool.create("open");

	public DeskCabinetBlock(BlockProperties properties) {
		super(properties);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(OPEN, false);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(OPEN);
	}

}
