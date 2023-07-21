package me.zer0pxl.cb.furniture.abstraction;

import me.zer0pxl.cb.furniture.multipart.Multipart;
import me.zer0pxl.cb.furniture.multipart.VariantList;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;

import java.util.Map;
import java.util.Set;

public interface ModModelBlockDefinition {

	Map<String, ModelBlockDefinition.Variants> getMapVariants();

	boolean hasMultipartData();

	Multipart getMultipartData();

	void setMultipart(Multipart multipart);

	Set<VariantList> getMultipartVariants();

}
