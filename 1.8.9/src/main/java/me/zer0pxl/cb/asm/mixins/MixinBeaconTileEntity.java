package me.zer0pxl.cb.asm.mixins;

import me.zer0pxl.cb.furniture.block.version_specific.BeaconVersionBlock;
import me.zer0pxl.cb.furniture.util.Reflector;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBeacon.BeamSegment;
import net.minecraft.util.ITickable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(TileEntityBeacon.class)
public abstract class MixinBeaconTileEntity extends TileEntity implements ITickable {

	@Shadow
	public abstract void updateBeacon();

	public void update() {
		IBlockState blockState = getWorld().getBlockState(getPos());
		if (blockState.getBlock() instanceof BeaconVersionBlock)
			((BeaconVersionBlock) blockState.getBlock()).updateColor();

		updateBeacon();
	}

	@Redirect(method = "updateSegmentColors", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", remap = false))
	public boolean redirectUpdateSegmentColors(List<BeamSegment> list, Object object) {
		BeamSegment beamSegment = (BeamSegment) object;
		IBlockState blockState = getWorld().getBlockState(getPos());

		if (blockState.getBlock() instanceof BeaconVersionBlock && list.size() == 0) {
			float[] colors = ((BeaconVersionBlock) blockState.getBlock()).getColor();
			Reflector.set(object, colors, "colors", "field_177266_a");
		}

		return list.add(beamSegment);
	}

}
