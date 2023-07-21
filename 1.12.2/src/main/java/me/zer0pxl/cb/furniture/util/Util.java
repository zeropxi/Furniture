package me.zer0pxl.cb.furniture.util;

import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class Util {

	public static ModBlock getCustomBlock(IBlockState state) {
		if (!(state.getBlock() instanceof VersionBlock))
			return null;

		return ((VersionBlock) state.getBlock()).getModBlock();
	}

	@SuppressWarnings("unchecked")
	public static <V extends Comparable<V>> IBlockState withProperties(IBlockState state, Object... args) {
		for (int i = 0; i < args.length;)
			state = state.withProperty((IProperty<V>) args[i++], (V) args[i++]);

		return state;
	}

	@SuppressWarnings("unchecked")
	public static <V extends Comparable<V>> IBlockState withSameValueProperties(IBlockState state, V value, IProperty<V>... properties) {
		for (IProperty<V> property : properties)
			state = state.withProperty(property, value);

		return state;
	}

	public static EnumFacing getHorizontal(int ordinal) {
		return EnumFacing.values()[(ordinal % 4 + 2)];
	}

}
