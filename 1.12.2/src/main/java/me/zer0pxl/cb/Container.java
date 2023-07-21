package me.zer0pxl.cb;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import me.zer0pxl.cb.furniture.Registerer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLFileResourcePack;
import net.minecraftforge.fml.client.FMLFolderResourcePack;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class Container extends DummyModContainer {

	public Container() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "cb";
		meta.name = "Möbel";
		meta.description = "MysteryMods Möbel, nur ohne MysteryMod\nhttps://discord.gg/ckNt8p9zrq";
		meta.url = "https://kurzelinks.de/moebel-url";
		meta.logoFile = "assets/cb/icon.png";
		meta.version = "1.0";
		meta.authorList = ImmutableList.of("zer0pxl");
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@Override
	public Class<?> getCustomResourcePackClass() {
		return Plugin.obfuscated ? FMLFileResourcePack.class : FMLFolderResourcePack.class;
	}

	@Override
	public File getSource() {
		return Plugin.obfuscated ? Plugin.location : new File("../src/main/resources");
	}

	@Override
	public Object getMod() {
		return this;
	}

	@Subscribe
	public void onInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new Registerer());
	}

}