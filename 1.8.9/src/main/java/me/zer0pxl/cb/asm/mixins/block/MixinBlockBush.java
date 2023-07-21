package me.zer0pxl.cb.asm.mixins.block;

import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBush.class)
public class MixinBlockBush {

	@Inject(method = "canPlaceBlockOn", at = @At("HEAD"), cancellable = true)
	public void injectCanSustainBush(Block block, CallbackInfoReturnable<Boolean> cir) {
		if (block instanceof VersionBlock && ((VersionBlock) block).getModBlock().isCanSustainBush())
			cir.setReturnValue(true);
	}

}
