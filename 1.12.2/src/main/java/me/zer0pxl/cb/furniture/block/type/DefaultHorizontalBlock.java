package me.zer0pxl.cb.furniture.block.type;

import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import me.zer0pxl.cb.furniture.util.Util;
import net.minecraft.block.state.IBlockState;

public class DefaultHorizontalBlock extends FurnitureHorizontalBlock {

	public DefaultHorizontalBlock(BlockProperties blockProperties) {
		super(blockProperties);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if (!openable)
			return super.getStateFromMeta(meta);

		return Util.withProperties(getDefaultState(),
				DIRECTION, Util.getHorizontal(meta & 3),
				OPEN, ((meta & 4) != 0)
		);
	}

	@Override
	public int getMetaFromState(IBlockState blockState) {
		if (!openable)
			return super.getMetaFromState(blockState);

		int meta = super.getMetaFromState(blockState);
		if (blockState.getValue(DefaultHorizontalBlock.OPEN))
			meta |= 4;

		return meta;
	}

}
