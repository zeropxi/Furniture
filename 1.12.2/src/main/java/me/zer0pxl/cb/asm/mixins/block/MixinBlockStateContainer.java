package me.zer0pxl.cb.asm.mixins.block;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.BlockStateContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockStateContainer.class)
public class MixinBlockStateContainer {

	@Redirect(method = "setBits(I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;log2DeBruijn(I)I"))
	public int redirectSetBits(int value, int bitsIn) {
		if (bitsIn >= 13)
			return bitsIn;

		return MathHelper.log2DeBruijn(value);
	}

}
