package me.zer0pxl.cb.furniture.util;

import me.zer0pxl.cb.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class Reflector {

	public static String getMapped(String unobf, String obf) {
		return Plugin.obfuscated ? obf : unobf;
	}

	public static void set(Object obj, Object value, String unobf, String obf) {
		try {
			find(obj, unobf, obf).set(obj, value);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T get(Object obj, String unobf, String obf) {
		try {
			return (T) find(obj, unobf, obf).get(obj);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T invoke(Object obj, String unobf, String obf, Object... args) {
		try {
			Method method = obj.getClass().getDeclaredMethod(getMapped(unobf, obf));
			method.setAccessible(true);
			return (T) method.invoke(obj, args);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	private static Field find(Object obj, String unobf, String obf) throws NoSuchFieldException {
		Class<?> clazz = obj.getClass();
		do {
			try {
				Field field = clazz.getDeclaredField(getMapped(unobf, obf));
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException ignored) {
			}
		} while ((clazz = clazz.getSuperclass()) != Object.class);

		throw new NoSuchFieldException("Field " + getMapped(unobf, obf) + " not found in " + obj);
	}

}
