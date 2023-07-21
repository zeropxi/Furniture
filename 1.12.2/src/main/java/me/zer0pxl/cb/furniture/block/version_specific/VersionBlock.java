package me.zer0pxl.cb.furniture.block.version_specific;

import me.zer0pxl.cb.furniture.FurnitureTab;
import me.zer0pxl.cb.furniture.OptifineBlockAccess;
import me.zer0pxl.cb.furniture.block.FurnitureHorizontalBlock;
import me.zer0pxl.cb.furniture.block.ModBlock;
import me.zer0pxl.cb.furniture.block.type.ItemOnlyBlock;
import me.zer0pxl.cb.furniture.blockshapes.BlockShape;
import me.zer0pxl.cb.furniture.blockshapes.BlockShapesLoader;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

@SuppressWarnings("deprecation")
public class VersionBlock extends Block {

	private static ModBlock registeringBlock;
	protected final ModBlock modBlock;
	private final Map<IBlockState, List<AxisAlignedBB>> collisionShapes = new HashMap<>();
	private final Map<IBlockState, AxisAlignedBB> mainBoundingBoxes = new HashMap<>();

	public VersionBlock(ModBlock modBlock) {
		super(modBlock.getBlockProperties().material, modBlock.getBlockProperties().materialColor);
		this.modBlock = modBlock;
		BlockProperties blockProperties = modBlock.getBlockProperties();
		this.setSoundType(blockProperties.soundType);
		this.setResistance(blockProperties.resistance);
		this.setHardness(blockProperties.hardness);
		this.setCreativeTab(FurnitureTab.INSTANCE);
		this.setDefaultState(this.modBlock.getDefaultState(this.getBlockState().getBaseState()));
		this.modBlock.setDefaultState(this.getDefaultState());
	}

	public void addShapes() {
		Map<String, BlockShape> shapes = BlockShapesLoader.READ_SHAPES.getShapes(this.modBlock.getBlockKey().getLocation());

		for (IBlockState state : this.getBlockState().getValidStates()) {
			String shapeKey = this.getShapeKey(state);

			BlockShape blockShape = shapes.getOrDefault(shapeKey, BlockShape.DEFAULT_SHAPE);
			List<AxisAlignedBB> boundingBoxes = this.modBlock.getShapes(state, blockShape.getShape());
			this.mainBoundingBoxes.put(state, this.getFullBoundingBox(boundingBoxes));

			List<AxisAlignedBB> collisionShapes = blockShape.isHasCollisionShape()
					? this.modBlock.getCollisionShapes(state, blockShape.getCollisionShapes())
					: this.modBlock.getShapes(state, blockShape.getShape());
			this.collisionShapes.put(state, collisionShapes);
		}
	}

	private String getShapeKey(IBlockState state) {
		return state.toString().replace(this.modBlock.getBlockKey().getLocation(), "").replace("[", "").replace("]", "");
	}

	private AxisAlignedBB getFullBoundingBox(List<AxisAlignedBB> boundingBoxes) {
		if (boundingBoxes.size() == 0)
			return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

		Double minX = null;
		Double minY = null;
		Double minZ = null;
		Double maxX = null;
		Double maxY = null;
		Double maxZ = null;

		for (AxisAlignedBB boundingBox : boundingBoxes) {
			if (minX == null || boundingBox.minX < minX) minX = boundingBox.minX;
			if (minY == null || boundingBox.minY < minY) minY = boundingBox.minY;
			if (minZ == null || boundingBox.minZ < minZ) minZ = boundingBox.minZ;
			if (maxX == null || boundingBox.maxX > maxX) maxX = boundingBox.maxX;
			if (maxY == null || boundingBox.maxY > maxY) maxY = boundingBox.maxY;
			if (maxZ == null || boundingBox.maxZ > maxZ) maxZ = boundingBox.maxZ;
		}

		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	protected BlockStateContainer createBlockState() {
		List<IProperty<?>> list = new ArrayList<>();
		getCurrentBlockData().addProperties(list);

		return new BlockStateContainer(this, list.toArray(new IProperty[0]));
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		AxisAlignedBB boundingBox = this.mainBoundingBoxes.get(this.getActualState(state, source, pos));
		return boundingBox != null ? boundingBox : new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
	}

	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		if (!isActualState) {
			state = this.getActualState(state, worldIn, pos);
		}
		List<AxisAlignedBB> list = this.collisionShapes.getOrDefault(state, Collections.singletonList(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)));
		for (AxisAlignedBB box : list) {
			VersionBlock.addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
		}
	}

	public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
		blockState = this.getActualState(blockState, worldIn, pos);
		ArrayList<RayTraceResult> list = new ArrayList<>();
		for (AxisAlignedBB axisalignedbb : this.collisionShapes.getOrDefault(blockState, Collections.singletonList(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)))) {
			list.add(this.rayTrace(pos, start, end, axisalignedbb));
		}
		RayTraceResult raytraceresult1 = null;
		double d1 = 0.0;
		for (RayTraceResult raytraceresult : list) {
			double d0;
			if (raytraceresult == null || !((d0 = raytraceresult.hitVec.squareDistanceTo(end)) > d1)) continue;
			raytraceresult1 = raytraceresult;
			d1 = d0;
		}
		return raytraceresult1;
	}

	public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
		if (this.modBlock instanceof ItemOnlyBlock)
			return false;
		return super.canPlaceBlockAt(world, blockPos);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		ModBlock blockData = this.getCurrentBlockData();
		IBlockState blockState = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
		if (blockData instanceof FurnitureHorizontalBlock)
			blockState = blockState.withProperty(FurnitureHorizontalBlock.DIRECTION, placer.getHorizontalFacing());

		return blockState;
	}

	public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
		IBlockAccess minecraftBlockAccess = iBlockAccess.getClass().getName().equals("net.optifine.override.ChunkCacheOF") ? new OptifineBlockAccess(iBlockAccess) : iBlockAccess;
		return this.getCurrentBlockData().getActualState(iBlockState, blockPos, minecraftBlockAccess);
	}

	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.modBlock.getStateFromMeta(meta);
	}

	public int getMetaFromState(IBlockState state) {
		return this.modBlock.getMetaFromState(state);
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public PathNodeType getPathNodeType(IBlockState state) {
		return null;
	}

	protected ModBlock getCurrentBlockData() {
		return this.modBlock != null ? this.modBlock : registeringBlock;
	}

	public static void setRegisteringBlock(ModBlock registeringBlock) {
		VersionBlock.registeringBlock = registeringBlock;
	}

	public ModBlock getModBlock() {
		return this.modBlock;
	}

	public boolean isTopSolid(IBlockState state) {
		return true;
	}

}
