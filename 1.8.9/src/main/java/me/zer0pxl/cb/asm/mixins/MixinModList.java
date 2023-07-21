package me.zer0pxl.cb.asm.mixins;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(value = FMLHandshakeMessage.ModList.class, remap = false)
public abstract class MixinModList {

	@Shadow
	private Map<String, String> modTags;

	@Inject(method = "<init>(Ljava/util/List;)V", at = @At(value = "RETURN"))
	public void init(List<ModContainer> modList, CallbackInfo ci) {
		if (Minecraft.getMinecraft().isIntegratedServerRunning())
			return;

		modTags.keySet().removeIf(s -> s.equals("cb"));
	}

}
