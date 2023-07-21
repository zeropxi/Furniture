package me.zer0pxl.cb.furniture.block.kitchen;

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

import static me.zer0pxl.cb.furniture.block.kitchen.KitchenCounterBlock.Type.*;

public class KitchenCounterBlock extends FurnitureHorizontalBlock {

	public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

	public KitchenCounterBlock(BlockProperties properties) {
		super(properties);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(TYPE, DEFAULT);
	}

	@Override
	public IBlockState getActualState(IBlockState state, BlockPos blockPosition, IBlockAccess world) {
		EnumFacing direction = state.getValue(DIRECTION);
		IBlockState frontState = world.getBlockState(blockPosition.offset(direction.getOpposite()));
		if (Util.getCustomBlock(frontState) instanceof KitchenCounterBlock) {
			if (frontState.getValue(DIRECTION) == direction.rotateY())
				return state.withProperty(TYPE, RIGHT_CORNER);
			if (frontState.getValue(DIRECTION) == direction.rotateYCCW())
				return state.withProperty(TYPE, LEFT_CORNER);
		}

		IBlockState backState = world.getBlockState(blockPosition.offset(direction));
		if (Util.getCustomBlock(backState) instanceof KitchenCounterBlock) {
			if (backState.getValue(DIRECTION) == direction.rotateY()) {
				IBlockState leftState = world.getBlockState(blockPosition.offset(direction.rotateYCCW()));
				if (!(Util.getCustomBlock(leftState) instanceof KitchenCounterBlock) || leftState.getValue(DIRECTION) == direction.getOpposite())
					return state.withProperty(TYPE, LEFT_CORNER_INVERTED);
			}

			if (backState.getValue(DIRECTION) == direction.rotateYCCW()) {
				IBlockState rightState = world.getBlockState(blockPosition.offset(direction.rotateY()));
				if (!(Util.getCustomBlock(rightState) instanceof KitchenCounterBlock) || rightState.getValue(DIRECTION) == direction.getOpposite())
					return state.withProperty(TYPE, RIGHT_CORNER_INVERTED);
			}
		}

		return state.withProperty(TYPE, DEFAULT);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(TYPE);
	}

	public enum Type implements IStringSerializable {

		DEFAULT,
		LEFT_CORNER,
		RIGHT_CORNER,
		LEFT_CORNER_INVERTED,
		RIGHT_CORNER_INVERTED;

		@Override
		public String getName() {
			return name().toLowerCase();
		}

	}

}
