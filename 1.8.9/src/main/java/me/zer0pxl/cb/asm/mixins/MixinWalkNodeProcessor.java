package me.zer0pxl.cb.asm.mixins;

import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.pathfinder.WalkNodeProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WalkNodeProcessor.class)
public class MixinWalkNodeProcessor {

	@Inject(method = "func_176170_a", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/block/Block;getMaterial()Lnet/minecraft/block/material/Material;", ordinal = 3), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
	private static void injectWalkNodeProcess(IBlockAccess blockaccessIn, Entity entityIn, int x, int y, int z, int sizeX, int sizeY, int sizeZ, boolean avoidWater, boolean breakDoors, boolean enterDoors, CallbackInfoReturnable<Integer> cir, boolean flag, BlockPos blockpos, BlockPos.MutableBlockPos blockpos$mutableblockpos, int i, int j, int k, Block block) {
		if (block instanceof VersionBlock && ((VersionBlock)block).isFence())
			cir.setReturnValue(-3);
	}

}
