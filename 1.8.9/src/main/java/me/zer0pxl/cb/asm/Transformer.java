package me.zer0pxl.cb.asm;

import me.zer0pxl.cb.asm.transformer.BlockModelRendererTransformer;
import me.zer0pxl.cb.asm.transformer.RenderImplementationTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.file.Files;

public class Transformer implements IClassTransformer, Opcodes {

	private static final File mixinFile = new File("libraries/org/spongepowered/mixin/0.7.11/mixin-0.7.11.jar");

	public Transformer() throws Throwable {
		if (!mixinFile.exists())
			downloadMixin();

		injectIntoClassLoaders();

		MixinBootstrap.init();
		Mixins.addConfiguration("cb.mixins.json");
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void downloadMixin() throws Throwable {
		mixinFile.getParentFile().mkdirs();
		URLConnection c = new URL("https://repo.spongepowered.org/repository/maven-public/org/spongepowered/mixin/0.7.11-SNAPSHOT/mixin-0.7.11-20180703.121122-1.jar").openConnection();
		c.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
		Files.copy(c.getInputStream(), mixinFile.toPath());
	}

	private void injectIntoClassLoaders() throws Throwable {
		Field field = Launch.classLoader.getClass().getDeclaredField("parent");
		field.setAccessible(true);
		Object parent = field.get(Launch.classLoader);

		Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		method.setAccessible(true);

		URL arg = mixinFile.toURI().toURL();
		method.invoke(parent, arg);
		method.invoke(Launch.classLoader, arg);
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (transformedName.startsWith("me.zer0pxl.cb.asm.transformer"))
			return basicClass;

		byte[] bytes = BlockModelRendererTransformer.transform(transformedName, basicClass);
		bytes = RenderImplementationTransformer.transform(transformedName, bytes);
		return bytes;
	}

	{
		// Hack for some weird shit happening together with the ShinyPots Addon
		try {
			Class<?> lmTransformer = Class.forName("net.labymod.core.asm.LabyModTransformer");
			Class<?> mc18MappingImpl = Class.forName("net.labymod.core.asm.mappings.Minecraft18MappingImplementation");
			Field mappingImpl = lmTransformer.getDeclaredField("mappingImplementation");
			mappingImpl.setAccessible(true);
			mappingImpl.set(null, mc18MappingImpl.newInstance());
		} catch (ReflectiveOperationException ignored) {}

	}

}