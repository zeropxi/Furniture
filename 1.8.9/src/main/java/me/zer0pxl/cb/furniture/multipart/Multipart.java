package me.zer0pxl.cb.furniture.multipart;

import com.google.common.collect.Sets;
import com.google.gson.*;
import net.minecraft.block.state.BlockState;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Multipart {

	private final List<Selector> selectors;
	private BlockState stateContainer;

	public Multipart(List<Selector> selectorsIn) {
		selectors = selectorsIn;
	}

	public List<Selector> getSelectors() {
		return selectors;
	}

	public Set<VariantList> getVariants() {
		HashSet<VariantList> set = Sets.newHashSet();

		for (Selector selector : selectors)
			set.add(selector.getVariantList());

		return set;
	}

	public void setStateContainer(BlockState stateContainerIn) {
		stateContainer = stateContainerIn;
	}

	public BlockState getStateContainer() {
		return stateContainer;
	}

	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (!(object instanceof Multipart))
			return false;

		Multipart multipart = (Multipart) object;
		if (selectors.equals(multipart.selectors)) {
			if (stateContainer == null)
				return multipart.stateContainer == null;
			return stateContainer.equals(multipart.stateContainer);
		}

		return false;
	}

	public int hashCode() {
		int hash = 31 * selectors.hashCode();

		if (stateContainer != null)
			hash += stateContainer.hashCode();

		return hash;
	}

	public static class Deserializer implements JsonDeserializer<Multipart> {

		public Multipart deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
			return new Multipart(getSelectors(context, json.getAsJsonArray()));
		}

		private List<Selector> getSelectors(JsonDeserializationContext context, JsonArray elements) {
			ArrayList<Selector> list = new ArrayList<>();

			for (JsonElement jsonelement : elements)
				list.add(context.deserialize(jsonelement, Selector.class));

			return list;
		}

	}

}
