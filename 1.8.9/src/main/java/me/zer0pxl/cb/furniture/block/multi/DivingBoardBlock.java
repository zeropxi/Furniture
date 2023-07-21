package me.zer0pxl.cb.furniture.block.multi;

import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;

import java.util.List;

import static me.zer0pxl.cb.furniture.block.multi.DivingBoardBlock.DivingBoardPart.BASE;
import static me.zer0pxl.cb.furniture.block.multi.DivingBoardBlock.DivingBoardPart.BOARD;

public class DivingBoardBlock extends FurnitureHorizontalBlock {

	public static final PropertyEnum<DivingBoardPart> PART = PropertyEnum.create("part", DivingBoardPart.class);

	public DivingBoardBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(PART, BASE);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(PART);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		DivingBoardPart divingBoardPart = (meta > 3) ? BOARD : BASE;
		if (meta > 3)
			meta -= 4;

		return Util.withProperties(getDefaultState(),
				DIRECTION, Util.getHorizontal(meta & 3),
				PART, divingBoardPart
		);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int direction = state.getValue(DIRECTION).ordinal() - 2;
		if (state.getValue(PART) == BOARD)
			direction += 4;

		return direction;
	}

	@Override
	public boolean isValidPosition(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPosition) {
		return blockAccess.getBlockState(blockPosition.offset(blockState.getValue(DIRECTION))).getBlock() == Blocks.air;
	}

	public enum DivingBoardPart implements IStringSerializable {

		BASE,
		BOARD;

		@Override
		public String toString() {
			return this.getName();
		}

		@Override
		public String getName() {
			return (this == BASE) ? "base" : "board";
		}

	}

}
