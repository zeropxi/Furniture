package me.zer0pxl.cb.asm.mixins;

import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.renderer.block.model.VariantList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(ModelBlockDefinition.class)
public class MixinModelBlockDefinition {

	@Shadow
	@Final
	private Map<String, VariantList> mapVariants;

	@Inject(method = "getVariant", at = @At("HEAD"), cancellable = true)
	public void injectGetVariant(String variantName, CallbackInfoReturnable<VariantList> callbackInfoReturnable) throws ModelBlockDefinition.MissingVariantException {
		VariantList variantlist = mapVariants.get(variantName);
		if (variantlist != null || mapVariants.isEmpty() || !variantName.contains("=") || !variantName.contains(","))
			return;

		String sampleVariant = mapVariants.entrySet().iterator().next().getKey();
		List<String> conditionOrder = Arrays.stream(sampleVariant.split(","))
				.map(condition -> condition.split("=")[0])
				.collect(Collectors.toList());
		StringBuilder newVariantName = new StringBuilder();
		Map<String, String> conditions = this.parseConditions(variantName);

		for (String condition2 : conditionOrder) {
			if (newVariantName.length() > 0)
				newVariantName.append(",");

			if (!conditions.containsKey(condition2))
				continue;

			newVariantName.append(condition2).append("=").append(conditions.get(condition2));
		}

		variantlist = mapVariants.get(newVariantName.toString());
		if (variantlist != null)
			callbackInfoReturnable.setReturnValue(variantlist);
	}

	private Map<String, String> parseConditions(String conditions) {
		HashMap<String, String> conditionsMap = new HashMap<>();
		for (String condition : conditions.split(",")) {
			String[] conditionSplit = condition.split("=");
			conditionsMap.put(conditionSplit[0], conditionSplit[1]);
		}

		return conditionsMap;
	}

}
