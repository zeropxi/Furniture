package me.zer0pxl.cb.furniture.block.type;

import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.List;

import static me.zer0pxl.cb.furniture.block.type.DoubleBlock.DoublePart.LEFT;

public class DoubleBlock extends FurnitureHorizontalBlock {

	public static final PropertyEnum<DoublePart> PART = PropertyEnum.create("part", DoublePart.class);

	public DoubleBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(PART, LEFT);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(PART);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if (meta > 7)
			meta = 7;

		return Util.withProperties(getDefaultState(),
				DIRECTION, Util.getHorizontal(meta % 4),
				PART, DoublePart.values()[meta / 4]
		);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(PART).ordinal() * 4 + state.getValue(DIRECTION).ordinal() - 2;
	}

	@Override
	public String getVersionBlockClass() {
		return "DoubleVersionBlock";
	}

	@Override
	public boolean isValidPosition(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPosition) {
		EnumFacing direction = blockState.getValue(DIRECTION);
		return blockAccess.getBlockState(blockPosition.offset(direction.rotateYCCW().getOpposite())).getBlock() == Blocks.AIR;
	}

	public enum DoublePart implements IStringSerializable {
		LEFT,
		RIGHT;

		@Override
		public String getName() {
			return name().toLowerCase();
		}

	}

}
