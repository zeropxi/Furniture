package me.zer0pxl.cb.asm.mixins;

import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class MixinWorld {

	@Inject(method = "doesBlockHaveSolidTopSurface", at = @At("HEAD"), cancellable = true)
	private static void injectDoesBlockHaveSolidTopSurface(IBlockAccess blockAccess, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (blockAccess.getBlockState(pos).getBlock() instanceof VersionBlock)
			cir.setReturnValue(true);
	}

}
