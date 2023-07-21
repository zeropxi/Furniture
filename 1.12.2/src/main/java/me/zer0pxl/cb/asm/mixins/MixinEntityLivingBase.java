package me.zer0pxl.cb.asm.mixins;

import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.util.math.MathHelper.floor;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

	@Shadow
	protected abstract boolean canGoThroughtTrapDoorOnLadder(BlockPos pos, IBlockState state);

	public MixinEntityLivingBase(World worldIn) {
		super(worldIn);
	}

	@Inject(method = "isOnLadder", at = @At(value = "HEAD"), cancellable = true)
	public void injectIsOnLadder(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		Object ths = this;
		if (ths instanceof EntityPlayer && ((EntityPlayer) ths).isSpectator()) {
			callbackInfoReturnable.setReturnValue(false);
			return;
		}

		BlockPos blockpos = new BlockPos(floor(posX), floor(getEntityBoundingBox().minY), floor(posZ));
		IBlockState iblockstate = this.world.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		if (block == Blocks.LADDER || block == Blocks.VINE || block instanceof VersionBlock && ((VersionBlock) block).getModBlock().isClimbable()) {
			callbackInfoReturnable.setReturnValue(true);
			return;
		}

		boolean isTrapdoor = block instanceof BlockTrapDoor || block instanceof VersionBlock && ((VersionBlock) block).getModBlock().isTrapDoor();
		callbackInfoReturnable.setReturnValue(isTrapdoor && canGoThroughtTrapDoorOnLadder(blockpos, iblockstate));
	}

}
