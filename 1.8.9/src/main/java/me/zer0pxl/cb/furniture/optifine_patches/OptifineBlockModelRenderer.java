package me.zer0pxl.cb.furniture.optifine_patches;

import me.zer0pxl.cb.furniture.abstraction.BlockBakedModel;
import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.List;

@SuppressWarnings("unused")
public class OptifineBlockModelRenderer {

	public static List<BakedQuad> getQuads(List<BakedQuad> quads, IBakedModel iBakedModel, IBlockState state, BlockPos pos, EnumFacing facing) {
		Block block = state.getBlock();

		if (iBakedModel instanceof BlockBakedModel)
			return ((BlockBakedModel) iBakedModel).getQuads(block.getActualState(state, Minecraft.getMinecraft().theWorld, pos), facing);

		if (block instanceof VersionBlock)
			return Minecraft.getMinecraft().getBlockRendererDispatcher().getModelFromBlockState(block.getActualState(state, Minecraft.getMinecraft().theWorld, pos), Minecraft.getMinecraft().theWorld, pos).getFaceQuads(facing);

		return quads;
	}

	public static IBakedModel getModel(IBakedModel model, IBlockState state, BlockPos pos) {
		if (!(state.getBlock() instanceof VersionBlock))
			return model;

		return Minecraft.getMinecraft().getBlockRendererDispatcher().getModelFromBlockState(state.getBlock().getActualState(state, Minecraft.getMinecraft().theWorld, pos), Minecraft.getMinecraft().theWorld, pos);
	}

}
