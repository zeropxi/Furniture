package me.zer0pxl.cb.furniture.multipart;

import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

import java.util.function.Predicate;

public interface ICondition {

	ICondition TRUE = blockState -> b -> true;

	Predicate<IBlockState> getPredicate(BlockState p0);

}
