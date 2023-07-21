package me.zer0pxl.cb.furniture.block.plants;

import me.zer0pxl.cb.furniture.block.type.DefaultBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class LogBlock extends DefaultBlock {

	public static final PropertyEnum<BlockLog.EnumAxis> LOG_AXIS = PropertyEnum.create("axis", BlockLog.EnumAxis.class);

	public LogBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(LOG_AXIS);
	}

	@Override
	public IBlockState getActualState(IBlockState state, BlockPos blockPosition, IBlockAccess world) {
		BlockLog.EnumAxis axis = state.getValue(LOG_AXIS);
		return super.getActualState(state, blockPosition, world).withProperty(LOG_AXIS, axis);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState();

		switch (meta & 12) {
			case 0:
				return iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
			case 4:
				return iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
			case 8:
				return iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
			default:
				return iblockstate;
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		switch (state.getValue(LOG_AXIS)) {
			case X:
				return 4;
			case Y:
				return 0;
			default:
				return 8;
		}
	}

}