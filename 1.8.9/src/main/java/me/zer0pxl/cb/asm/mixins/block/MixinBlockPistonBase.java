package me.zer0pxl.cb.asm.mixins.block;

import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockPistonBase.class)
public class MixinBlockPistonBase {

	@Inject(method = "canPush", at = @At("HEAD"), cancellable = true)
	private static void injectCanPush(Block blockIn, World worldIn, BlockPos pos, EnumFacing direction, boolean allowDestroy, CallbackInfoReturnable<Boolean> cir) {
		if (blockIn instanceof VersionBlock)
			cir.setReturnValue(false);
	}

}
