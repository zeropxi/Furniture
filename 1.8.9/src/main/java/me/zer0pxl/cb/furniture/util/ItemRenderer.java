package me.zer0pxl.cb.furniture.util;

import me.zer0pxl.cb.furniture.block.version_specific.CustomItemBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;

import static net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.GUI;
import static net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms;
import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings("deprecation")
public class ItemRenderer {

	public static void renderItemIntoGUI(ItemStack stack, int x, int y, float zLevel) {
		TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
		IBakedModel ibakedmodel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
		textureManager.bindTexture(TextureMap.locationBlocksTexture);
		textureManager.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(GL_GREATER, 0.1f);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1f, 1f, 1f, 1f);
		ItemTransformVec3f itemTransformVec3f = ibakedmodel.getItemCameraTransforms().getTransform(GUI);
		setupGuiTransform(x, y, zLevel, ibakedmodel.isGui3d(), stack.getItem() instanceof CustomItemBlock && itemTransformVec3f != ItemTransformVec3f.DEFAULT);

		ibakedmodel = applyTransform(ibakedmodel, stack);

		Minecraft.getMinecraft().getRenderItem().renderItem(stack, ibakedmodel);
		GlStateManager.disableAlpha();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
		textureManager.bindTexture(TextureMap.locationBlocksTexture);
		textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
	}

	private static void setupGuiTransform(int xPosition, int yPosition, float zLevel, boolean isGui3d, boolean isCustomModel) {
		GlStateManager.translate(xPosition, yPosition, 100f + zLevel);
		GlStateManager.translate(8f, 8f, 0f);

		if (isCustomModel) {
			GlStateManager.scale(16f, -16f, 16f);
			GlStateManager.enableLighting();
			return;
		}

		GlStateManager.scale(0.5f, 0.5f, -0.5f);
		if (isGui3d) {
			GlStateManager.scale(40f, 40f, 40f);
			GlStateManager.rotate(210f, 1f, 0f, 0f);
			GlStateManager.rotate(-135f, 0f, 1f, 0f);
			GlStateManager.enableLighting();
		} else {
			GlStateManager.scale(64f, 64f, 64f);
			GlStateManager.rotate(180f, 1f, 0f, 0f);
			GlStateManager.disableLighting();
		}
	}

	private static IBakedModel applyTransform(IBakedModel ibakedmodel, ItemStack stack) {
		if (!(stack.getItem() instanceof CustomItemBlock))
			return handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GUI);

		if (((CustomItemBlock) stack.getItem()).getBlock().getUnlocalizedName().contains("stairs"))
			ibakedmodel.getItemCameraTransforms().gui.rotation.y = 45f;

		ItemTransformVec3f itemtransformvec3f = ibakedmodel.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GUI);

		if (itemtransformvec3f == ItemTransformVec3f.DEFAULT)
			return handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GUI);

		GlStateManager.translate(itemtransformvec3f.translation.x, itemtransformvec3f.translation.y, itemtransformvec3f.translation.z);
		GlStateManager.rotate(itemtransformvec3f.rotation.x, 1f, 0f, 0f);
		GlStateManager.rotate(itemtransformvec3f.rotation.y, 0f, 1f, 0f);
		GlStateManager.rotate(itemtransformvec3f.rotation.z, 0f, 0f, 1f);
		GlStateManager.scale(itemtransformvec3f.scale.x, itemtransformvec3f.scale.y, itemtransformvec3f.scale.z);

		if (isThereOneNegativeScale(itemtransformvec3f))
			GlStateManager.cullFace(GL_FRONT);

		return ibakedmodel;
	}

	private static boolean isThereOneNegativeScale(ItemTransformVec3f itemTransformVec) {
		return itemTransformVec.scale.x < 0 ^ itemTransformVec.scale.y < 0 ^ itemTransformVec.scale.z < 0;
	}

}
