package me.zer0pxl.cb.furniture.multipart;

import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConditionAnd implements ICondition {

	private final Iterable<ICondition> conditions;

	public ConditionAnd(Iterable<ICondition> conditionsIn) {
		this.conditions = conditionsIn;
	}

	@Override
	public Predicate<IBlockState> getPredicate(BlockState blockState) {
		return Predicates.and(StreamSupport.stream(conditions.spliterator(), false)
				.map(condition -> (condition == null) ? null : condition.getPredicate(blockState))
				.collect(Collectors.toList()));
	}

}
