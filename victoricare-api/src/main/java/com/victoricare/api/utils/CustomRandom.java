package com.victoricare.api.utils;

public class CustomRandom {

	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String get2Chars() {
		Integer pos1 = get(0, 25);
		Integer pos2 = get(0, 25);
		return ALPHABET.charAt(pos1) + "" + ALPHABET.charAt(pos2);
	}

	public static Integer get4DigitCode() {
		return get(1000, 9999);
	}

	public static Integer get6DigitCode() {
		return get(100000, 999999);
	}

	public static boolean check6DigitCode(Integer code) {
		return check(100000, 999999, code);
	}

	public static Integer get(Integer min, Integer max) {
		if (min == null || max == null) {
			return null;
		}

		final int range = max - min + 1;
		return (int) ((Math.random() * range) + min);
	}

	public static boolean check(Integer min, Integer max, Integer code) {
		if (code == null || min == null || max == null) {
			return false;
		}
		return code >= min && code <= max;
	}
}
