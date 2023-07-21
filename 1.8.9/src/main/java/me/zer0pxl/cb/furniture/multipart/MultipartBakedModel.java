package me.zer0pxl.cb.furniture.multipart;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.zer0pxl.cb.furniture.abstraction.BlockBakedModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class MultipartBakedModel implements BlockBakedModel {

	private final Map<Predicate<IBlockState>, IBakedModel> selectors;
	protected final boolean ambientOcclusion;
	protected final boolean gui3D;
	protected final TextureAtlasSprite particleTexture;
	protected final ItemCameraTransforms cameraTransforms;

	public MultipartBakedModel(Map<Predicate<IBlockState>, IBakedModel> selectorsIn) {
		selectors = selectorsIn;
		IBakedModel ibakedmodel = selectorsIn.values().iterator().next();
		ambientOcclusion = ibakedmodel.isAmbientOcclusion();
		gui3D = ibakedmodel.isGui3d();
		particleTexture = ibakedmodel.getParticleTexture();
		cameraTransforms = ibakedmodel.getItemCameraTransforms();
	}

	public List<BakedQuad> getFaceQuads(EnumFacing enumFacing) {
		return Collections.emptyList();
	}

	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side) {
		ArrayList<BakedQuad> list = Lists.newArrayList();
		if (state != null) {
			for (Map.Entry<Predicate<IBlockState>, IBakedModel> entry : selectors.entrySet()) {
				if (!entry.getKey().test(state)) continue;
				list.addAll(entry.getValue().getGeneralQuads());
				list.addAll(entry.getValue().getFaceQuads(side));
			}
		}
		return list;
	}

	public List<BakedQuad> getGeneralQuads() {
		return Collections.emptyList();
	}

	public boolean isAmbientOcclusion() {
		return this.ambientOcclusion;
	}

	public boolean isGui3d() {
		return this.gui3D;
	}

	public boolean isBuiltInRenderer() {
		return false;
	}

	public TextureAtlasSprite getParticleTexture() {
		return this.particleTexture;
	}

	public ItemCameraTransforms getItemCameraTransforms() {
		return this.cameraTransforms;
	}

	public static class Builder {

		public final Map<Predicate<IBlockState>, IBakedModel> builderSelectors = Maps.newLinkedHashMap();

		public void putModel(Predicate<IBlockState> predicate, IBakedModel model) {
			this.builderSelectors.put(predicate, model);
		}

		public IBakedModel makeMultipartModel() {
			return new MultipartBakedModel(this.builderSelectors);
		}
	}

}
