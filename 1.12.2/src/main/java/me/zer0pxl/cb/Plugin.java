package me.zer0pxl.cb;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class Plugin implements IFMLLoadingPlugin {

	public static boolean obfuscated = false;
	public static File location = null;

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"me.zer0pxl.cb.asm.Transformer"};
	}

	@Override
	public String getModContainerClass() {
		return "me.zer0pxl.cb.Container";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		obfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
		location = (File) data.getOrDefault("coremodLocation", new File(""));
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}