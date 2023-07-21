package me.zer0pxl.cb.furniture.block.fence;

import com.google.gson.JsonObject;
import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

import java.util.List;

import static me.zer0pxl.cb.furniture.block.fence.UpgradedGateBlock.DoorHingeSide.LEFT;
import static me.zer0pxl.cb.furniture.block.fence.UpgradedGateBlock.DoorHingeSide.RIGHT;

public class UpgradedGateBlock extends FurnitureHorizontalBlock {

	public static final PropertyEnum<DoorHingeSide> HINGE = PropertyEnum.create("hinge", DoorHingeSide.class);
	public static final PropertyBool DOUBLE = PropertyBool.create("double");

	public UpgradedGateBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public void initBlockData(JsonObject blockData) {
		super.initBlockData(blockData);
		openable = true;
	}

	@Override
	public int getMetaFromState(IBlockState blockState) {
		int meta = blockState.getValue(DIRECTION).ordinal() - 2;

		if (blockState.getValue(OPEN))
			meta |= 4;
		if (blockState.getValue(DOUBLE))
			meta |= 8;

		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		DoorHingeSide doorHingeSide = (meta & 4) == 0 ? LEFT : RIGHT;
		boolean open = (meta & 8) != 0;
		boolean isDouble = (meta & 16) != 0;

		return Util.withProperties(getDefaultState(),
				DIRECTION, Util.getHorizontal(meta & 3),
				HINGE, doorHingeSide,
				OPEN, open,
				DOUBLE, isDouble
		);
	}

	@Override
	public IBlockState getDefaultState(IBlockState defaultState) {
		return Util.withProperties(super.getDefaultState(defaultState),
				HINGE, LEFT,
				DOUBLE, false
		);
	}

	@Override
	public void addProperties(List<IProperty<?>> properties) {
		super.addProperties(properties);
		properties.add(HINGE);
		properties.add(DOUBLE);
	}

	public enum DoorHingeSide implements IStringSerializable {

		LEFT,
		RIGHT;

		@Override
		public String getName() {
			return name().toLowerCase();
		}

	}

}