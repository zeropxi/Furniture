package me.zer0pxl.cb.furniture.block.type;

import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;

import java.util.List;

import static me.zer0pxl.cb.furniture.block.type.HorizontalExtensionBlock.Type.*;

public class HorizontalExtensionBlock extends FurnitureHorizontalBlock {

	public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

	public HorizontalExtensionBlock(BlockProperties properties) {
		super(properties);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(TYPE, SINGLE);
	}

	@Override
	public IBlockState getActualState(IBlockState state, BlockPos blockPosition, IBlockAccess world) {
		EnumFacing dir = state.getValue(DIRECTION);
		boolean left = isBench(world, blockPosition, dir.rotateYCCW(), dir);
		boolean right = isBench(world, blockPosition, dir.rotateY(), dir);

		if (left && right) return state.withProperty(TYPE, MIDDLE);
		if (left) return state.withProperty(TYPE, RIGHT);
		if (right) return state.withProperty(TYPE, LEFT);
		return state.withProperty(TYPE, SINGLE);
	}

	private boolean isBench(IBlockAccess world, BlockPos source, EnumFacing direction, EnumFacing targetDirection) {
		IBlockState state = world.getBlockState(source.offset(direction));

		if (Util.getCustomBlock(state) == this)
			return state.getValue(DIRECTION).equals(targetDirection);

		return false;
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(TYPE);
	}

	public enum Type implements IStringSerializable {
		LEFT,
		MIDDLE,
		RIGHT,
		SINGLE;

		@Override
		public String getName() {
			return name().toLowerCase();
		}

	}

}