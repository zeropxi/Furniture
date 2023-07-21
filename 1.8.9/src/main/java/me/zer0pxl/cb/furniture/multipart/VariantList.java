package me.zer0pxl.cb.furniture.multipart;

import com.google.gson.*;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition.Variant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VariantList {
	private final List<Variant> variantList;

	public VariantList(List<Variant> variantListIn) {
		variantList = variantListIn;
	}

	public List<Variant> getVariantList() {
		return variantList;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (!(object instanceof VariantList))
			return false;

		return variantList.equals(((VariantList) object).variantList);
	}

	@Override
	public int hashCode() {
		return variantList.hashCode();
	}

	public static class Deserializer implements JsonDeserializer<VariantList> {

		public VariantList deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
			ArrayList<Variant> list = new ArrayList<>();
			if (json.isJsonArray()) {
				JsonArray jsonarray = json.getAsJsonArray();
				if (jsonarray.size() == 0)
					throw new JsonParseException("Empty variant array");

				for (JsonElement jsonelement : jsonarray)
					list.add(context.deserialize(jsonelement, Variant.class));
			} else {
				list.add(context.deserialize(json, Variant.class));
			}

			return new VariantList(list);
		}

	}

}
