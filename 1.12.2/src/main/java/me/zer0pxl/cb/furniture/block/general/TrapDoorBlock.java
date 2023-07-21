package me.zer0pxl.cb.furniture.block.general;

import me.zer0pxl.cb.furniture.block.type.DefaultHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

import static java.util.Collections.singletonList;
import static me.zer0pxl.cb.furniture.block.general.TrapDoorBlock.DoorHalf.BOTTOM;
import static me.zer0pxl.cb.furniture.block.general.TrapDoorBlock.DoorHalf.TOP;

public class TrapDoorBlock extends DefaultHorizontalBlock {

	public static final PropertyEnum<DoorHalf> HALF = PropertyEnum.create("half", DoorHalf.class);
	protected static final AxisAlignedBB EAST_OPEN_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.1875, 1.0, 1.0);
	protected static final AxisAlignedBB WEST_OPEN_AABB = new AxisAlignedBB(0.8125, 0.0, 0.0, 1.0, 1.0, 1.0);
	protected static final AxisAlignedBB SOUTH_OPEN_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.1875);
	protected static final AxisAlignedBB NORTH_OPEN_AABB = new AxisAlignedBB(0.0, 0.0, 0.8125, 1.0, 1.0, 1.0);
	protected static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.1875, 1.0);
	protected static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.0, 0.8125, 0.0, 1.0, 1.0, 1.0);

	public TrapDoorBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	protected static EnumFacing getFacing(int meta) {
		switch (meta & 3) {
			case 0:
				return EnumFacing.NORTH;
			case 1:
				return EnumFacing.SOUTH;
			case 2:
				return EnumFacing.WEST;
			default:
				return EnumFacing.EAST;
		}
	}

	protected static int getMetaForFacing(EnumFacing facing) {
		switch (facing) {
			case NORTH:
				return 0;
			case SOUTH:
				return 1;
			case WEST:
				return 2;
			default:
				return 3;
		}
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(HALF);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return super.getDefaultState(defaultState).withProperty(HALF, BOTTOM);
	}

	@Override
	public List<AxisAlignedBB> getShapes(IBlockState blockState, List<AxisAlignedBB> providedShapes) {
		if (!blockState.getValue(OPEN))
			return singletonList(blockState.getValue(HALF) == TOP ? TOP_AABB : BOTTOM_AABB);

		switch (blockState.getValue(DIRECTION)) {
			case SOUTH:
				return singletonList(SOUTH_OPEN_AABB);
			case WEST:
				return singletonList(WEST_OPEN_AABB);
			case EAST:
				return singletonList(EAST_OPEN_AABB);
			default:
				return singletonList(NORTH_OPEN_AABB);
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return Util.withProperties(getDefaultState(),
				DIRECTION, getFacing(meta),
				OPEN, (meta & 4) != 0,
				HALF, (meta & 8) == 0 ? BOTTOM : TOP
		);
	}

	@Override
	public int getMetaFromState(IBlockState blockState) {
		int meta = getMetaForFacing(blockState.getValue(DIRECTION));

		if (blockState.getValue(OPEN))
			meta |= 4;
		if (blockState.getValue(HALF) == TOP)
			meta |= 8;

		return meta;
	}

	public enum DoorHalf implements IStringSerializable {

		TOP,
		BOTTOM;

		@Override
		public String getName() {
			return name().toLowerCase();
		}

	}

}