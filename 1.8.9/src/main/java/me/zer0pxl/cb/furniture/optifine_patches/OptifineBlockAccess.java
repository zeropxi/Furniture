package me.zer0pxl.cb.furniture.optifine_patches;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;

public class OptifineBlockAccess implements IBlockAccess {
	private final IBlockAccess chunkCache;

	public OptifineBlockAccess(IBlockAccess chunkCache) {
		this.chunkCache = chunkCache;
	}

	public IBlockState getBlockState(BlockPos pos) {
		return chunkCache.getBlockState(pos);
	}

	public TileEntity getTileEntity(BlockPos pos) {
		return null;
	}

	public int getCombinedLight(BlockPos pos, int lightValue) {
		return 0;
	}

	public boolean isAirBlock(BlockPos pos) {
		return false;
	}

	public BiomeGenBase getBiomeGenForCoords(BlockPos pos) {
		return null;
	}

	public boolean extendedLevelsInChunkCache() {
		return false;
	}

	public int getStrongPower(BlockPos pos, EnumFacing direction) {
		return 0;
	}

	public WorldType getWorldType() {
		return null;
	}

	public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
		return false;
	}

}
