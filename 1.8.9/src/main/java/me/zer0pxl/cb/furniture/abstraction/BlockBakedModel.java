package me.zer0pxl.cb.furniture.abstraction;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.EnumFacing;

import java.util.List;

public interface BlockBakedModel extends IBakedModel {

	default List<BakedQuad> getQuads(IBlockState state, EnumFacing side) {
		return getFaceQuads(side);
	}

}