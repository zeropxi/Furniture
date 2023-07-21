package me.zer0pxl.cb.asm.mixins.block;

import me.zer0pxl.cb.furniture.block.ReplaceableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {

	@Inject(method = "canPlaceBlockAt", at = @At(value = "HEAD"), cancellable = true)
	public void injectCanPlaceBlockAt(World worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		IBlockState state = worldIn.getBlockState(pos);
		callbackInfoReturnable.setReturnValue(state.getBlock() instanceof ReplaceableBlock || state.getBlock().isReplaceable(worldIn, pos));
	}

}
