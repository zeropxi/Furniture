package me.zer0pxl.cb.asm.mixins.model;

import com.google.gson.*;
import me.zer0pxl.cb.furniture.abstraction.ModModelBlockDefinition;
import me.zer0pxl.cb.furniture.multipart.Multipart;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.util.JsonUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Mixin(ModelBlockDefinition.Deserializer.class)
public abstract class MixinModelBlockDefinitionDeserializer {

	@Shadow
	protected abstract List<ModelBlockDefinition.Variants> parseVariantsList(JsonDeserializationContext p_178334_1_, JsonObject p_178334_2_);

	@Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/client/renderer/block/model/ModelBlockDefinition;", at = @At("HEAD"), cancellable = true)
	public void injectDeserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext, CallbackInfoReturnable<ModelBlockDefinition> cir) {
		JsonObject jsonobject = jsonElement.getAsJsonObject();
		List<ModelBlockDefinition.Variants> variants = parseVariantsList(jsonDeserializationContext, jsonobject);
		Multipart multipart = parseMultipart(jsonDeserializationContext, jsonobject);

		if (variants.isEmpty() && (multipart == null || multipart.getVariants().isEmpty()))
			throw new JsonParseException("Neither 'variants' nor 'multipart' found");

		ModelBlockDefinition modelBlockDefinition = new ModelBlockDefinition(variants);
		((ModModelBlockDefinition) modelBlockDefinition).setMultipart(multipart);
		cir.setReturnValue(modelBlockDefinition);
	}

	@Inject(method = "parseVariantsList", at = @At("HEAD"), cancellable = true)
	public void injectParseVariantsList(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject, CallbackInfoReturnable<List<ModelBlockDefinition.Variants>> cir) {
		if (!jsonObject.has("variants"))
			cir.setReturnValue(Collections.emptyList());
	}

	protected Multipart parseMultipart(JsonDeserializationContext deserializationContext, JsonObject object) {
		if (!object.has("multipart"))
			return null;

		JsonArray jsonarray = JsonUtils.getJsonArray(object, "multipart");
		return deserializationContext.deserialize(jsonarray, Multipart.class);
	}

}
