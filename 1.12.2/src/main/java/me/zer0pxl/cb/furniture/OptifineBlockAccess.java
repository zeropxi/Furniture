package me.zer0pxl.cb.furniture;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

public class OptifineBlockAccess implements IBlockAccess {
	private final IBlockAccess chunkCache;

	public OptifineBlockAccess(IBlockAccess chunkCache) {
		this.chunkCache = chunkCache;
	}

	public IBlockState getBlockState(BlockPos pos) {
		return chunkCache.getBlockState(pos);
	}

	public TileEntity getTileEntity(BlockPos pos) { return null; }
	public int getCombinedLight(BlockPos pos, int lightValue) { return 0; }
	public boolean isAirBlock(BlockPos pos) { return false; }
	public Biome getBiome(BlockPos pos) { return null; }
	public int getStrongPower(BlockPos pos, EnumFacing direction) { return 0; }
	public WorldType getWorldType() { return null; }
	public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) { return false; }

}
