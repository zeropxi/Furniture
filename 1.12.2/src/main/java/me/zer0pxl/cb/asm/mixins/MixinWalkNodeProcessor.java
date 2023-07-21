package me.zer0pxl.cb.asm.mixins;

import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({WalkNodeProcessor.class})
public class MixinWalkNodeProcessor {

	@Inject(method = "getPathNodeTypeRaw", at = {@At("HEAD")}, cancellable = true)
	public void injectGetPathNodeTypeRaw(IBlockAccess blockAccess, int x, int y, int z, CallbackInfoReturnable<PathNodeType> callbackInfoReturnable) {
		BlockPos blockpos = new BlockPos(x, y, z);
		IBlockState iblockstate = blockAccess.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		if (!(block instanceof VersionBlock))
			return;

		PathNodeType pathNodeType = ((VersionBlock) block).getPathNodeType(iblockstate);
		if (pathNodeType == null)
			return;

		callbackInfoReturnable.setReturnValue(pathNodeType);
	}

}
