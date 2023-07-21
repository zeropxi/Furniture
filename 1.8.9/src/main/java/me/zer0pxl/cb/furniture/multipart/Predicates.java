package me.zer0pxl.cb.furniture.multipart;

import java.util.function.Predicate;

public class Predicates {

	public static <T> Predicate<T> not(Predicate<T> predicate) {
		return value -> !predicate.test(value);
	}

	public static <T> Predicate<T> or(Iterable<Predicate<T>> iterable) {
		return value -> {
			for (Predicate<T> predicate : iterable)
				if (predicate.test(value))
					return true;
			return false;
		};
	}

	public static <T> Predicate<T> and(Iterable<Predicate<T>> iterable) {
		return value -> {
			for (Predicate<T> predicate : iterable)
				if (!predicate.test(value))
					return false;
			return true;
		};
	}

}
