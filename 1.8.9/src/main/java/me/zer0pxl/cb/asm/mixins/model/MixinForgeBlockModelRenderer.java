package me.zer0pxl.cb.asm.mixins.model;

import me.zer0pxl.cb.furniture.abstraction.BlockBakedModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ForgeBlockModelRenderer.class)
public class MixinForgeBlockModelRenderer {

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/IBakedModel;getFaceQuads(Lnet/minecraft/util/EnumFacing;)Ljava/util/List;"))
	private static List<BakedQuad> redirectRender(IBakedModel iBakedModel, EnumFacing facing, VertexLighterFlat lighter, IBlockAccess world, IBakedModel model, Block block, BlockPos pos, WorldRenderer wr, boolean checkSides) {
		if (iBakedModel instanceof BlockBakedModel)
			return ((BlockBakedModel) iBakedModel).getQuads(block.getActualState(world.getBlockState(pos), world, pos), facing);

		return iBakedModel.getFaceQuads(facing);
	}

}
