package me.zer0pxl.cb.asm.mixins.block;

import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBush.class)
public class MixinBlockBush {

	@Inject(method = "canSustainBush", at = @At("HEAD"), cancellable = true)
	public void injectCanSustainBush(IBlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.getBlock() instanceof VersionBlock && (((VersionBlock) state.getBlock()).getModBlock().isCanSustainBush()))
			cir.setReturnValue(true);
	}

}
