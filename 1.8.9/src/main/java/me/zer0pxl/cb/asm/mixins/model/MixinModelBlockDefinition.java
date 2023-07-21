package me.zer0pxl.cb.asm.mixins.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.zer0pxl.cb.furniture.abstraction.ModModelBlockDefinition;
import me.zer0pxl.cb.furniture.multipart.Multipart;
import me.zer0pxl.cb.furniture.multipart.Selector;
import me.zer0pxl.cb.furniture.multipart.VariantList;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Mixin(ModelBlockDefinition.class)
public class MixinModelBlockDefinition implements ModModelBlockDefinition {

	private static final Gson GSON = new GsonBuilder()
		.registerTypeAdapter(ModelBlockDefinition.class, new ModelBlockDefinition.Deserializer())
		.registerTypeAdapter(ModelBlockDefinition.Variant.class, new ModelBlockDefinition.Variant.Deserializer())
		.registerTypeAdapter(VariantList.class, new VariantList.Deserializer())
		.registerTypeAdapter(Multipart.class, new Multipart.Deserializer())
		.registerTypeAdapter(Selector.class, new Selector.Deserializer())
		.create();

	@Shadow
	@Final
	private Map<String, ModelBlockDefinition.Variants> mapVariants;
	private Multipart multipart;

	@Inject(method = "parseFromReader", at = @At("HEAD"), cancellable = true)
	private static void injectParseFromReader(Reader reader, CallbackInfoReturnable<ModelBlockDefinition> cir) {
		cir.setReturnValue(MixinModelBlockDefinition.GSON.fromJson(reader, ModelBlockDefinition.class));
	}

	@Inject(method = "<init>(Ljava/util/List;)V", at = @At("RETURN"))
	public void injectConstruct(List<ModelBlockDefinition> modelBlockDefinitions, CallbackInfo callbackInfo) {
		ModelBlockDefinition definition = null;

		for (ModelBlockDefinition currentDefinition : modelBlockDefinitions) {
			if (((ModModelBlockDefinition)currentDefinition).hasMultipartData()) {
				mapVariants.clear();
				definition = currentDefinition;
			}
		}

		if (definition != null)
			multipart = ((ModModelBlockDefinition) definition).getMultipartData();
	}

	@Inject(method = "getVariants", at = @At("HEAD"), cancellable = true)
	public void injectGetVariant(String variantName, CallbackInfoReturnable<ModelBlockDefinition.Variants> cir) {
		ModelBlockDefinition.Variants variantlist = mapVariants.get(variantName);
		if (variantlist != null
			|| mapVariants.isEmpty()
			|| !variantName.contains("=")
			|| !variantName.contains(","))
			return;

		String sampleVariant = mapVariants.entrySet().iterator().next().getKey();
		List<String> conditionOrder = Arrays.stream(sampleVariant.split(","))
			.map(condition -> condition.split("=")[0])
			.collect(Collectors.toList());

		StringBuilder newVariantName = new StringBuilder();
		Map<String, String> conditions = parseConditions(variantName);

		for (String condition : conditionOrder) {
			if (newVariantName.length() > 0)
				newVariantName.append(",");

			if (!conditions.containsKey(condition))
				continue;

			newVariantName.append(condition).append("=").append(conditions.get(condition));
		}

		variantlist = mapVariants.get(newVariantName.toString());
		if (variantlist != null)
			cir.setReturnValue(variantlist);
	}

	private Map<String, String> parseConditions(String conditions) {
		Map<String, String> conditionsMap = new HashMap<>();

		for (String condition : conditions.split(",")) {
			String[] conditionSplit = condition.split("=");
			conditionsMap.put(conditionSplit[0], conditionSplit[1]);
		}

		return conditionsMap;
	}

	@Inject(method = "equals", at = @At("HEAD"), cancellable = true)
	public void equals(Object object, CallbackInfoReturnable<Boolean> cir) {
		if (this == object) {
			cir.setReturnValue(true);
			return;
		}

		if (object instanceof ModelBlockDefinition) {
			ModModelBlockDefinition definition = (ModModelBlockDefinition) object;

			if (mapVariants.equals(definition.getMapVariants())) {
				cir.setReturnValue(hasMultipartData() ? multipart.equals(definition.getMultipartData()) : !definition.hasMultipartData());
				return;
			}
		}

		cir.setReturnValue(false);
	}

	@Override
	public int hashCode() {
		return 31 * mapVariants.hashCode() + (hasMultipartData() ? multipart.hashCode() : 0);
	}

	@Override
	public Map<String, ModelBlockDefinition.Variants> getMapVariants() {
		return mapVariants;
	}

	@Override
	public boolean hasMultipartData() {
		return multipart != null;
	}

	@Override
	public Multipart getMultipartData() {
		return multipart;
	}

	@Override
	public void setMultipart(Multipart multipart) {
		this.multipart = multipart;
	}

	@Override
	public Set<VariantList> getMultipartVariants() {
		Set<VariantList> set = mapVariants.values().stream()
			.map(variants -> new VariantList(variants.getVariants()))
			.collect(Collectors.toSet());

		if (hasMultipartData())
			set.addAll(multipart.getVariants());

		return set;
	}

}
