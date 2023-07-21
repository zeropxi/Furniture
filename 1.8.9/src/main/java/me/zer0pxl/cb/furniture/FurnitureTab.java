package me.zer0pxl.cb.furniture;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class FurnitureTab extends CreativeTabs {

	public static final FurnitureTab INSTANCE = new FurnitureTab();

	private FurnitureTab() {
		super("cb");
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(ModBlocks.getBlockByKey("cb:oak_table").getBlockHandle());
	}

	@Override
	public String getTranslatedTabLabel() {
		return "Möbel";
	}

}
