package me.zer0pxl.cb.furniture.block;

import com.google.gson.JsonObject;
import me.zer0pxl.cb.furniture.ModBlocks;
import me.zer0pxl.cb.furniture.properties.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public abstract class ModBlock {

	private final BlockProperties blockProperties;
	private Block blockHandle;
	private IBlockState defaultState;
	private JsonObject blockData = new JsonObject();
	private String versionBlockClass;
	private Boolean hasVersionBlockClass;
	private String name;
	private ModBlocks.BlockKey blockKey;
	private boolean climbable;
	private boolean trapDoor;
	private boolean canSustainBush;
	private float lightLevel;

	public ModBlock(BlockProperties blockProperties) {
		this.blockProperties = blockProperties;
	}

	public void initBlockData(JsonObject blockData) {
		this.climbable = blockData.has("climbable") && blockData.get("climbable").getAsBoolean();
		this.trapDoor = blockData.has("trapDoor") && blockData.get("trapDoor").getAsBoolean();
		this.canSustainBush = blockData.has("canSustainBush") && blockData.get("canSustainBush").getAsBoolean();
		this.lightLevel = blockData.has("lightlevel") ? blockData.get("lightlevel").getAsFloat() : 0.0f;
	}

	public abstract void addProperties(List<IProperty<?>> properties);

	public abstract IBlockState getDefaultState(IBlockState defaultState);

	public IBlockState getActualState(IBlockState state, BlockPos blockPosition, IBlockAccess world) {
		return state;
	}

	public boolean isValidPosition(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPosition) {
		return true;
	}

	public int getMetaFromState(IBlockState blockState) {
		return 0;
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.defaultState;
	}

	public boolean hasItem() {
		return true;
	}

	public ModBlock getBlockToSupplyToItem() {
		return this;
	}

	public List<AxisAlignedBB> getShapes(IBlockState blockState, List<AxisAlignedBB> providedShapes) {
		return providedShapes;
	}

	public List<AxisAlignedBB> getCollisionShapes(IBlockState blockState, List<AxisAlignedBB> providedShapes) {
		return providedShapes;
	}

	public BlockProperties getBlockProperties() {
		return this.blockProperties;
	}

	public Block getBlockHandle() {
		return this.blockHandle;
	}

	public IBlockState getDefaultState() {
		return this.defaultState;
	}

	public JsonObject getBlockData() {
		return this.blockData;
	}
	public String getVersionBlockClass() {
		if (hasVersionBlockClass == null) {
			String name = getClass().getSimpleName();
			name = name.substring(0, name.length() - 5) + "VersionBlock";
			try {
				Class.forName("me.zer0pxl.cb.furniture.block.version_specific." + name);
				versionBlockClass = name;
				hasVersionBlockClass = true;
			} catch (ClassNotFoundException ignored) {
				hasVersionBlockClass = false;
			}
		}

		return versionBlockClass;
	}

	public ModBlocks.BlockKey getBlockKey() {
		return this.blockKey;
	}

	public String getName() {
		return this.name;
	}

	public boolean isClimbable() {
		return this.climbable;
	}

	public boolean isTrapDoor() {
		return this.trapDoor;
	}

	public boolean isCanSustainBush() {
		return this.canSustainBush;
	}

	public float getLightLevel() {
		return this.lightLevel;
	}

	public void setBlockHandle(Block blockHandle) {
		this.blockHandle = blockHandle;
	}

	public void setDefaultState(IBlockState defaultState) {
		this.defaultState = defaultState;
	}

	public void setBlockData(JsonObject blockData) {
		this.blockData = blockData;
	}

	public void setVersionBlockClass(String versionBlockClass) {
		this.versionBlockClass = versionBlockClass;
		hasVersionBlockClass = true;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBlockKey(ModBlocks.BlockKey blockKey) {
		this.blockKey = blockKey;
	}

}
