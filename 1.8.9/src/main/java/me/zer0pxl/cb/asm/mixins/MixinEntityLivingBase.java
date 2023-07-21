package me.zer0pxl.cb.asm.mixins;

import me.zer0pxl.cb.furniture.block.version_specific.VersionBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.util.MathHelper.floor_double;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

	public MixinEntityLivingBase(World worldIn) {
		super(worldIn);
	}

	@Inject(method = "isOnLadder", at = @At("HEAD"), cancellable = true)
	public void injectIsOnLadder(CallbackInfoReturnable<Boolean> cir) {
		BlockPos blockpos = new BlockPos(floor_double(posX), floor_double(getEntityBoundingBox().minY), floor_double(posZ));
		Block block = worldObj.getBlockState(blockpos).getBlock();

		cir.setReturnValue((block == Blocks.ladder
			|| block == Blocks.vine
			|| (block instanceof VersionBlock
			&& ((VersionBlock)block).getModBlock().isClimbable())
			) && (!(((Object) this) instanceof EntityPlayer)
			|| !((EntityPlayer)(Object) this).isSpectator()));
	}

}
