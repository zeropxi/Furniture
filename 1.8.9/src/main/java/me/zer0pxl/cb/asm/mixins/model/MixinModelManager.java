package me.zer0pxl.cb.asm.mixins.model;

import me.zer0pxl.cb.furniture.abstraction.ModModelBakery;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.IRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelManager.class)
public class MixinModelManager {

	@Redirect(method = "onResourceManagerReload", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/ModelBakery;setupModelRegistry()Lnet/minecraft/util/IRegistry;"))
	public IRegistry<ModelResourceLocation, IBakedModel> redirectSetupModelRegistry(ModelBakery modelBakery) {
		IRegistry<ModelResourceLocation, IBakedModel> modelRegistry = modelBakery.setupModelRegistry();
		((ModModelBakery)modelBakery).bakeMultipartModels();
		return modelRegistry;
	}

}
