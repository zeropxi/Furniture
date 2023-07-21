package me.zer0pxl.cb.asm.mixins.block;

import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockPistonBase.class)
public class MixinBlockPistonBase {

	@Inject(method = "canPush", at = @At("HEAD"), cancellable = true)
	private static void injectCanPush(IBlockState blockStateIn, World worldIn, BlockPos pos, EnumFacing facing, boolean destroyBlocks, EnumFacing p_185646_5_, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		if (blockStateIn.getBlock() instanceof VersionBlock)
			callbackInfoReturnable.setReturnValue(false);
	}

}
