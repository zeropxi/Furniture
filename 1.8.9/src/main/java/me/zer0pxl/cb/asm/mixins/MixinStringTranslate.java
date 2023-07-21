package me.zer0pxl.cb.asm.mixins;

import me.zer0pxl.cb.furniture.ModBlocks;
import me.zer0pxl.cb.furniture.block.ModBlock;
import net.minecraft.util.StringTranslate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(StringTranslate.class)
public abstract class MixinStringTranslate {

	@Shadow
	@Final
	private Map<String, String> languageList;

	@Inject(method = "tryTranslateKey", at = @At("HEAD"))
	public void injectTryTranslateKey(String key, CallbackInfoReturnable<String> cir) {
		if (languageList.containsKey("cb.detection.placeholder"))
			return;

		for (ModBlock value : ModBlocks.getBlocks().values())
			languageList.put(String.format("tile.%s.name", value.getBlockKey().getLocation().substring("cb:".length())), value.getName());

		languageList.put("cb.detection.placeholder", "Hello :D");
	}

}
