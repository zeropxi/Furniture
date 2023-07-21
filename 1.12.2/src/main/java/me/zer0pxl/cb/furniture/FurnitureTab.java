package me.zer0pxl.cb.furniture;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class FurnitureTab extends CreativeTabs {

	public static final FurnitureTab INSTANCE = new FurnitureTab();

	private FurnitureTab() {
		super("cb");
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModBlocks.getBlockByKey("cb:oak_table").getBlockHandle());
	}

	@Override
	public String getTranslationKey() {
		return "Möbel";
	}

}
